package com.dreamteam.vicam.model.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fsommar on 2014-04-01.
 */
@DatabaseTable(tableName = "preset")
public class Preset {

  @DatabaseField(columnName = "id", generatedId = true)
  private int id = -1;
  @DatabaseField(columnName = "name", unique = true, canBeNull = false)
  private String name;
  @DatabaseField(columnName = "cameraState", foreign = true, foreignAutoCreate = true,
                 foreignAutoRefresh = true)
  private CameraState cameraState;

  Preset() {
    // ORMLite needs a no-arg constructor
  }

  public Preset(int id, String name, CameraState cameraState) {
    this(name, cameraState);
    this.id = id;
  }

  public Preset(String name, CameraState cameraState) {
    if (name == null || cameraState == null) {
      throw new IllegalArgumentException(String.format(
          "Name(%s) or camera state(%s) can't be null!", name, cameraState));
    }
    this.name = name;
    this.cameraState = cameraState;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public CameraState getCameraState() {
    return cameraState;
  }

  public Preset copyWithName(String name) {
    return new Preset(id, name, cameraState);
  }

  @Override
  public String toString() {
    return name;
  }
}
