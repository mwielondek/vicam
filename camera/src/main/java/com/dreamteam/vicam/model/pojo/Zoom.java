package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.presenter.utility.Utils;

/**
 * Created by fsommar on 2014-04-01.
 */
public class Zoom {

  public static final int LOWER_BOUND = 0x555, UPPER_BOUND = 0xFFF;

  private int level;

  public Zoom(int level) {
    Utils.rangeCheck(level, LOWER_BOUND, UPPER_BOUND);
    this.level = level;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  @Override
  public String toString() {
    return "Zoom{" +
           "level=" + level +
           '}';
  }
}
