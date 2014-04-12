package com.dreamteam.vicam.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.model.pojo.CameraState;
import com.dreamteam.vicam.model.pojo.Focus;
import com.dreamteam.vicam.model.pojo.Position;
import com.dreamteam.vicam.model.pojo.Preset;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by fsommar on 2014-04-03.
 */
public class DatabaseOrmLiteHelper extends OrmLiteSqliteOpenHelper implements DAOFactory {

  // name of the database file for your application -- change to something appropriate for your app
  private static final String DATABASE_NAME = "vicamera.db";
  // any time you make changes to your database objects, you may have to increase the database version
  private static final int DATABASE_VERSION = 1;

  // the DAO object we use to access the SimpleData table
  private CameraDAO cameraDao;
  private PresetDAO presetDao;

  public DatabaseOrmLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
    try {
      Log.i(this.getClass().getName(), "onCreate");
      TableUtils.createTable(connectionSource, Focus.class);
      TableUtils.createTable(connectionSource, Position.class);
      TableUtils.createTable(connectionSource, CameraState.class);
      TableUtils.createTable(connectionSource, Preset.class);
      TableUtils.createTable(connectionSource, Camera.class);
    } catch (SQLException e) {
      Log.e(this.getClass().getName(), "Can't create database", e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
                        int newVersion) {
    // Do nothing on upgrade
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

  @Override
  public CameraDAO getCameraDAO() {
    if (cameraDao == null) {
      // cameraDao = new CameraDAOImpl(getDao(Camera.class));
    }
    return cameraDao;
  }

  @Override
  public PresetDAO getPresetDAO() {
    if (presetDao == null) {
      // presetDao = new PresetDAOImpl(getDao(Preset.class));
    }
    return presetDao;
  }

}
