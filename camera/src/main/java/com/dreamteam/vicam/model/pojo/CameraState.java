package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.model.database.ormlite.ZoomPersister;
import com.dreamteam.vicam.model.interfaces.Identifiable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Stores the current settings for a specific camera. It is often used in conjunction with {@link
 * com.dreamteam.vicam.model.pojo.Preset}.
 *
 * @author Milosz Wielondek
 * @author Daniel Millevik
 * @since 2014-04-01.
 */
@DatabaseTable(tableName = "cameraState")
public class CameraState implements Identifiable {

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

  /**
   * ORMLite needs a no-argument constructor.
   */
  CameraState() {
  }

  /**
   * Creates a CameraState object with settings for position, zoom and focus as well as id. The
   * different settings can not be null.
   *
   * @throws java.lang.IllegalArgumentException if either of the arguments are null.
   */
  public CameraState(int id, Position position, Zoom zoom, Focus focus) {
    this(position, zoom, focus);
    this.id = id;
  }

  /**
   * Creates a CameraState object without an id.
   *
   * @see #CameraState(int, Position, Zoom, Focus)
   */
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

  /**
   * Returns the unique id for the CameraState object.
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the position for the CameraState.
   */
  public Position getPosition() {
    return position;
  }

  /**
   * Returns the zoom for the CameraState.
   */
  public Zoom getZoom() {
    return zoom;
  }

  /**
   * Returns the focus for the CameraState.
   */
  public Focus getFocus() {
    return focus;
  }

  /**
   * Returns true if autofocus is active, false otherwise.
   */
  public boolean isAF() {
    return focus.isAutofocus();
  }

  /**
   * Creates a new {@link com.dreamteam.vicam.model.pojo.CameraState.Copy} object.
   */
  public Copy copy() {
    return new Copy();
  }

  /**
   * Overrides toString and returns the new representation of the CameraState object as a String.
   *
   * @return A String representing the CameraState object and its state.
   */
  @Override
  public String toString() {
    return "CameraState{" +
           "id=" + id +
           ", position=" + position +
           ", zoom=" + zoom +
           ", focus=" + focus +
           '}';
  }

  /**
   * This class is used for copying {@link com.dreamteam.vicam.model.pojo.CameraState} objects and
   * making changes to them. Returns the new object with the commit method.
   *
   * @author Fredrik Sommar
   */
  public class Copy {

    private Position cPosition;
    private Zoom cZoom;
    private Focus cFocus;


    /**
     * The constructor copies the exact same state that the {@link com.dreamteam.vicam.model.pojo.CameraState}
     * has.
     */
    private Copy() {
      this.cPosition = position;
      this.cZoom = zoom;
      this.cFocus = focus;
    }

    /**
     * Changes the position of the copy.
     */
    public Copy position(Position position) {
      this.cPosition = position;
      return this;
    }

    /**
     * Changes the zoom of the copy.
     */
    public Copy zoom(Zoom zoom) {
      this.cZoom = zoom;
      return this;
    }

    /**
     * Changes the focus of the copy.
     */
    public Copy focus(Focus focus) {
      this.cFocus = focus;
      return this;
    }

    /**
     * Returns a new {@link com.dreamteam.vicam.model.pojo.CameraState} with the updated fields.
     */
    public CameraState commit() {
      return new CameraState(id, cPosition, cZoom, cFocus);
    }
  }

}
