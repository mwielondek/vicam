package com.dreamteam.vicam.model.database.ormlite;

import com.dreamteam.vicam.model.pojo.Zoom;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.IntegerObjectType;
import com.j256.ormlite.support.DatabaseResults;

import java.sql.SQLException;

/**
 * Created by Daniel on 2014-04-11.
 */
public class ZoomPersister extends IntegerObjectType {

  private ZoomPersister() {
    super(SqlType.INTEGER, new Class<?>[] { Zoom.class });
  }

  @Override
  public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos)
      throws SQLException {
    return results.getInt(columnPos);
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
