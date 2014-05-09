package com.dreamteam.vicam.model.database.ormlite;

import com.dreamteam.vicam.model.database.CameraDAO;
import com.dreamteam.vicam.model.database.DAOFactory;
import com.dreamteam.vicam.model.database.PresetDAO;
import com.dreamteam.vicam.view.BaseApplication;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import rx.Observable;

/**
 * Created by fsommar on 2014-04-26.
 */
public class DAOFactoryImpl implements DAOFactory {

  private DatabaseOrmLiteHelper mDatabaseHelper;

  @Override
  public Observable<CameraDAO> getCameraDAO() {
    return getDatabase().getCameraDAO();
  }

  @Override
  public Observable<PresetDAO> getPresetDAO() {
    return getDatabase().getPresetDAO();
  }

  public void close() {
    if (mDatabaseHelper != null) {
      OpenHelperManager.releaseHelper();
      mDatabaseHelper = null;
    }
  }

  private DatabaseOrmLiteHelper getDatabase() {
    if (mDatabaseHelper == null) {
      mDatabaseHelper = OpenHelperManager.getHelper(BaseApplication.getInstance(), DatabaseOrmLiteHelper.class);
    }
    return mDatabaseHelper;
  }
}
