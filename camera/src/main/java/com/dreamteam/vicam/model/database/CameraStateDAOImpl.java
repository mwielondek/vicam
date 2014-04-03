package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.CameraState;

import java.util.List;

/**
 * Created by fsommar on 2014-04-03.
 */
public class CameraStateDAOImpl implements CameraStateDAO {

  @Override
  public int insertCameraState(CameraState cameraState) {
    return -1;
  }

  @Override
  public CameraState findCameraState(int id) {
    return null;
  }

  @Override
  public boolean updateCameraState(CameraState cameraState) {
    return false;
  }

  @Override
  public boolean deleteCameraState(int id) {
    return false;
  }

  @Override
  public List<CameraState> getCameraStates() {
    return null;
  }
}
