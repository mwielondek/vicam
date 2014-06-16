package com.dreamteam.vicam.presenter.utility;

import com.google.common.io.Files;

import android.os.Environment;
import android.util.Log;

import com.dreamteam.vicam.model.interfaces.Identifiable;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import rx.Observable;
import rx.functions.Action1;

/**
 * A collection of utility functions that are generic enough to not belong anywhere else.
 *
 * @author Fredrik Sommar
 * @since 2014-04-12
 */
public class Utils {

  /**
   * Throws an {@link java.lang.IllegalArgumentException} if value is outside range {@code [lower,
   * upper]}.
   */
  public static void rangeCheck(int value, int lower, int upper) {
    if (value < lower || value > upper) {
      throw new IllegalArgumentException(
          String.format("Value needs to be in range [%d, %d] - was %d.", lower, upper, value));
    }
  }

  /**
   * Takes an integer value and returns a {@link java.lang.String} of the value padded it with the
   * given amount of zeroes.
   *
   * @param value         The value to be padded.
   * @param decimalPlaces Number of zeroes to pad with.
   * @return A zero-padded {@link java.lang.String}.
   */
  public static String padZeroes(int value, int decimalPlaces) {
    return String.format("%0" + decimalPlaces + "d", value);
  }

  /**
   * Takes a integer value and returns a {@link java.lang.String} of the value converted to base 16
   * (hexadecimal) and padded with the given amount of zeroes.
   *
   * @param hexValue      The value to be padded and converted.
   * @param decimalPlaces Number of zeroes to pad with.
   * @return A zero-padded hexadecimal representation of the passed value as a {@link
   * java.lang.String}.
   */
  public static String padZeroesHex(int hexValue, int decimalPlaces) {
    return String.format("%0" + decimalPlaces + "X", hexValue);
  }

  /**
   * Parses a {@link java.lang.String} strictly containing a hexadecimal number and returns the
   * integer represented by the {@link java.lang.String}.
   *
   * @throws java.lang.NumberFormatException If the provided {@link java.lang.String} is not a valid
   *                                         formatted number.
   */
  public static int parseHex(String hexString) {
    return Integer.parseInt(hexString, 16);
  }

  /**
   * Returns a no operation {@link rx.functions.Action1} useful when subscribing to {@link
   * rx.Observable Observables} and the result isn't needed.
   */
  public static <T> Action1<T> noop() {
    return new Action1<T>() {
      @Override
      public void call(T t) {
      }
    };
  }

  /**
   * Converts an {@link java.io.InputStream} to a {@link java.lang.String}. <br/>If unsuccessful an
   * empty {@link java.lang.String} will be returned.
   */
  public static String streamToString(java.io.InputStream is) {
    Scanner s = new Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }

  /**
   * Grabs the stack trace from the supplied {@link java.lang.Throwable} and turns it into a {@link
   * java.lang.String}.
   */
  public static String throwableToString(Throwable throwable) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    throwable.printStackTrace(pw);
    return sw.toString();
  }

  /**
   * Acknowledges an error has occurred when using the database by logging it.
   */
  public static void databaseError(String msg, Throwable e) {
    Log.e(Constants.DATABASE_TAG, msg, e);
  }

  /**
   * Used for logging general application errors.
   */
  public static void errorLog(String s) {
    Log.e(Constants.TAG, s);
  }

  /**
   * Used for logging debug messages.
   */
  public static void debugLog(String s) {
    if (Constants.DEBUG) {
      Log.d(Constants.TAG, s);
    }
  }

  /**
   * Contains functions useful when constructing generic Data Access Objects for the ORMLite
   * library.
   *
   * @author Fredrik Sommar
   * @since 2014-04-12
   */
  public static class ORMLite {

    /**
     * Uses the Data Access Object to insert an object into the database.
     *
     * @return An {@link rx.Observable} with the id of the inserted object, or if unsuccessful an
     * {@link rx.Observable} error with the {@link java.sql.SQLException} that was thrown.
     */
    public static <T extends Identifiable> Observable<Integer> insert(Dao<T, ?> dao, T obj) {
      try {
        dao.create(obj);
        return Observable.just(obj.getId());
      } catch (SQLException e) {
        databaseError(String.format("Failed inserting obj(%s) into database", obj), e);
        return Observable.error(e);
      }
    }


    /**
     * Finds the object with the given id using the provided Data Access Object.
     *
     * @return An {@link rx.Observable} with the queried object, or if unsuccessful an {@link
     * rx.Observable} error with the {@link java.sql.SQLException} that was thrown.
     */
    public static <T, ID> Observable<T> find(Dao<T, ID> dao, ID id) {
      try {
        return Observable.just(dao.queryForId(id));
      } catch (SQLException e) {
        databaseError(String.format("Failed finding an object with id=%s in database", id), e);
        return Observable.error(e);
      }
    }

    /**
     * Updates the given object using the provided Data Access Object. Make sure that whatever field
     * is used as key exists on the provided object.
     *
     * @return An {@link rx.Observable} with {@code true}, or if unsuccessful an {@link
     * rx.Observable} error with the {@link java.sql.SQLException} that was thrown.
     */
    public static <T extends Identifiable> Observable<Boolean> update(Dao<T, ?> dao, T obj) {
      try {
        dao.update(obj);
        return Observable.just(true);
      } catch (SQLException e) {
        databaseError(String.format("Failed updating obj(%s) in database", obj), e);
        return Observable.error(e);
      }
    }

    /**
     * Deletes the object with the given id using the provided Data Access Object.
     *
     * @return An {@link rx.Observable} with {@code true}, or if unsuccessful an {@link
     * rx.Observable} error with the {@link java.sql.SQLException} that was thrown.
     */
    public static <T, ID> Observable<Boolean> delete(Dao<T, ID> dao, ID id) {
      try {
        dao.deleteById(id);
        return Observable.just(true);
      } catch (SQLException e) {
        databaseError(String.format("Failed deleting an obj with id=%s in database", id), e);
        return Observable.error(e);
      }
    }

    /**
     * Gets all objects related to the provided Data Access Object.
     *
     * @return An {@link rx.Observable} with a list of the objects, or an {@link rx.Observable}
     * error with the {@link java.sql.SQLException} that was thrown.
     */
    public static <T> Observable<List<T>> getAll(Dao<T, ?> dao) {
      try {
        return Observable.just(dao.queryForAll());
      } catch (SQLException e) {
        databaseError("Failed querying all objects from database", e);
        return Observable.error(e);
      }
    }
  }

  /**
   * Contains functions for importing and exporting settings as a database file.
   *
   * @author Fredrik Sommar
   * @since 2014-05-14
   */
  public static class DatabaseSync {

    /**
     * Imports a database file from the external SD-card. <br/>For the base path {@code
     * BACKUP_FOLDER_PATH} in {@link com.dreamteam.vicam.presenter.utility.Constants} is
     * used.<br/><br/> The final path will be {@code SD-card/BACKUP_FOLDER_PATH/importName}.
     *
     * @param importName The name of the file to be imported.
     * @return The path from the SD-card to the imported file, or null if unsuccessful.
     */
    public static String importDb(String importName) {
      try {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        File backupFolder = new File(sd, Constants.BACKUP_FOLDER_PATH);

        if (sd.canWrite() && backupFolder.exists()) {
          File internalDb = new File(data, Constants.INTERNAL_DB_PATH);

          final String settingsPath = Constants.BACKUP_FOLDER_PATH + importName;
          File importDb = new File(sd, settingsPath);

          if (importDb.exists()) {
            Files.copy(importDb, internalDb); // overwrite internal db with imported db
            return settingsPath;
          }
        }
      } catch (Exception e) {
        Utils.databaseError("Error occurred while importing '" + importName + "'", e);
      }
      return null;
    }

    /**
     * Exports a database file to the external SD-card. <br/>For the base path {@code
     * BACKUP_FOLDER_PATH} in {@link com.dreamteam.vicam.presenter.utility.Constants} is
     * used.<br/><br/> The final path will be {@code SD-card/BACKUP_FOLDER_PATH/importName}.
     *
     * @param exportName The name of the file to be exported.
     * @return The path from the SD-card to the exported file, or null if unsuccessful.
     */
    public static String exportDb(String exportName) {
      try {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();

        if (sd.canWrite()) {
          File backupFolder = new File(sd, Constants.BACKUP_FOLDER_PATH);

          if (backupFolder.exists() || backupFolder.mkdir()) {
            File internalDb = new File(data, Constants.INTERNAL_DB_PATH);

            final String settingsPath = Constants.BACKUP_FOLDER_PATH + exportName;
            File exportDb = new File(sd, settingsPath);

            if (exportDb.exists() || exportDb.createNewFile()) {
              Files.copy(internalDb, exportDb); // export internal db to export db location
              return settingsPath;
            }
          }
        }
      } catch (Exception e) {
        Utils.databaseError("Error occurred while exporting '" + exportName + "'", e);
      }
      return null;
    }
  }

}
