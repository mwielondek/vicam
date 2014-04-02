package com.dreamteam.vicam.model.pojo;

import *;

/**
 * Created by fsommar on 2014-04-01.
 */
public class CameraState {
    private Position position;
    private Zoom zoom;
    private Brightness brightness;
    private Focus focus;

    public CameraState(Position position, Zoom zoom, Brightness brightness, Focus focus) {
        this.position = position;
        this.zoom = zoom;
        this.brightness = brightness;
        this.focus = focus;
    }

    public Position getPosition() {
        return position;
    }

    public Zoom getZoom() {
        return zoom;
    }

    public Brightness getBrightness() {
        return brightness;
    }

    public Focus getFocus() {
        return focus;
    }
}
