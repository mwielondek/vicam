package com.dreamteam.vicam.model.pojo;

/**
 * Created by fsommar on 2014-04-01.
 */
public class CameraState {

  private int id;
  private Position position;
  private Zoom zoom;
  private Focus focus;

  public CameraState(int id, Position position, Zoom zoom, Focus focus) {
    this.id = id;
    this.position = position;
    this.zoom = zoom;
    this.focus = focus;
  }

  public CameraState(Position position, Zoom zoom, Focus focus) {
    this.position = position;
    this.zoom = zoom;
    this.focus = focus;
  }

  public int getId() {
    return id;
  }

  public Position getPosition() {
    return position;
  }

  public Zoom getZoom() {
    return zoom;
  }

  public Focus getFocus() {
    return focus;
  }

  public boolean isAF() {
    return focus.isAutoFocus();
  }
}
