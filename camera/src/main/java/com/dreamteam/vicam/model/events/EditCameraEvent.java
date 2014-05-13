package com.dreamteam.vicam.model.events;

import com.dreamteam.vicam.model.pojo.Camera;

/**
 * Created by fsommar on 2014-05-08.
 */
public class EditCameraEvent {

  public final Camera camera;

  public EditCameraEvent(Camera camera) {
    this.camera = camera;
  }
}
