package com.dreamteam.vicam.model.pojo;

/**
 * Created by fsommar on 2014-04-01.
 */
public class Position {

  private int pan, tilt, id;

  public Position(int pan, int tilt, int id) {
    this.pan = pan;
    this.tilt = tilt;
    this.id = id;
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
