package com.dreamteam.vicam.model.events;

import com.dreamteam.vicam.model.pojo.Camera;

/**
 * This event is fired when a {@link com.dreamteam.vicam.model.pojo.Camera} object is updated and
 * needs its representation in the database to be updated as well.
 *
 * @author Fredrik Sommar
 * @since 2014-05-08.
 */
public class EditCameraEvent {

  /**
   * The updated {@link com.dreamteam.vicam.model.pojo.Camera} object.
   */
  public final Camera camera;

  /**
   * An event indicating that the database entry with the same id as the provided {@link
   * com.dreamteam.vicam.model.pojo.Camera} object should be updated to match the object.
   *
   * @param camera The camera to update its database representation with. Make sure its key is set
   *               and is the same as its representation in the database!
   */
  public EditCameraEvent(Camera camera) {
    this.camera = camera;
  }
}
