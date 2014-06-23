package com.dreamteam.vicam.model.database.ormlite;

import com.dreamteam.vicam.model.pojo.Zoom;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.IntegerObjectType;

import java.sql.SQLException;

/**
 * Used for {@link com.dreamteam.vicam.model.pojo.Zoom} by ORMLite in order to represent the {@link
 * com.dreamteam.vicam.model.pojo.Zoom} object as an integer in the database.
 *
 * @author Daniel Millevik
 * @since 2014-04-11.
 */
public class ZoomPersister extends IntegerObjectType {

  private static ZoomPersister singleton;

  private ZoomPersister() {
    super(SqlType.INTEGER, new Class<?>[]{Zoom.class});
  }

  /**
   * ORMLite uses reflection to find this method and therefore its signature can't be changed!
   */
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
