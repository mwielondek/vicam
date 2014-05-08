package com.dreamteam.vicam.view.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.events.EditPresetEvent;
import com.dreamteam.vicam.model.events.SavePresetEvent;
import com.dreamteam.vicam.model.events.UpdatePresetEvent;
import com.dreamteam.vicam.model.pojo.Preset;
import com.dreamteam.vicam.presenter.utility.Dagger;
import com.dreamteam.vicam.view.MainActivity;

import de.greenrobot.event.EventBus;

import javax.inject.Inject;

/**
 * Manages a custom layout for the SavePreset dialog
 */
public class EditPresetDialogFragment extends DialogFragment {

  Activity mContext;
  @Inject
  EventBus eventBus;
  private Preset presetToEdit;

  public EditPresetDialogFragment(Context context) {
    Dagger.inject(this);
    mContext = (Activity) context;
  }

  public void setPresetToEdit(Preset p) {
    this.presetToEdit = p;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

    // Don't know if title is too much?
    // builder.setTitle(R.string.dialog_save_preset_title);

    // Get the layout inflater
    LayoutInflater inflater = mContext.getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_save_preset, null);
    final EditText editText = (EditText) view.findViewById(R.id.edit_text_save_preset);
    editText.setText(presetToEdit.getName());

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(view)
        // Add action buttons
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // Add the preset to database
            Preset renamedPreset = presetToEdit.copy().name(editText.getText().toString()).commit();
            eventBus.post(new UpdatePresetEvent(renamedPreset));
          }
        })
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
          // Cancel dialog
          public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
          }
        });
    return builder.create();
  }
}
