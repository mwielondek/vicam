package com.dreamteam.vicam.presenter.utility;

import java.util.Scanner;

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

  public static String streamToString(java.io.InputStream is) {
    Scanner s = new Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }
}
