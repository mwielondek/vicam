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

import com.dreamteam.camera.R;

/**
 * Manages a custom layout for the Add Camera dialog in settings
 */
public class AddCameraDialogFragment extends DialogFragment {

  Activity mContext;


  public AddCameraDialogFragment(Context context) {

    mContext = (Activity) context;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

    // Get the layout inflater
    LayoutInflater inflater = mContext.getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_add_camera, null);

    //final EditText addCameraEditText = (EditText) view.findViewById(R.id.add_camera_edittext);

    //final EditText addCameraEditText2 = (EditText) view.findViewById(R.id.add_camera_edittext2);

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(view)
        // Add action buttons
        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // Add the preset to database
         //  eventBus.post(new PresetSaveEvent(dialog, addCameraEditText.getText().toString()));
          }
        })
        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
          // Cancel dialog
          public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
          }
        });
    return builder.create();
  }
}
