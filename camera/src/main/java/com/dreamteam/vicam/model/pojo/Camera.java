package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.model.interfaces.Identifiable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Locale;

/**
 * @author Milosz Wielondek
 * @author Daniel Millevik
 * @since 2014-04-01.
 */
@DatabaseTable(tableName = "camera")
public class Camera implements Identifiable {

  @DatabaseField(columnName = "id", generatedId = true)
  private int id = -1;
  @DatabaseField(columnName = "ip", canBeNull = false)
  private String ip;
  @DatabaseField(columnName = "name", unique = true, canBeNull = false)
  private String name;
  @DatabaseField(columnName = "port")
  private Integer port;
  @DatabaseField(columnName = "invert_x", canBeNull = false)
  private boolean invertX;
  @DatabaseField(columnName = "invert_y", canBeNull = false)
  private boolean invertY;

  /**
   * ORMLite needs a no-argument constructor.
   */
  Camera() {
  }

  /**
   * Creates a Camera object initiating the Camera's state as well as the Camera id.
   * The Camera's name and ip can not be null.
   * @throws {@link IllegalArgumentException}.
   */
  public Camera(int id, String ip, String name, Integer port, boolean invertX, boolean invertY) {
    this(ip, name, port, invertX, invertY);
    this.id = id;
  }

  /**
   * Creates a Camera object initiating the Camera's state, inverted movements is deactivated.
   * The Camera's name and ip can not be null.
   * @throws {@link IllegalArgumentException}.
   */
  public Camera(String ip, String name, Integer port) {
    this(ip, name, port, false, false);
  }

  /**
   * Creates a Camera object initiating the Camera's state.
   * The Camera's name and ip can not be null.
   * @throws {@link IllegalArgumentException}.
   */
  public Camera(String ip, String name, Integer port, boolean invertX, boolean invertY) {
    if (ip == null || name == null) {
      throw new IllegalArgumentException(String.format(
          "Name(%s) or IP address(%s) can't be null.", name, ip));
    }
    this.ip = ip;
    this.name = name;
    this.port = port;
    this.invertX = invertX;
    this.invertY = invertY;
  }

  /**
   * Returns the Camera's ip
   */
  public String getIp() {
    return ip;
  }

  /**
   * Returns the Camera's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Return the Camera's port.
   */
  public Integer getPort() {
    return port;
  }

  /**
   * Returns the unique id for the Position object.
   * 
   * @return The id as an int.
   */
  public int getId() {
    return id;
  }

  /**
   * Return a boolean indicating if the Camera's movement on the x-axis is inverted or not.
   */
  public boolean isInvertX() {
    return invertX;
  }

  /**
   * Return a boolean indicating if the Camera's movement on the y-axis is inverted or not.
   */
  public boolean isInvertY() {
    return invertY;
  }

  /**
   * Returns a formatted String of the Camera's web address
   */
  public String getAddress() {
    if (port == null) {
      return String.format(Locale.US, "http://%s", ip);
    }
    return String.format(Locale.US, "http://%s:%d", ip, port);
  }

  /**
   * Overrides toString and returns the new representation of the Camera object as a String.
   * 
   * @return A String representing the Camera object and its state.
   */
  @Override
  public String toString() {
    return "Camera{" +
           "id=" + id +
           ", ip='" + ip + '\'' +
           ", name='" + name + '\'' +
           ", port=" + port +
           ", invertX=" + invertX +
           ", invertY=" + invertY +
           '}';
  }

  /**
   * Parses the port from a String to an Integer and returns it.
   */
  public static Integer parsePort(String port) {
      try {
        return Integer.parseInt(port);
      } catch (NumberFormatException ignored) {
        return null;
      }
  }
}
