package com.dreamteam.vicam.view.custom.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.view.MainActivity;

/**
 * Manages a custom layout for the Edit Camera Dialog
 */
public class EditCameraDialogFragment extends DialogFragment {

  MainActivity mActivity;

  public EditCameraDialogFragment(MainActivity activity) {
    mActivity = activity;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

    // Get the layout inflater
    LayoutInflater inflater = mActivity.getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_edit_camera, null);

    builder.setTitle(R.string.edit_camera);

    EditText nameEdit = (EditText) view.findViewById(R.id.edit_camera_name);
    EditText ipEdit = (EditText) view.findViewById(R.id.edit_camera_ip);
    EditText portEdit = (EditText) view.findViewById(R.id.edit_camera_port);

    Camera camera = mActivity.getCurrentCamera();



    if (camera != null) {
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




}
