package com.dreamteam.vicam.view.custom.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
 * Manages a custom layout for the Edit Camera Dialog
 */
public class EditCameraDialogFragment extends DialogFragment {

  private static final String CAMERA_ID_KEY = "camera_id_key";
  private static final Pattern IP_ADDRESS = Patterns.IP_ADDRESS;
  private boolean validName;
  private boolean validIP;
  private boolean validPort;
  private Context ctx;

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

    Context ctx = getActivity();
    ctx.setTheme(android.R.style.Theme_Holo_Light);

    final int cameraId = getArguments().getInt(CAMERA_ID_KEY);

    // Get the layout inflater
    LayoutInflater inflater = activity.getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_edit_camera, null);

    builder.setTitle(R.string.edit_camera);

    final EditText nameEdit = (EditText) view.findViewById(R.id.edit_camera_name);
    final EditText ipEdit = (EditText) view.findViewById(R.id.edit_camera_ip);
    final EditText portEdit = (EditText) view.findViewById(R.id.edit_camera_port);
    final Switch invertXSwitch = (Switch) view.findViewById(R.id.edit_camera_invert_x_axis);
    final Switch invertYSwitch = (Switch) view.findViewById(R.id.edit_camera_invert_y_axis);



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

        validName = true;
        if (TextUtils.isEmpty(nameEdit.getText().toString())) {
          nameEdit.setError("Invalid name");
          validName = false;
          alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(false);
        }

        if(validName && validIP && validPort) {
          alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(true);
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

        validIP = true;
        Matcher matcher = IP_ADDRESS.matcher(ipEdit.getText().toString());
        if (!matcher.matches()) {
          ipEdit.setError("Invalid IP-Address");
          validIP = false;
          alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
        if(validName && validIP && validPort) {
          alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(true);
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

        validPort = true;

        try {
          int x = Integer.parseInt(portEdit.getText().toString());
          if (x < 0 || x > 65535) {
            portEdit.setError("Invalid Port");
            validPort = false;
            alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(false);
          }

        } catch (NumberFormatException e) {
          // Nothing
        }

        if(validName && validIP && validPort) {
          alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(true);
        }
      }
    });

    nameEdit.setOnFocusChangeListener(
        new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {

            if (!hasFocus) {
              validName = true;
              if (TextUtils.isEmpty(nameEdit.getText().toString())) {
                nameEdit.setError("Invalid name");
                validName = false;
                alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(false);
              }
              if(validName && validIP && validPort) {
                alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(true);
              }
            }
          }
        }
    );

    ipEdit.setOnFocusChangeListener(
        new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
              validIP = true;
              Matcher matcher = IP_ADDRESS.matcher(ipEdit.getText().toString());
              if (!matcher.matches()) {
                ipEdit.setError("Invalid IP-Address");
                validIP = false;
                alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(false);
              }
              if(validName && validIP && validPort) {
                alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(true);
              }
            }
          }
        }
    );

    portEdit.setOnFocusChangeListener(
        new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
              validPort = true;

              try {
                int x = Integer.parseInt(portEdit.getText().toString());
                if (x < 0 || x > 65535) {
                  portEdit.setError("Invalid Port");
                  validPort = false;
                  alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(false);
                }

              } catch (NumberFormatException e) {
                // Nothing
              }

              if(validName && validIP && validPort) {
                alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(true);
              }
            }
          }
        }
    );

    alertDialog.setOnShowListener(
        new DialogInterface.OnShowListener() {
          @Override
          public void onShow(DialogInterface dialogInterface) {
            if(validName && validIP && validPort) {
              alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(true);
            } else {
              alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setEnabled(false);
            }

          }
        }
    );

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


    return alertDialog;
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    //this.getDialog().setCanceledOnTouchOutside(true);
    return null;
  }


}
