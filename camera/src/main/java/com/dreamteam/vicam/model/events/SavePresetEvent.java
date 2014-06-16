package com.dreamteam.vicam.model.events;

import android.content.DialogInterface;

/**
 * This event fires when the save preset dialog is finished.
 *
 * @author Fredrik Sommar
 * @since 2014-04-28.
 */
public class SavePresetEvent {

  /**
   * The {@link android.content.DialogInterface} object from where the save event originated.
   */
  public final DialogInterface dialog;
  /**
   * The name of the preset to save.
   */
  public final String name;

  /**
   * An event indicating that a {@link com.dreamteam.vicam.model.pojo.Preset} object should be
   * saved.
   *
   * @param dialog The dialog where the event originated.
   * @param name   The name to save the preset with.
   */
  public SavePresetEvent(DialogInterface dialog, String name) {
    this.dialog = dialog;
    this.name = name;
  }
}
