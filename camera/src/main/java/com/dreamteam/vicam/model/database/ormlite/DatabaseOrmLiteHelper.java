package com.dreamteam.vicam.model.database.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dreamteam.vicam.model.database.CameraDAO;
import com.dreamteam.vicam.model.database.PresetDAO;
import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.model.pojo.CameraState;
import com.dreamteam.vicam.model.pojo.Focus;
import com.dreamteam.vicam.model.pojo.Position;
import com.dreamteam.vicam.model.pojo.Preset;
import com.dreamteam.vicam.presenter.utility.Constants;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import rx.Observable;

/**
 * A database helper necessary for OrmLite to work with android. onUpgrade is called when {@link
 * #DATABASE_VERSION} is increased and is used for e.g. altering tables and such as necessary when
 * upgrading the internal database representation.
 *
 * @author Fredrik Sommar
 * @since 2014-04-03.
 */
public class DatabaseOrmLiteHelper extends OrmLiteSqliteOpenHelper {

  private static final int DATABASE_VERSION = 2;

  private CameraDAO cameraDao;
  private PresetDAO presetDao;

  public DatabaseOrmLiteHelper(Context context) {
    super(context, Constants.DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
    try {
      Log.i(getClass().getName(), "onCreate");
      TableUtils.createTable(connectionSource, Focus.class);
      TableUtils.createTable(connectionSource, Position.class);
      TableUtils.createTable(connectionSource, CameraState.class);
      TableUtils.createTable(connectionSource, Preset.class);
      TableUtils.createTable(connectionSource, Camera.class);
    } catch (SQLException e) {
      Utils.databaseError("Can't create database", e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
                        int newVersion) {
    if (oldVersion == 1) {
      // Add columns for x and y inversion
      db.execSQL(
          "ALTER TABLE camera ADD invert_x INTEGER NOT NULL CHECK(invert_x IN (0,1)) DEFAULT 0;");
      db.execSQL(
          "ALTER TABLE camera ADD invert_y INTEGER NOT NULL CHECK(invert_y IN (0,1)) DEFAULT 0;");
    }
  }

  /**
   * Closes the database connections and clears cached DAOs.
   */
  @Override
  public void close() {
    super.close();
    cameraDao = null;
    presetDao = null;
  }

  /**
   * Used by {@link com.dreamteam.vicam.model.database.ormlite.DAOFactoryImpl}.
   * @see com.dreamteam.vicam.model.database.DAOFactory#getCameraDAO()
   */
  public Observable<CameraDAO> getCameraDAO() {
    if (cameraDao == null) {
      try {
        @SuppressWarnings("unchecked")
        CameraDAO temp = new CameraDAOImpl(this);
        cameraDao = temp;
        return Observable.just(cameraDao);
      } catch (SQLException e) {
        Utils.databaseError("Error while getting Camera DAO", e);
        return Observable.error(e);
      }
    } else {
      return Observable.just(cameraDao);
    }
  }

  /**
   * Used by {@link com.dreamteam.vicam.model.database.ormlite.DAOFactoryImpl}.
   * @see com.dreamteam.vicam.model.database.DAOFactory#getPresetDAO()
   */
  public Observable<PresetDAO> getPresetDAO() {
    if (presetDao == null) {
      try {
        @SuppressWarnings("unchecked")
        PresetDAO temp = new PresetDAOImpl(this);
        presetDao = temp;
        return Observable.just(presetDao);
      } catch (SQLException e) {
        Utils.databaseError("Error while getting Preset DAO", e);
        return Observable.error(e);
      }
    }
    return Observable.just(presetDao);
  }

}
