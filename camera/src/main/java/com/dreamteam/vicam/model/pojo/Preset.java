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

  public Preset copyWithName(String newName) {
    return new Preset(id, newName, camera, cameraState);
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
}
