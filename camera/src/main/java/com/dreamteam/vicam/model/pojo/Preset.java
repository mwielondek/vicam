package com.dreamteam.vicam.model.pojo;

/**
 * Created by fsommar on 2014-04-01.
 */
public class Preset {

  private String name;
  private int id;
  private CameraState cameraState;

  public Preset(String name, int id, CameraState cameraState) {
    this.name = name;
    this.id = id;
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
