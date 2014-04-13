package com.dreamteam.vicam.model.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fsommar on 2014-04-01.
 */
@DatabaseTable(tableName = "preset")
public class Preset {

  @DatabaseField(columnName = "name", unique = true, canBeNull = false)
  private String name;
  @DatabaseField(columnName = "id", generatedId = true)
  private int id;
  @DatabaseField(columnName = "cameraState", foreign = true)
  private CameraState cameraState;

  public Preset() {
    // ORMLite needs a no-arg constructor
  }

  public Preset(String name, int id, CameraState cameraState) {
    this.name = name;
    this.id = id;
    this.cameraState = cameraState;
  }

  public Preset(String name, CameraState cameraState) {
    this.name = name;
    this.cameraState = cameraState;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public CameraState getCameraState() {
    return cameraState;
  }
}
