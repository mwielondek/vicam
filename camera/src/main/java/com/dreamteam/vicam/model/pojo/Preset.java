package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.model.interfaces.Identifiable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fsommar on 2014-04-01.
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

  Preset() {
    // ORMLite needs a no-arg constructor
  }

  public Preset(int id, String name, Camera camera, CameraState cameraState) {
    this(name, camera, cameraState);
    this.id = id;
  }

  public Preset(String name, Camera camera, CameraState cameraState) {
    if (name == null || camera == null || cameraState == null) {
      throw new IllegalArgumentException(String.format(
          "Camera(%s), Name(%s) or camera state(%s) can't be null!", camera, name, cameraState));
    }
    this.name = name;
    this.camera = camera;
    this.cameraState = cameraState;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Camera getCamera() {
    return camera;
  }

  public CameraState getCameraState() {
    return cameraState;
  }

  public Copy copy() {
    return new Copy();
  }

  @Override
  public String toString() {
    return "Preset{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", camera=" + camera +
           ", cameraState=" + cameraState +
           '}';
  }

  public class Copy {

    private String cName;
    private Camera cCamera;
    private CameraState cCameraState;


    private Copy() {
      this.cName = name;
      this.cCamera = camera;
      this.cCameraState = cameraState;
    }

    public Copy name(String name) {
      this.cName = name;
      return this;
    }

    public Copy camera(Camera camera) {
      this.cCamera = camera;
      return this;
    }

    public Copy cameraState(CameraState cameraState) {
      this.cCameraState = cameraState;
      return this;
    }

    public Preset commit() {
      return new Preset(id, cName, cCamera, cCameraState);
    }
  }

}
