package com.dreamteam.vicam.presenter.utility;

import android.util.Log;

import com.dreamteam.vicam.model.interfaces.Identifiable;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by fsommar on 2014-04-12.
 */
public class Utils {

  public static final long DELAY_TIME_MILLIS = 130;

  public static void rangeCheck(int param, int lower, int upper) {
    if (param < lower || param > upper) {
      throw new IllegalArgumentException(
          String.format("Parameter needs to be in range [%d, %d] - was %d.", lower, upper, param));
    }
  }

  public static String streamToString(java.io.InputStream is) {
    Scanner s = new Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }

  public static void databaseLog(String msg, Throwable e) {
    if (Constants.DEBUG) {
      Log.e(Constants.DATABASE_TAG, msg, e);
    }
  }

  public static class ORMLite {

    public static <T extends Identifiable> int insert(Dao<T, ?> dao, T obj) {
      try {
        dao.create(obj);
        return obj.getId();
      } catch (SQLException e) {
        databaseLog(String.format("Failed inserting obj(%s) into database", obj), e);
        return -1;
      }
    }

    public static <T, ID> T find(Dao<T, ID> dao, ID id) {
      try {
        return dao.queryForId(id);
      } catch (SQLException e) {
        databaseLog(String.format("Failed finding an object with id=%s in database", id), e);
        return null;
      }
    }

    public static <T extends Identifiable> boolean update(Dao<T, ?> dao, T obj) {
      try {
        dao.update(obj);
        return true;
      } catch (SQLException e) {
        databaseLog(String.format("Failed updating obj(%s) in database", obj), e);
        return false;
      }
    }

    public static <T, ID> boolean delete(Dao<T, ID> dao, ID id) {
      try {
        dao.deleteById(id);
        return true;
      } catch (SQLException e) {
        databaseLog(String.format("Failed deleting an obj with id=%s in database", id), e);
        return false;
      }
    }

    public static <T> List<T> getAll(Dao<T, ?> dao) {
      try {
        return dao.queryForAll();
      } catch (SQLException e) {
        databaseLog("Failed querying all objects from database", e);
        return null;
      }
    }

  }
}
