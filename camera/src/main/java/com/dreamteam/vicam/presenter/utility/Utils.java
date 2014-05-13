package com.dreamteam.vicam.presenter.utility;

import android.util.Log;

import com.dreamteam.vicam.model.interfaces.Identifiable;
import com.j256.ormlite.dao.Dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by fsommar on 2014-04-12.
 */
public class Utils {

  public static final long DELAY_TIME_MILLIS = 130;
  private static final String TAG = "VICAM";

  public static void rangeCheck(int param, int lower, int upper) {
    if (param < lower || param > upper) {
      throw new IllegalArgumentException(
          String.format("Parameter needs to be in range [%d, %d] - was %d.", lower, upper, param));
    }
  }

  public static <T> Action1<T> noop() {
    return new Action1<T>() {
      @Override
      public void call(T t) {
      }
    };
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

  public static String throwableToString(Throwable throwable) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    throwable.printStackTrace(pw);
    return sw.toString();
  }

  public static void errorLog(String s) {
    Log.e(TAG, s);
  }

  public static void infoLog(String s) {
    Log.i(TAG, s);
  }

  public static class ORMLite {

    public static <T extends Identifiable> Observable<Integer> insert(Dao<T, ?> dao, T obj) {
      try {
        dao.create(obj);
        return Observable.just(obj.getId());
      } catch (SQLException e) {
        databaseLog(String.format("Failed inserting obj(%s) into database", obj), e);
        return Observable.error(e);
      }
    }

    public static <T, ID> Observable<T> find(Dao<T, ID> dao, ID id) {
      try {
        return Observable.just(dao.queryForId(id));
      } catch (SQLException e) {
        databaseLog(String.format("Failed finding an object with id=%s in database", id), e);
        return Observable.error(e);
      }
    }

    public static <T extends Identifiable> Observable<Boolean> update(Dao<T, ?> dao, T obj) {
      try {
        dao.update(obj);
        return Observable.just(true);
      } catch (SQLException e) {
        databaseLog(String.format("Failed updating obj(%s) in database", obj), e);
        return Observable.error(e);
      }
    }

    public static <T, ID> Observable<Boolean> delete(Dao<T, ID> dao, ID id) {
      try {
        dao.deleteById(id);
        return Observable.just(true);
      } catch (SQLException e) {
        databaseLog(String.format("Failed deleting an obj with id=%s in database", id), e);
        return Observable.error(e);
      }
    }

    public static <T> Observable<List<T>> getAll(Dao<T, ?> dao) {
      try {
        return Observable.just(dao.queryForAll());
      } catch (SQLException e) {
        databaseLog("Failed querying all objects from database", e);
        return Observable.error(e);
      }
    }

  }
}
