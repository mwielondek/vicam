package com.dreamteam.vicam.presenter.utility;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by fsommar on 2014-04-12.
 */
public class Utils {

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

  public static class ORMLite {

    public static <T> boolean insert(Dao<T, ?> dao, T obj) {
      int insertRows = 0;
      try {
        insertRows = dao.create(obj);
      } catch (SQLException ignored) {
      }
      return insertRows != 0;
    }

    public static <T, ID> T find(Dao<T, ID> dao, ID id) {
      try {
        return dao.queryForId(id);
      } catch (SQLException e) {
        return null;
      }
    }

    public static <T> boolean update(Dao<T, ?> dao, T obj) {
      int updatedRows = 0;
      try {
        updatedRows = dao.update(obj);
      } catch (SQLException ignored) {
      }
      return updatedRows != 0;
    }

    public static <T, ID> boolean delete(Dao<T, ID> dao, ID id) {
      int deletedRows = 0;
      try {
        deletedRows = dao.deleteById(id);
      } catch (SQLException ignored) {
      }
      return deletedRows != 0;
    }

    public static <T> List<T> getAll(Dao<T, ?> dao) {
      try {
        return dao.queryForAll();
      } catch (SQLException e) {
        return null;
      }
    }

  }
}
