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
import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.dreamteam.vicam.view.MainActivity;

import rx.functions.Action1;

/**
 * Manages a custom layout for the Edit Camera Dialog
 */
public class EditCameraDialogFragment extends DialogFragment {

  public static DialogFragment newInstance() {
    return new EditCameraDialogFragment();
  }

  public EditCameraDialogFragment() {
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    MainActivity activity = (MainActivity) getActivity();
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

    // Get the layout inflater
    LayoutInflater inflater = activity.getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_edit_camera, null);

    builder.setTitle(R.string.edit_camera);

    final EditText nameEdit = (EditText) view.findViewById(R.id.edit_camera_name);
    final EditText ipEdit = (EditText) view.findViewById(R.id.edit_camera_ip);
    final EditText portEdit = (EditText) view.findViewById(R.id.edit_camera_port);

    activity.getCurrentCamera().subscribe(
        new Action1<Camera>() {
          @Override
          public void call(Camera camera) {
            // nameEdit.clearFocus();
            // Show current camera as hints
            nameEdit.setText(camera.getName());
            nameEdit.setSelectAllOnFocus(true);
            ipEdit.setText(camera.getIp());
            ipEdit.setSelectAllOnFocus(true);
            // Port can be null
            if (camera.getPort() != null) {
              portEdit.setText(camera.getPort().toString());
              portEdit.setSelectAllOnFocus(true);
            }

          }
        }, Utils.<Throwable>noop()
    );

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(view)
        .setPositiveButton(R.string.apply_changes, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {

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
