package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Camera;
import com.j256.ormlite.dao.Dao;

import java.util.List;

import static com.dreamteam.vicam.presenter.utility.Utils.ORMLite;

/**
 * Created by fsommar on 2014-04-03.
 */
public class CameraDAOImpl implements CameraDAO {

  private Dao<Camera, Integer> cameraDaoOrmLite;

  public CameraDAOImpl(
      Dao<Camera, Integer> cameraDaoOrmLite) {
    this.cameraDaoOrmLite = cameraDaoOrmLite;
  }

  @Override
  public int insertCamera(Camera camera) {
    boolean success = ORMLite.insert(cameraDaoOrmLite, camera);
    return success ? camera.getId() : -1;
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
  public boolean deleteCamera(int id) {
    return ORMLite.delete(cameraDaoOrmLite, id);
  }

  @Override
  public List<Camera> getCameras() {
    return ORMLite.getAll(cameraDaoOrmLite);
  }
}
