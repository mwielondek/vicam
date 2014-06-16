package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.model.interfaces.Identifiable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Used for storing different predefined settings for a specific camera.
 * 
 * @author Milosz Wielondek
 * @author Daniel Millevik
 * @since 2014-04-01.
 */
@DatabaseTable(tableName = "preset")
public class Preset implements Identifiable {

  @DatabaseField(columnName = "id", generatedId = true)
  private int id = -1;
  @DatabaseField(columnName = "name", canBeNull = false)
  private String name;
  @DatabaseField(columnName = "camera_id", foreign = true, canBeNull = false)
  private Camera camera;
  @DatabaseField(columnName = "cameraState_id", foreign = true, foreignAutoCreate = true,
                 foreignAutoRefresh = true)
  private CameraState cameraState;

  /**
   * ORMLite needs a no-argument constructor.
   */
  Preset() {
  }

  /**
   * Creates a Preset object with a name, Camera and CameraState as well as id.
   * The Preset's name, Camera and CameraState can not be null.
   * @throws {@link IllegalArgumentException}.
   */
  public Preset(int id, String name, Camera camera, CameraState cameraState) {
    this(name, camera, cameraState);
    this.id = id;
  }

  /**
   * Creates a Preset object with a name, Camera and CameraState.
   * The Preset's name, Camera and CameraState can not be null.
   * @throws {@link IllegalArgumentException}.
   */
  public Preset(String name, Camera camera, CameraState cameraState) {
    if (name == null || camera == null || cameraState == null) {
      throw new IllegalArgumentException(String.format(
          "Camera(%s), Name(%s) or camera state(%s) can't be null!", camera, name, cameraState));
    }
    this.name = name;
    this.camera = camera;
    this.cameraState = cameraState;
  }

  /**
   * Returns the unique id for the Preset object.
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the Preset's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the Camera object that the preset is tied to.
   */
  public Camera getCamera() {
    return camera;
  }

  /**
   * Returns saved settings for the camera as a CameraState object
   */
  public CameraState getCameraState() {
    return cameraState;
  }

  /**
   * Creates a new Copy Object
   */
  public Copy copy() {
    return new Copy();
  }

  /**
   * Overrides toString and returns the new representation of the Preset object as a String.
   * 
   * @return A String representing the Preset object and its state.
   */
  @Override
  public String toString() {
    return "Preset{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", camera=" + camera +
           ", cameraState=" + cameraState +
           '}';
  }

  /**
   * The Copy Class is used for copying Preset objects and making changes to them.
   * Returns the new object with the commit method.
   * 
   * @author Fredrik Sommar
   */
  public class Copy {
    private String cName;
    private Camera cCamera;
    private CameraState cCameraState;


    /**
     * The constructor copies the exact same state that the Preset has.
     */
    private Copy() {
      this.cName = name;
      this.cCamera = camera;
      this.cCameraState = cameraState;
    }

    /**
     * Changes the name of the copy.
     */
    public Copy name(String name) {
      this.cName = name;
      return this;
    }

    /**
     * Changes the camera of the copy.
     */
    public Copy camera(Camera camera) {
      this.cCamera = camera;
      return this;
    }

    /**
     * Changes the camera state of the copy.
     */
    public Copy cameraState(CameraState cameraState) {
      this.cCameraState = cameraState;
      return this;
    }

    /**
     * Creates a new Preset with the copied or changed state and returns it.
     */
    public Preset commit() {
      return new Preset(id, cName, cCamera, cCameraState);
    }
  }

}
