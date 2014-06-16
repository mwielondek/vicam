package com.dreamteam.vicam.model.events;

import com.dreamteam.vicam.model.pojo.Camera;

/**
 * This event fires when the currently selected camera is changed or updated.
 *
 * @author Fredrik Sommar
 * @since 2014-04-23.
 */
public class CameraChangedEvent {

  /**
   * The new representation of the camera.
   */
  public final Camera camera;

  /**
   * An event indicating the provided {@link com.dreamteam.vicam.model.pojo.Camera} as the new
   * currently selected camera.
   *
   * @param camera The new camera representation.
   */
  public CameraChangedEvent(Camera camera) {
    this.camera = camera;
  }
}
