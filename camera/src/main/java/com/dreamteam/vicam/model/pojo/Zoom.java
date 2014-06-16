package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.presenter.utility.Utils;

/**
 * This class keeps track of a camera's zoom level.
 * Also makes sure the zoom level stays within the specific bounds.
 * 
 * @author Milosz Wielondek
 * @author Daniel Millevik
 * @since 2014-04-01.
 */
public class Zoom {

  public static final int LOWER_BOUND = 0x555, UPPER_BOUND = 0xFFF;
  public static final int RANGE = UPPER_BOUND - LOWER_BOUND;

  private int level;

  /**
   * Constructor creates a new Zoom object with the level as argument.
   * Throws IllegalArgumentException if the level is not within acceptet bounds. 
   */
  public Zoom(int level) {
    Utils.rangeCheck(level, LOWER_BOUND, UPPER_BOUND);
    this.level = level;
  }

  /**
   * Returns the zoom level to the caller.
   * 
   * @return An int representing the objects zoom level.
   */
  public int getLevel() {
    return level;
  }

  /**
   * Takes a parameter and changes the zoom level of the object accordingly.
   * 
   * @param An int representing the objects zoom level.
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * Overrides toString and returns the new representation of the Zoom object as a String.
   * 
   * @return A String representing the Zoom object and its level.
   */
  @Override
  public String toString() {
    return "Zoom{" +
           "level=" + level +
           '}';
  }
}
