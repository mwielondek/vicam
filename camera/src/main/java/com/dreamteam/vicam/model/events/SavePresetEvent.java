package com.dreamteam.vicam.model.events;

import android.content.DialogInterface;

/**
 * Created by fsommar on 2014-04-28.
 */
public class SavePresetEvent {

  public final DialogInterface dialog;
  public final String name;

  public SavePresetEvent(DialogInterface dialog, String name) {
    this.dialog = dialog;
    this.name = name;
  }
}
