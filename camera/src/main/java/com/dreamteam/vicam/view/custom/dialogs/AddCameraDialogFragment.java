package com.dreamteam.vicam.view.custom.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.events.SaveCameraEvent;
import com.dreamteam.vicam.presenter.utility.Dagger;

import de.greenrobot.event.EventBus;

import javax.inject.Inject;

/**
 * Manages a custom layout for the Add Camera dialog in settings
 */
public class AddCameraDialogFragment extends DialogFragment {

  @Inject
  EventBus mEventBus;

  public static DialogFragment newInstance() {
    return new AddCameraDialogFragment();
  }

  public AddCameraDialogFragment() {
    Dagger.inject(this);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_add_camera, null);

    builder.setTitle(R.string.add_new_camera);

    final EditText nameEdit = (EditText) view.findViewById(R.id.add_camera_name);
    final EditText ipEdit = (EditText) view.findViewById(R.id.add_camera_ip);
    final EditText portEdit = (EditText) view.findViewById(R.id.add_camera_port);

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(view)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // Send event to save camera in database
            mEventBus.post(new SaveCameraEvent(
                dialog,
                nameEdit.getText().toString(),
                ipEdit.getText().toString(),
                portEdit.getText().toString()));
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

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    this.getDialog().setCanceledOnTouchOutside(true);
    return null;
  }
}
