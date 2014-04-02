package com.dreamteam.vicam.model.pojo;

/**
 * Created by fsommar on 2014-04-01.
 */
public class Focus {

    private boolean autoFocus;
    private float level;
    private int id;

    public Focus(boolean autoFocus, float level, int id) {
        this.autoFocus = autoFocus;
        this.level = level;
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

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        this.level = level;
    }
}
