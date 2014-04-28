package com.dreamteam.vicam.model.events;

import android.content.DialogInterface;

/**
 * Created by fsommar on 2014-04-28.
 */
public class PresetSaveEvent {

  public final DialogInterface dialog;
  public final String name;

  public PresetSaveEvent(DialogInterface dialog, String name) {
    this.dialog = dialog;
    this.name = name;
  }
}
