package com.dreamteam.vicam.model.pojo;

/**
 * Created by fsommar on 2014-04-01.
 */
public class Zoom {

  public static final int LOWER_BOUND = 0x555, UPPER_BOUND = 0xFFF;

  private int level;

  public Zoom(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
}
