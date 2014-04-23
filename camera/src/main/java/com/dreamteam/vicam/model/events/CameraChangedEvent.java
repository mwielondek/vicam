package com.dreamteam.vicam.model.events;

import com.dreamteam.vicam.model.pojo.Camera;

/**
 * Created by fsommar on 2014-04-23.
 */
public class CameraChangedEvent {

  public final Camera camera;

  public CameraChangedEvent(Camera c) {
    this.camera = c;
  }
}
