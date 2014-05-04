package com.dreamteam.vicam.model.database.ormlite;

import com.dreamteam.vicam.model.database.CameraDAO;
import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.model.pojo.Preset;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import static com.dreamteam.vicam.presenter.utility.Utils.ORMLite;

/**
 * Created by fsommar on 2014-04-03.
 */
public class CameraDAOImpl implements CameraDAO {

  private final OrmLiteSqliteOpenHelper ormLiteHelper;
  private Dao<Camera, Integer> cameraDaoOrmLite;
  private Dao<Preset, Integer> presetDaoOrmLite;

  public CameraDAOImpl(OrmLiteSqliteOpenHelper ormLiteHelper) throws SQLException {
    this.ormLiteHelper = ormLiteHelper;
    this.cameraDaoOrmLite = ormLiteHelper.getDao(Camera.class);
    this.presetDaoOrmLite = ormLiteHelper.getDao(Preset.class);
  }

  @Override
  public int insertCamera(Camera camera) {
    return ORMLite.insert(cameraDaoOrmLite, camera);
  }

  @Override
  public Camera findCamera(int id) {
    return ORMLite.find(cameraDaoOrmLite, id);
  }

  @Override
  public boolean updateCamera(Camera camera) {
    return ORMLite.update(cameraDaoOrmLite, camera);
  }

  @Override
  public boolean deleteCamera(final int id) {
    try {
      TransactionManager.callInTransaction(
          ormLiteHelper.getConnectionSource(),
          new Callable<Object>() {
            @Override
            public Object call() throws Exception {
              DeleteBuilder deleteBuilder = presetDaoOrmLite.deleteBuilder();
              deleteBuilder.where().eq("camera_id", id);
              deleteBuilder.delete();
              cameraDaoOrmLite.deleteById(id);
              return null;
            }
          }
      );
      return true;
    } catch (SQLException e) {
      Utils.databaseLog(String.format("Error while deleting camera with id %d", id), e);
      return false;
    }
    //return ORMLite.delete(cameraDaoOrmLite, id);
  }

  @Override
  public List<Camera> getCameras() {
    return ORMLite.getAll(cameraDaoOrmLite);
  }
}
