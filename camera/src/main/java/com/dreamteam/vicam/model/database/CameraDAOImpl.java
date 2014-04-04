package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Camera;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

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
    int insertRows = 0;
    try {
      insertRows = cameraDaoOrmLite.create(camera);
    } catch (SQLException e) {
      return -1;
    }
    if (insertRows == 0) {
      return -1;
    }
    return camera.getId();
  }

  @Override
  public Camera findCamera(int id) {
    try {
      return cameraDaoOrmLite.queryForId(id);
    } catch (SQLException e) {
      return null;
    }
  }

  @Override
  public boolean updateCamera(Camera camera) {
    try {
      int updatedRows = cameraDaoOrmLite.update(camera);
      return updatedRows == 1;
    } catch (SQLException e) {
      return false;
    }
  }

  @Override
  public boolean deleteCamera(int id) {
    try {
      int deletedRows = cameraDaoOrmLite.deleteById(id);
      return deletedRows == 1;
    } catch (SQLException e) {
      return false;
    }
  }

  @Override
  public List<Camera> getCameras() {
    try {
      return cameraDaoOrmLite.queryForAll();
    } catch (SQLException e) {
      return null;
    }
  }
}
