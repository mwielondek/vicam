package com.dreamteam.vicam.model.pojo;

/**
 * Created by fsommar on 2014-04-01.
 */
public class Focus {

  private int id;
  private int level;
  private boolean autoFocus;

  public Focus(int level, boolean autoFocus) {
    this.level = level;
    this.autoFocus = autoFocus;
  }

  public Focus(int id, int level, boolean autoFocus) {

    this.id = id;
    this.level = level;
    this.autoFocus = autoFocus;
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
