package com.dreamteam.vicam.presenter.utility;

import java.util.Locale;

/**
 * A collection of static constant values used throughout the application.
 *
 * @author Fredrik Sommar
 * @since 2014-04-23
 */
public class Constants {

  /**
   * Used to determine whether debug statements should print or be logged.
   */
  public static final boolean DEBUG = true;
  /**
   * A hardware limitation for the Panasonic AW-HE50 camera is that it cannot accept requests more
   * often than around 130 ms.
   */
  public static final long DELAY_TIME_MILLIS = 130;
  /**
   * A tag used when logging via the Android logger ({@link android.util.Log}).
   */
  public static final String TAG = "VICAM";
  /**
   * A tag used when logging database errors via the Android logger ({@link android.util.Log}).
   */
  public static final String DATABASE_TAG = "VICAM_DB";
  /**
   * The package name for the application. TODO: generate or get it from somewhere so it isn't
   * hardcoded.
   */
  public static final String PACKAGE_NAME = "com.dreamteam.camera";
  /**
   * The name of the database used internally to store data.
   */
  public static final String DATABASE_NAME = "vicamera.db";
  /**
   * The path on the SD-card where the database should be exported to.
   */
  public static final String BACKUP_FOLDER_PATH = "/Vicam/";
  /**
   * The path to the internal database used to store all settings in the application.
   */
  public static final String INTERNAL_DB_PATH =
      String.format(Locale.US, "/data/%s/databases/%s", PACKAGE_NAME, DATABASE_NAME);


}
