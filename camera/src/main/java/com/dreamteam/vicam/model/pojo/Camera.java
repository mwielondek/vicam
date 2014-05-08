package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.model.interfaces.Identifiable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fsommar on 2014-04-01.
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
  private Short port;
  @DatabaseField(columnName = "invert_x", canBeNull = false)
  private boolean invertX;
  @DatabaseField(columnName = "invert_y", canBeNull = false)
  private boolean invertY;

  Camera() {
    // ORMLite needs a no-arg constructor
  }

  public Camera(int id, String ip, String name, Short port, boolean invertX, boolean invertY) {
    this(ip, name, port, invertX, invertY);
    this.id = id;
  }

  public Camera(String ip, String name, Short port) {
    this(ip, name, port, false, false);
  }

  public Camera(String ip, String name, Short port, boolean invertX, boolean invertY) {
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

  public String getIp() {
    return ip;
  }

  public String getName() {
    return name;
  }

  public Short getPort() {
    return port;
  }

  public int getId() {
    return id;
  }

  public boolean isInvertX() {
    return invertX;
  }

  public boolean isInvertY() {
    return invertY;
  }

  public String getAddress() {
    if (port == null) {
      return String.format("http://%s", ip);
    }
    return String.format("http://%s:%d", ip, port);
  }

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

  public static Short parsePort(String port) {
      try {
        return Short.parseShort(port);
      } catch (NumberFormatException ignored) {
        return null;
      }
  }
}
