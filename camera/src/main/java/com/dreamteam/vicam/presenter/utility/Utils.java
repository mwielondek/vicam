package com.dreamteam.vicam.presenter.utility;

/**
 * Created by fsommar on 2014-04-12.
 */
public class Utils {

  public static void rangeCheck(int param, int lower, int upper) {
    if (param < lower || param > upper) {
      throw new IllegalArgumentException(
          String.format("Parameter needs to be in range [%d, %d] - was %d.", lower, upper, param));
    }
  }
}
