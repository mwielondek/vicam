package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.presenter.utility.Utils;

/**
 * The Speed class type is used as a parameter for different {@link com.dreamteam.vicam.presenter.network.camera.CameraFacade}
 * methods to represent a camera's movement speed in both axes.
 *
 * @author Milosz Wielondek
 * @author Daniel Millevik
 * @since 2014-04-01.
 */
public class Speed {

  public static final int LOWER_BOUND = 1, UPPER_BOUND = 99, STOP = 50;

  private int x, y;

  /**
   * Creates a Speed object with the specified arguments for movement speed on both axes.
   *
   * @throws java.lang.IllegalArgumentException if the parameters are not within accepted bounds.
   */
  public Speed(int x, int y) {
    Utils.rangeCheck(x, LOWER_BOUND, UPPER_BOUND);
    Utils.rangeCheck(y, LOWER_BOUND, UPPER_BOUND);
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the movement speed for the x-axis.
   */
  public int getX() {
    return x;
  }

  /**
   * Returns the movement speed for the y-axis.
   */
  public int getY() {
    return y;
  }
}
