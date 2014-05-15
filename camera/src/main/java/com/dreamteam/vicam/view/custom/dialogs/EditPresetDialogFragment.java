package com.dreamteam.vicam.view.custom.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.database.DAOFactory;
import com.dreamteam.vicam.model.database.PresetDAO;
import com.dreamteam.vicam.model.events.EditPresetEvent;
import com.dreamteam.vicam.model.pojo.Preset;
import com.dreamteam.vicam.presenter.utility.Dagger;
import com.dreamteam.vicam.presenter.utility.Utils;

import de.greenrobot.event.EventBus;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Manages a custom layout for the SavePreset dialog
 */
public class EditPresetDialogFragment extends DialogFragment {

  private static final String PRESET_ID_KEY = "preset_id_key";

  @Inject
  EventBus mEventBus;
  @Inject
  DAOFactory mDAOFactory;

  public EditPresetDialogFragment() {
    Dagger.inject(this);
  }

  public static DialogFragment newInstance(int presetId) {
    DialogFragment frag = new EditPresetDialogFragment();

    Bundle args = new Bundle();
    args.putInt(PRESET_ID_KEY, presetId);

    frag.setArguments(args);
    return frag;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final int presetId = getArguments().getInt(PRESET_ID_KEY);

    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    Context ctx = getActivity();
   // ctx.setTheme(android.R.style.Theme_Holo_Light);

    // Inflate the layout for the dialog
    LayoutInflater inflater = getActivity().getLayoutInflater();
    // Pass null as the parent view because its going in the dialog layout
    View view = inflater.inflate(R.layout.dialog_save_preset, null);
    final EditText editText = (EditText) view.findViewById(R.id.edit_text_save_preset);
    mDAOFactory.getPresetDAO().flatMap(new Func1<PresetDAO, Observable<Preset>>() {
      @Override
      public Observable<Preset> call(PresetDAO presetDAO) {
        return presetDAO.findPreset(presetId);
      }
    }).subscribe(new Action1<Preset>() {
      @Override
      public void call(final Preset preset) {
        editText.setText(preset.getName());
        builder.setPositiveButton(
            android.R.string.ok, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int id) {
                // Add the preset to database
                Preset renamedPreset = preset.copy().name(editText.getText().toString()).commit();
                mEventBus.post(new EditPresetEvent(renamedPreset));
              }
            }
        );
      }
    }, Utils.<Throwable>noop());

    builder.setView(view)
        // Add action buttons
        .setNegativeButton(
            android.R.string.cancel,
            new DialogInterface.OnClickListener() {
              // Cancel dialog
              public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
              }
            }
        );
    return builder.create();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    this.getDialog().setCanceledOnTouchOutside(true);
    return null;
  }
}
