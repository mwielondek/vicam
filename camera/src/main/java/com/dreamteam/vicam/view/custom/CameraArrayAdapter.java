package com.dreamteam.vicam.view.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.pojo.Camera;

import java.util.List;

/**
 * Manages a text representation for {@link com.dreamteam.vicam.model.pojo.Camera} objects in an
 * adapter list.
 *
 * @author Donia Alipoor
 * @since 2014-04-24.
 */
public class CameraArrayAdapter extends ArrayAdapter<Camera> {

  private final Context context;
  private final List<Camera> cameras;

  public CameraArrayAdapter(Context context, List<Camera> cameras) {
    super(context, R.layout.change_camera_spinner, cameras);
    this.context = context;
    this.cameras = cameras;
  }

  @Override
  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    return getCustomView(position, parent);
  }

  private View getCustomView(int position, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.change_camera_spinner, parent, false);

    TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
    textView.setText(cameras.get(position).getName());

    return rowView;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    return getCustomView(position, parent);
  }
}
