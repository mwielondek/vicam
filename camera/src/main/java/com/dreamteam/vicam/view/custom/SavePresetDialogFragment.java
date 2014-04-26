package com.dreamteam.vicam.view.custom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.dreamteam.camera.R;

/**
 * Manages a custom layout for the SavePreset dialog
 */
public class SavePresetDialogFragment extends DialogFragment {

  Activity mMainActivity;

  public SavePresetDialogFragment(Context context) {
    mMainActivity = (Activity) context;


  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(mMainActivity);

    // Don't know if title is too much?
   // builder.setTitle(R.string.dialog_save_preset_title);

    // Get the layout inflater
    LayoutInflater inflater = mMainActivity.getLayoutInflater();

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(inflater.inflate(R.layout.save_preset_dialog, null))
        // Add action buttons
        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            // Add the preset to database
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
