package com.dreamteam.vicam.view.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.events.SaveCameraEvent;
import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.presenter.utility.Dagger;
import com.dreamteam.vicam.view.MainActivity;

import de.greenrobot.event.EventBus;

import javax.inject.Inject;

/**
 * Manages a custom layout for the Edit Camera Dialog
 */
public class EditCameraDialogFragment extends DialogFragment {
  private Camera mCurrentCamera;

  /*
  @Inject
  EventBus mEventBus;
  */
  Activity mActivity;

  public EditCameraDialogFragment(Activity activity, MainActivity currentActivity) {
    //Dagger.inject(this);

    mCurrentCamera = currentActivity.getCurrentCamera();
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

    if(mCurrentCamera == null) {

    } else {
      // Show current camera as hints
      nameEdit.setHint(mCurrentCamera.getName());
      ipEdit.setHint(mCurrentCamera.getIp().toString());
      portEdit.setHint(mCurrentCamera.getPort().toString());
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
