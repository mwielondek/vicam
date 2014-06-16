package com.dreamteam.vicam.model.events;

import android.content.DialogInterface;

/**
 * This event fires when the save camera dialog is finished.
 *
 * @author Fredrik Sommar
 * @since 2014-05-04.
 */
public class SaveCameraEvent {

  /**
   * The dialog where the event originated.
   */
  public final DialogInterface dialog;
  /**
   * The name of the camera.
   */
  public final String name;
  /**
   * The IP address of the camera.
   */
  public final String ip;
  /**
   * The port of the camera.
   */
  public final String port;
  /**
   * An indication of whether the x-axis is inverted or not.
   */
  public final boolean invertX;
  /**
   * An indication of whether the y-axis is inverted or not.
   */
  public final boolean invertY;

  /**
   * An event indicating that a {@link com.dreamteam.vicam.model.pojo.Camera} object should be
   * saved.
   *
   * @param dialog  The dialog where the event originated.
   * @param name    The name of the camera.
   * @param ip      The ip address of the camera.
   * @param port    The port of the camera.
   * @param invertX An indication of whether the x-axis is inverted or not.
   * @param invertY An indication of whether the y-axis is inverted or not.
   */
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
