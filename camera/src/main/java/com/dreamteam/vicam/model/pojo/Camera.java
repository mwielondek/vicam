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

  Camera() {
    // ORMLite needs a no-arg constructor
  }

  public Camera(int id, String ip, String name, Short port) {
    this(ip, name, port);
    this.id = id;
  }

  public Camera(String ip, String name, Short port) {
    if (ip == null || name == null) {
      throw new IllegalArgumentException(String.format(
          "Name(%s) or IP address(%s) can't be null.", name, ip));
    }
    this.ip = ip;
    this.name = name;
    this.port = port;
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

  public String getAddress() {
    if (port == null) {
      return String.format("http://%s", ip);
    }
    return String.format("http://%s:%d", ip, port);
  }

  public Camera copyWithName(String newName) {
    return new Camera(id, ip, newName, port);
  }

  @Override
  public String toString() {
    return getName();
  }
}
