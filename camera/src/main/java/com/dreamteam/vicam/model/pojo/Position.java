package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.model.interfaces.Identifiable;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * The Position class represents the cameras position in both axes.
 * Uses ORMLite to store the object as a table in a database.
 * 
 * @author Milosz Wielondek
 * @author Daniel Millevik
 * @since 2014-04-01.
 */
@DatabaseTable(tableName = "position")
public class Position implements Identifiable {

  public static final int LOWER_BOUND = 0x0000, UPPER_BOUND = 0xFFFF, MID = 0x8000;

  @DatabaseField(columnName = "id", generatedId = true)
  private int id = -1;
  @DatabaseField(columnName = "pan")
  private int pan;
  @DatabaseField(columnName = "tilt")
  private int tilt;

  /**
   * ORMLite needs a no-argument constructor.
   */
  Position() {
  }

  /**
   * Creates a Position object with an id and sets coordinates indicating camera position.
   * The coordinates should be withing accepted bounds.
   * Throws IllegalArgumentExeption otherwise.
   */
  public Position(int id, int pan, int tilt) {
    this(pan, tilt);
    this.id = id;
  }

  /**
   * Creates a Position object with set coordinates indicating camera position.
   * The coordinates should be withing accepted bounds.
   * Throws IllegalArgumentException otherwise.
   */
  public Position(int pan, int tilt) {
    Utils.rangeCheck(pan, LOWER_BOUND, UPPER_BOUND);
    Utils.rangeCheck(tilt, LOWER_BOUND, UPPER_BOUND);
    this.pan = pan;
    this.tilt = tilt;
  }

  
  /**
   * Returns the unique id for the Position object.
   * 
   * @return The id as an int.
   */
  public int getId() {
    return id;
  }

  /**
   * Overrides toString and returns the new representation of the Position object as a String.
   * 
   * @return A String representing the Position object and its state.
   */
  @Override
  public String toString() {
    return "Position{" +
           "id=" + id +
           ", pan=" + pan +
           ", tilt=" + tilt +
           '}';
  }

  /**
   * Returns the camera's x-axis position.
   */
  public int getPan() {
    return pan;
  }

  /**
   * Returns the camera's y-axis position.
   */
  public int getTilt() {
    return tilt;
  }
}
