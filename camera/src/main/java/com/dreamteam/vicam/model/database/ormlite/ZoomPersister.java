package com.dreamteam.vicam.model.database.ormlite;

import com.dreamteam.vicam.model.pojo.Zoom;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.IntegerObjectType;

import java.sql.SQLException;

/**
 * Created by Daniel on 2014-04-11.
 */
public class ZoomPersister extends IntegerObjectType {

  private static ZoomPersister singleton;

  private ZoomPersister() {
    super(SqlType.INTEGER, new Class<?>[]{Zoom.class});
  }

  public static ZoomPersister getSingleton() {
    if (singleton == null) {
      singleton = new ZoomPersister();
    }
    return singleton;
  }

  @Override
  public Object javaToSqlArg(FieldType fieldType, Object javaObject) throws SQLException {
    return ((Zoom) javaObject).getLevel();
  }

  @Override
  public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos)
      throws SQLException {
    if (sqlArg == null) {
      return null;
    } else {
      return new Zoom((int) sqlArg);
    }
  }
}
