package com.dreamteam.vicam.view.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.pojo.Preset;

import java.util.List;

/**
 * Manages a text representation for {@link com.dreamteam.vicam.model.pojo.Preset} objects in an
 * adapter list.
 *
 * @author Donia Alipoor
 * @since 2014-04-24.
 */
public class PresetArrayAdapter extends ArrayAdapter<Preset> {

  private final Context context;
  private final List<Preset> presets;

  public PresetArrayAdapter(Context context, List<Preset> presets) {
    super(context, R.layout.drawer_list_item, presets);
    this.context = context;
    this.presets = presets;
  }

  @Override
  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    return getCustomView(position, parent);
  }

  private View getCustomView(int position, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.drawer_list_item, parent, false);

    TextView textView = (TextView) rowView.findViewById(R.id.textView);
    textView.setText(presets.get(position).getName());

    return rowView;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    return getCustomView(position, parent);
  }
}
