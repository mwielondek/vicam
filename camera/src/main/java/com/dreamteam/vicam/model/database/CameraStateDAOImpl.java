package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.CameraState;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by fsommar on 2014-04-03.
 */
public class CameraStateDAOImpl implements CameraStateDAO {

  private Dao<CameraState, Integer> cameraStateDaoOrmLite;

  public CameraStateDAOImpl(
      Dao<CameraState, Integer> cameraStateDaoOrmLite) {
    this.cameraStateDaoOrmLite = cameraStateDaoOrmLite;
  }

  @Override
  public int insertCameraState(CameraState cameraState) {
    int insertRows = 0;
    try {
      insertRows = cameraStateDaoOrmLite.create(cameraState);
    } catch (SQLException e) {
      return -1;
    }
    if (insertRows == 0) {
      return -1;
    }
    return cameraState.getId();
  }

  @Override
  public CameraState findCameraState(int id) {
    try {
      return cameraStateDaoOrmLite.queryForId(id);
    } catch (SQLException e) {
      return null;
    }
  }

  @Override
  public boolean updateCameraState(CameraState cameraState) {
    try {
      int updatedRows = cameraStateDaoOrmLite.update(cameraState);
      return updatedRows == 1;
    } catch (SQLException e) {
      return false;
    }
  }

  @Override
  public boolean deleteCameraState(int id) {
    try {
      int deletedRows = cameraStateDaoOrmLite.deleteById(id);
      return deletedRows == 1;
    } catch (SQLException e) {
      return false;
    }
  }

  @Override
  public List<CameraState> getCameraStates() {
    try {
      return cameraStateDaoOrmLite.queryForAll();
    } catch (SQLException e) {
      return null;
    }
  }
}
