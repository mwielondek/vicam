package com.dreamteam.vicam.view.custom.listeners;

import android.view.View;
import android.widget.AdapterView;

import com.dreamteam.vicam.model.events.CameraChangedEvent;
import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.presenter.utility.Dagger;

import de.greenrobot.event.EventBus;

import javax.inject.Inject;

/**
 * Created by fsommar on 2014-04-26.
 */
public class CameraSpinnerItemListener implements AdapterView.OnItemSelectedListener {
  @Inject
  EventBus mEventBus;

  public CameraSpinnerItemListener() {
    Dagger.inject(this);
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    Camera camera = (Camera) parent.getItemAtPosition(position);
    if (camera != null) {
      mEventBus.post(new CameraChangedEvent(camera));
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
  }

}
