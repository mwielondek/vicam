package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Camera;

import java.util.List;

/**
 * Created by fsommar on 2014-04-03.
 */
public class CameraDAOImpl implements CameraDAO {

  @Override
  public int insertCamera(Camera camera) {
    return -1;
  }

  @Override
  public Camera findCamera(int id) {
    return null;
  }

  @Override
  public boolean updateCamera(Camera camera) {
    return false;
  }

  @Override
  public boolean deleteCamera(int id) {
    return false;
  }

  @Override
  public List<Camera> getCameras() {
    return null;
  }
}
