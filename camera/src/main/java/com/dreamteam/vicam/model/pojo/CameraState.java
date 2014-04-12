package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.model.database.ormlite.ZoomPersister;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fsommar on 2014-04-01.
 */
@DatabaseTable(tableName = "cameraStates")
public class CameraState {

  @DatabaseField(generatedId = true)
  private int id;
  @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
  private Position position;
  @DatabaseField(persisterClass = ZoomPersister.class)
  private Zoom zoom;
  @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
  private Focus focus;

  public CameraState() {
    // ORMLite needs a no-arg constructor
  }

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
