package com.dreamteam.vicam.model.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fsommar on 2014-04-01.
 */
@DatabaseTable(tableName = "cameras")
public class Camera {

  @DatabaseField(canBeNull = false)
  private String ip;
  @DatabaseField(unique = true, canBeNull = false)
  private String name;
  @DatabaseField
  private short port;
  @DatabaseField(generatedId = true)
  private int id;

  public Camera() {
    // ORMLite needs a no-arg constructor
  }

  public Camera(String ip, String name, short port, int id) {
    this.ip = ip;
    this.name = name;
    this.port = port;
    this.id = id;
  }

  public Camera(String ip, String name, short port) {
    this.ip = ip;
    this.name = name;
    this.port = port;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public short getPort() {
    return port;
  }

  public void setPort(short port) {
    this.port = port;
  }

  public int getId() {
    return id;
  }
}
