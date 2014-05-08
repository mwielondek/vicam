package com.dreamteam.vicam.model.events;

import android.content.DialogInterface;

/**
 * Created by fsommar on 2014-05-04.
 */
public class SaveCameraEvent {

  public final DialogInterface dialog;
  public final String name;
  public final String ip;
  public final String port;
  public final boolean invertX;
  public final boolean invertY;

  public SaveCameraEvent(DialogInterface dialog, String name, String ip, String port,
                         boolean invertX, boolean invertY) {
    this.dialog = dialog;
    this.name = name;
    this.ip = ip;
    this.port = port;
    this.invertX = invertX;
    this.invertY = invertY;
  }
}
