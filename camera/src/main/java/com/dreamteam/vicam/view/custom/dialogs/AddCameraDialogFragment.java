package com.dreamteam.vicam.view.custom.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

  private boolean validName;
  private boolean validIP;
  private boolean validPort;

  public AddCameraDialogFragment() {
    Dagger.inject(this);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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

    nameEdit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        validName = true;
        if (TextUtils.isEmpty(nameEdit.getText().toString())) {
          nameEdit.setError("Invalid name");
          validName = false;
          positiveButton.setEnabled(false);
        }

        if (validName && validIP && validPort) {
          positiveButton.setEnabled(true);
        }

      }
    });

    ipEdit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
      }

      @Override
      public void afterTextChanged(Editable editable) {
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        validIP = true;
        Matcher matcher = IP_ADDRESS.matcher(ipEdit.getText().toString());
        if (!matcher.matches()) {
          ipEdit.setError("Invalid IP-Address");
          validIP = false;
          positiveButton.setEnabled(false);
        }
        if (validName && validIP && validPort) {
          positiveButton.setEnabled(true);
        }
      }
    });

    portEdit.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        validPort = true;

        try {
          int x = Integer.parseInt(portEdit.getText().toString());
          if (x < 0 || x > 65535) {
            portEdit.setError("Invalid Port");
            validPort = false;
            positiveButton.setEnabled(false);
          }

        } catch (NumberFormatException e) {
          // Nothing
        }

        if (validName && validIP && validPort) {
          positiveButton.setEnabled(true);
        }
      }
    });

    nameEdit.setOnFocusChangeListener(
        new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (!hasFocus) {
              validName = true;
              if (TextUtils.isEmpty(nameEdit.getText().toString())) {
                nameEdit.setError("Invalid name");
                validName = false;
                positiveButton.setEnabled(false);
              }
              if (validName && validIP && validPort) {
                positiveButton.setEnabled(true);
              }
            }
          }
        }
    );

    ipEdit.setOnFocusChangeListener(
        new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (!hasFocus) {
              validIP = true;
              Matcher matcher = IP_ADDRESS.matcher(ipEdit.getText().toString());
              if (!matcher.matches()) {
                ipEdit.setError("Invalid IP-Address");
                validIP = false;
                positiveButton.setEnabled(false);
              }
              if (validName && validIP && validPort) {
                positiveButton.setEnabled(true);
              }
            }
          }
        }
    );

    portEdit.setOnFocusChangeListener(
        new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (!hasFocus) {
              validPort = true;

              try {
                int x = Integer.parseInt(portEdit.getText().toString());
                if (x < 0 || x > 65535) {
                  portEdit.setError("Invalid Port");
                  validPort = false;
                  positiveButton.setEnabled(false);
                }

              } catch (NumberFormatException e) {
                // Nothing
              }

              if (validName && validIP && validPort) {
                positiveButton.setEnabled(true);
              }
            }
          }
        }
    );

    alertDialog.setOnShowListener(
        new DialogInterface.OnShowListener() {
          @Override
          public void onShow(DialogInterface dialogInterface) {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
          }
        }
    );

    return alertDialog;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    this.getDialog().setCanceledOnTouchOutside(false);
    return null;
  }
}
