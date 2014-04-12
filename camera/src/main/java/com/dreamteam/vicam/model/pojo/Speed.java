package com.dreamteam.vicam.model.pojo;

/**
 * Created by fsommar on 2014-04-01.
 */
public class Speed {

  public static final int LOWER_BOUND = 1, UPPER_BOUND = 99, STOP = 50;

  private int x, y;

  public Speed(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
