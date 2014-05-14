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
 * Created by fsommar on 2014-04-03.
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
      Utils.databaseLog("Can't create database", e);
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
   * Close the database connections and clear any cached DAOs.
   */
  @Override
  public void close() {
    super.close();
    cameraDao = null;
    presetDao = null;
  }

  public Observable<CameraDAO> getCameraDAO() {
    if (cameraDao == null) {
      try {
        @SuppressWarnings("unchecked")
        CameraDAO temp = new CameraDAOImpl(this);
        cameraDao = temp;
        return Observable.just(cameraDao);
      } catch (SQLException e) {
        Utils.databaseLog("Error while getting Camera DAO", e);
        return Observable.error(e);
      }
    } else {
      return Observable.just(cameraDao);
    }
  }

  public Observable<PresetDAO> getPresetDAO() {
    if (presetDao == null) {
      try {
        @SuppressWarnings("unchecked")
        PresetDAO temp = new PresetDAOImpl(this);
        presetDao = temp;
        return Observable.just(presetDao);
      } catch (SQLException e) {
        Utils.databaseLog("Error while getting Preset DAO", e);
        return Observable.error(e);
      }
    }
    return Observable.just(presetDao);
  }

}
