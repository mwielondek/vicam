package com.dreamteam.vicam.view.custom.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.events.SaveCameraEvent;
import com.dreamteam.vicam.presenter.utility.Dagger;

import de.greenrobot.event.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

  private boolean wasKeyboardOpen = false;

  private static final Pattern IP_ADDRESS = Patterns.IP_ADDRESS;

  private Context ctx;

  public AddCameraDialogFragment() {
    Dagger.inject(this);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    ctx = getActivity();
    ctx.setTheme(android.R.style.Theme_Holo_Light);

    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_add_camera, null);

    builder.setTitle(R.string.add_new_camera);

    final EditText nameEdit = (EditText) view.findViewById(R.id.add_camera_name);
    final EditText ipEdit = (EditText) view.findViewById(R.id.add_camera_ip);
    final EditText portEdit = (EditText) view.findViewById(R.id.add_camera_port);
    final Switch invertXSwitch = (Switch) view.findViewById(R.id.add_camera_invert_x_axis);
    final Switch invertYSwitch = (Switch) view.findViewById(R.id.add_camera_invert_y_axis);

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
                portEdit.getText().toString(),
                !invertXSwitch.isChecked(),
                invertYSwitch.isChecked()));
          }
        })
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
          // Cancel dialog
          public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
          }
        });

    final AlertDialog alertDialog = builder.create();

    nameEdit.setOnFocusChangeListener(
        new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {

            if (!hasFocus) {
              boolean validValues = true;
              if (TextUtils.isEmpty(nameEdit.getText().toString())) {
                nameEdit.setError("Invalid name");
                validValues = false;
              }
              alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(validValues);
            }
          }
        }
    );

    ipEdit.setOnFocusChangeListener(
        new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
              boolean validValues = true;
              Matcher matcher1 = IP_ADDRESS.matcher(ipEdit.getText().toString());
              if (!matcher1.matches()) {
                ipEdit.setError("Invalid IP-Address");
                validValues = false;
              }
              alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(validValues);
            }
          }
        }
    );

    alertDialog.setOnShowListener(
        new DialogInterface.OnShowListener() {
          @Override
          public void onShow(DialogInterface dialogInterface) {
            alertDialog.getButton(alertDialog.BUTTON_POSITIVE)
                .setEnabled(false);
          }
        }
    );

    return alertDialog;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    this.getDialog().setCanceledOnTouchOutside(true);
    return null;
  }
}
