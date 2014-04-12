package com.dreamteam.vicam.model.pojo;

import com.dreamteam.vicam.presenter.utility.Utils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fsommar on 2014-04-01.
 */
@DatabaseTable
public class Focus {

  public static final int LOWER_BOUND = 0x555, UPPER_BOUND = 0xFFF;

  @DatabaseField(generatedId = true)
  private int id;
  @DatabaseField
  private int level;
  @DatabaseField
  private boolean autoFocus;

  public Focus() {
    // ORMLite needs a no-arg constructor
  }

  public Focus(int level, boolean autoFocus) {
    Utils.rangeCheck(level, LOWER_BOUND, UPPER_BOUND);
    this.level = level;
    this.autoFocus = autoFocus;
  }

  public Focus(int id, int level, boolean autoFocus) {
    this(level, autoFocus);
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public boolean isAutoFocus() {
    return autoFocus;
  }

  public void setAutoFocus(boolean autoFocus) {
    this.autoFocus = autoFocus;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
}
