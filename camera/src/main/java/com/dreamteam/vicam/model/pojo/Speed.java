package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.presenter.utility.Utils;

/**
 * Created by fsommar on 2014-04-01.
 */
public class Speed {

  public static final int LOWER_BOUND = 1, UPPER_BOUND = 99, STOP = 50;

  private int x, y;

  public Speed(int x, int y) {
    Utils.rangeCheck(x, LOWER_BOUND, UPPER_BOUND);
    Utils.rangeCheck(y, LOWER_BOUND, UPPER_BOUND);
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
