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
import com.dreamteam.vicam.model.database.CameraDAO;
import com.dreamteam.vicam.model.database.DAOFactory;
import com.dreamteam.vicam.model.events.EditCameraEvent;
import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.presenter.utility.Dagger;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.dreamteam.vicam.view.MainActivity;

import de.greenrobot.event.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Manages a custom layout for the Edit Camera Dialog.
 *
 * @author Benny Tieu
 */
public class EditCameraDialogFragment extends DialogFragment {

  private static final String CAMERA_ID_KEY = "camera_id_key";

  // These fields checks the validity for corresponding input fields
  private static final Pattern IP_ADDRESS = Patterns.IP_ADDRESS;
  private boolean validName;
  private boolean validIP;
  private boolean validPort;

  @Inject
  EventBus mEventBus;
  @Inject
  DAOFactory mDAOFactory;

  public EditCameraDialogFragment() {
    Dagger.inject(this);
  }

  public static DialogFragment newInstance(int cameraId) {
    DialogFragment frag = new EditCameraDialogFragment();

    Bundle args = new Bundle();
    args.putInt(CAMERA_ID_KEY, cameraId);

    frag.setArguments(args);
    return frag;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    MainActivity activity = (MainActivity) getActivity();
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

    // Get the layout inflater of the current activity
    LayoutInflater inflater = activity.getLayoutInflater();
    // Inflate the layout
    View view = inflater.inflate(R.layout.dialog_edit_camera, null);

    // Set the title of the dialog
    builder.setTitle(R.string.edit_camera);

    final EditText nameEdit = (EditText) view.findViewById(R.id.edit_camera_name);
    final EditText ipEdit = (EditText) view.findViewById(R.id.edit_camera_ip);
    final EditText portEdit = (EditText) view.findViewById(R.id.edit_camera_port);
    final Switch invertXSwitch = (Switch) view.findViewById(R.id.edit_camera_invert_x_axis);
    final Switch invertYSwitch = (Switch) view.findViewById(R.id.edit_camera_invert_y_axis);

    final int cameraId = getArguments().getInt(CAMERA_ID_KEY);
    mDAOFactory.getCameraDAO().flatMap(new Func1<CameraDAO, Observable<Camera>>() {
      @Override
      public Observable<Camera> call(CameraDAO cameraDAO) {
        return cameraDAO.findCamera(cameraId);
      }
    }).subscribe(new Action1<Camera>() {
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
        // Inversion of axes
        invertXSwitch.setChecked(!camera.isInvertX());
        invertYSwitch.setChecked(camera.isInvertY());
      }
    }, Utils.<Throwable>noop());

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(view)
        .setPositiveButton(R.string.apply_changes, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            mEventBus.post(new EditCameraEvent(new Camera(
                cameraId,
                ipEdit.getText().toString(),
                nameEdit.getText().toString(),
                Camera.parsePort(portEdit.getText().toString()),
                !invertXSwitch.isChecked(),
                invertYSwitch.isChecked()
            )));
          }
        })
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
          // Cancel dialog
          @Override
          public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
          }
        });

    final AlertDialog alertDialog = builder.create();

    // Listeners for the input fields

    nameEdit.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
          }

          @Override
          public void afterTextChanged(Editable editable) {
            validName = true;
            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

            // If the field is empty display error
            if (TextUtils.isEmpty(nameEdit.getText().toString())) {
              nameEdit.setError("Invalid name");
              validName = false;
              positiveButton.setEnabled(false);
            }

            // The user can only proceed if the fields name, IP and Port is valid
            if (validName && validIP && validPort) {
              positiveButton.setEnabled(true);
            }

          }
        }
    );

    ipEdit.addTextChangedListener(
        new TextWatcher() {
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

            // Error if IP-address is not valid
            if (!matcher.matches()) {
              ipEdit.setError("Invalid IP-Address");
              validIP = false;
              positiveButton.setEnabled(false);
            }

            // The user can only proceed if the fields name, IP and Port is valid
            if (validName && validIP && validPort) {
              positiveButton.setEnabled(true);
            }
          }
        }
    );

    portEdit.addTextChangedListener(
        new TextWatcher() {
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

              // The port can only be in the interval 0 - 65535, if not set error
              if (x < 0 || x > 65535) {
                portEdit.setError("Invalid Port");
                validPort = false;
                positiveButton.setEnabled(false);
              }

            } catch (NumberFormatException e) {
              // Nothing
            }

            // The user can only proceed if the fields name, IP and Port is valid
            if (validName && validIP && validPort) {
              positiveButton.setEnabled(true);
            }
          }
        }
    );

    nameEdit.setOnFocusChangeListener(
        new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

            if (!hasFocus) {
              validName = true;

              // If the field is empty, set error
              if (TextUtils.isEmpty(nameEdit.getText().toString())) {
                nameEdit.setError("Invalid name");
                validName = false;
                positiveButton.setEnabled(false);
              }

              // The user can only proceed if the fields name, IP and Port is valid
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

              // Error if IP-address is not valid
              if (!matcher.matches()) {
                ipEdit.setError("Invalid IP-Address");
                validIP = false;
                positiveButton.setEnabled(false);
              }
              // The user can only proceed if the fields name, IP and Port is valid
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

                // The port can only be in the interval 0 - 65535, if not set error
                if (x < 0 || x > 65535) {
                  portEdit.setError("Invalid Port");
                  validPort = false;
                  positiveButton.setEnabled(false);
                }
              } catch (NumberFormatException e) {
                // Nothing
              }

              // The user can only proceed if the fields name, IP and Port is valid
              if (validName && validIP && validPort) {
                positiveButton.setEnabled(true);
              }
            }
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
