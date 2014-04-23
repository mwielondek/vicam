package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.model.interfaces.Identifiable;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fsommar on 2014-04-01.
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

  Focus() {
    // ORMLite needs a no-arg constructor
  }

  public Focus(int id, int level, boolean autofocus) {
    this(level, autofocus);
    this.id = id;
  }

  public Focus(int level, boolean autofocus) {
    Utils.rangeCheck(level, LOWER_BOUND, UPPER_BOUND);
    this.level = level;
    this.autofocus = autofocus;
  }

  public int getId() {
    return id;
  }

  public boolean isAutofocus() {
    return autofocus;
  }

  public int getLevel() {
    return level;
  }

  @Override
  public String toString() {
    return "Focus{" +
           "id=" + id +
           ", level=" + level +
           ", autofocus=" + autofocus +
           '}';
  }
}
