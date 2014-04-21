package com.dreamteam.vicam.model.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fsommar on 2014-04-01.
 */
@DatabaseTable(tableName = "preset")
public class Preset {

  @DatabaseField(columnName = "id", generatedId = true)
  private int id;
  @DatabaseField(columnName = "name", unique = true, canBeNull = false)
  private String name;
  @DatabaseField(columnName = "cameraState", foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
  private CameraState cameraState;

  public Preset() {
    // ORMLite needs a no-arg constructor
  }

  public Preset(int id, String name, CameraState cameraState) {
    this.id = id;
    this.name = name;
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

  @Override
  public String toString() {
    return name;
  }
}
