package com.dreamteam.vicam.model.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fsommar on 2014-04-01.
 */
@DatabaseTable
public class Position {

  @DatabaseField
  private int pan;
  @DatabaseField
  private int tilt;
  @DatabaseField(generatedId = true)
  private int id;

  public Position() {
    // ORMLite needs a no-arg constructor
  }

  public Position(int pan, int tilt, int id) {
    this.pan = pan;
    this.tilt = tilt;
    this.id = id;
  }

  public Position(int pan, int tilt) {
    this.pan = pan;
    this.tilt = tilt;
  }

  public int getId() {
    return id;
  }

  public int getPan() {
    return pan;
  }

  public void setPan(int pan) {
    this.pan = pan;
  }

  public int getTilt() {
    return tilt;
  }

  public void setTilt(int tilt) {
    this.tilt = tilt;
  }
}
