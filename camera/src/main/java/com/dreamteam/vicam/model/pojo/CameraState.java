package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.model.database.ormlite.ZoomPersister;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fsommar on 2014-04-01.
 */
@DatabaseTable(tableName = "cameraState")
public class CameraState {

  @DatabaseField(columnName = "id", generatedId = true)
  private int id = -1;
  @DatabaseField(columnName = "position", foreign = true, foreignAutoCreate = true,
                 foreignAutoRefresh = true)
  private Position position;
  @DatabaseField(columnName = "zoom", persisterClass = ZoomPersister.class)
  private Zoom zoom;
  @DatabaseField(columnName = "focus", foreign = true, foreignAutoCreate = true,
                 foreignAutoRefresh = true)
  private Focus focus;

  CameraState() {
    // ORMLite needs a no-arg constructor
  }

  public CameraState(int id, Position position, Zoom zoom, Focus focus) {
    this(position, zoom, focus);
    this.id = id;
  }

  public CameraState(Position position, Zoom zoom, Focus focus) {
    if (position == null || zoom == null || focus == null) {
      throw new IllegalArgumentException(String.format(
          "No parameters are allowed to be null! (Position(%s), Zoom(%s), Focus(%s))",
          position, zoom, focus));
    }
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
    return focus.isAutofocus();
  }
}
