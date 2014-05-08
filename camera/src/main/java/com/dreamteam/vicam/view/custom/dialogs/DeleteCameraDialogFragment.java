package com.dreamteam.vicam.view.custom.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.database.DAOFactory;
import com.dreamteam.vicam.model.events.DeleteCameraEvent;
import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.presenter.utility.Dagger;

import de.greenrobot.event.EventBus;

import javax.inject.Inject;

/**
 * Created by fsommar on 2014-05-08.
 */
public class DeleteCameraDialogFragment extends DialogFragment {

  private static final String CAMERA_ID_KEY = "camera_id_key";

  @Inject
  EventBus mEventBus;
  @Inject
  DAOFactory mDAOFactory;

  public static DialogFragment newInstance(int cameraId) {
    DialogFragment fragment = new DeleteCameraDialogFragment();

    Bundle bundle = new Bundle();
    bundle.putInt(CAMERA_ID_KEY, cameraId);
    fragment.setArguments(bundle);

    return fragment;
  }

  public DeleteCameraDialogFragment() {
    Dagger.inject(this);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    int cameraId = getArguments().getInt(CAMERA_ID_KEY);
    final Camera camera = mDAOFactory.getCameraDAO().findCamera(cameraId);
    if (camera != null) {
        builder.setTitle(getString(R.string.delete_camera))
            .setMessage(getString(R.string.delete_camera_confirmation, camera.getName()))
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                mEventBus.post(new DeleteCameraEvent(camera));
              }
            })
            .setNegativeButton(android.R.string.cancel, null);
    } else {
      builder.setTitle("Something unintended happen.");
    }

    return builder.create();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    this.getDialog().setCanceledOnTouchOutside(true);
    return null;
  }
}
