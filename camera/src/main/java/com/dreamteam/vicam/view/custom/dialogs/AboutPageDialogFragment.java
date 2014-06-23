package com.dreamteam.vicam.view.custom.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamteam.camera.R;

/**
 * Manages a custom layout for the about dialog fragment.
 *
 * @author Benny Tieu
 */
public class AboutPageDialogFragment extends DialogFragment {

  public static DialogFragment newInstance() {
    return new AboutPageDialogFragment();
  }

  public AboutPageDialogFragment() {
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();
    // Inflates the layout
    View view = inflater.inflate(R.layout.dialog_about_page, null);

    builder.setView(view).setPositiveButton(R.string.ok, null);

    return builder.create();
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    this.getDialog().setCanceledOnTouchOutside(true);
    return null;
  }
}
