package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.model.interfaces.Identifiable;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * The Focus class represents the level of the camera's focus as well as keeps track of autofocus.
 * Uses ORMLite to store the object as a table in a database.
 * 
 * @author Milosz Wielondek
 * @author Daniel Millevik
 * @since 2014-04-01.
 */
@DatabaseTable(tableName = "focus")
public class Focus implements Identifiable {

  public static final int LOWER_BOUND = 0x555, UPPER_BOUND = 0xFFF;

  @DatabaseField(columnName = "id", generatedId = true)
  private int id = -1;
  @DatabaseField(columnName = "level")
  private int level;
  @DatabaseField(columnName = "autofocus")
  private boolean autofocus;

  /**
   * ORMLite needs a no-argument constructor.
   */
  Focus() {
  }

  /**
   * Creates a Focus object with set level and autofocus as well as an id.
   * The level should be withing accepted bounds.
   * @throws java.lang.IllegalArgumentException if the level is out of bounds.
   */
  public Focus(int id, int level, boolean autofocus) {
    this(level, autofocus);
    this.id = id;
  }

  /**
   * Creates a Focus object without an id.
   * @see #Focus(int, int, boolean)
   */
  public Focus(int level, boolean autofocus) {
    Utils.rangeCheck(level, LOWER_BOUND, UPPER_BOUND);
    this.level = level;
    this.autofocus = autofocus;
  }

  /**
   * Returns the unique id for the Focus object.
   */
  public int getId() {
    return id;
  }

  /**
   * Returns true if the autofocus is active, false otherwise.
   */
  public boolean isAutofocus() {
    return autofocus;
  }

  /**
   * Returns the focus level to the caller.
   */
  public int getLevel() {
    return level;
  }

  /**
   * Overrides toString and returns the new representation of the Focus object as a String.
   * 
   * @return A String representing the Focus object and its state.
   */
  @Override
  public String toString() {
    return "Focus{" +
           "id=" + id +
           ", level=" + level +
           ", autofocus=" + autofocus +
           '}';
  }
}
