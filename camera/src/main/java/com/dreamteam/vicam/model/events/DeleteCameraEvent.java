package com.dreamteam.vicam.model.events;

import com.dreamteam.vicam.model.pojo.Camera;

/**
 * This event is fired when a camera should be deleted.
 *
 * @author Fredrik Sommar
 * @since 2014-05-08.
 */
public class DeleteCameraEvent {

  /**
   * The camera that is set for deletion.
   */
  public final Camera camera;

  /**
   * An event indicating that the provided {@link com.dreamteam.vicam.model.pojo.Camera} should be
   * deleted.
   *
   * @param camera The camera that is set for deletion.
   */
  public DeleteCameraEvent(Camera camera) {
    this.camera = camera;
  }
}
