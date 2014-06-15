package com.dreamteam.vicam.model.database.ormlite;

import com.dreamteam.vicam.model.database.CameraDAO;
import com.dreamteam.vicam.model.database.DAOFactory;
import com.dreamteam.vicam.model.database.PresetDAO;
import com.dreamteam.vicam.view.BaseApplication;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import rx.Observable;

/**
 * An ORMLite implementation of the {@link com.dreamteam.vicam.model.database.DAOFactory} interface.
 * Make sure to call {@link #close()} when the database should be released!
 *
 * @author Fredrik Sommar
 * @since 2014-04-26.
 */
public class DAOFactoryImpl implements DAOFactory {

  private DatabaseOrmLiteHelper mDatabaseHelper;

  /**
   * {@inheritDoc}
   */
  @Override
  public Observable<CameraDAO> getCameraDAO() {
    return getDatabase().getCameraDAO();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Observable<PresetDAO> getPresetDAO() {
    return getDatabase().getPresetDAO();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {
    if (mDatabaseHelper != null) {
      OpenHelperManager.releaseHelper();
      mDatabaseHelper = null;
    }
  }

  /**
   * Returns the current instance of the database if it exists - if not it creates it.
   */
  private DatabaseOrmLiteHelper getDatabase() {
    if (mDatabaseHelper == null) {
      mDatabaseHelper =
          OpenHelperManager.getHelper(BaseApplication.getInstance(), DatabaseOrmLiteHelper.class);
    }
    return mDatabaseHelper;
  }
}
