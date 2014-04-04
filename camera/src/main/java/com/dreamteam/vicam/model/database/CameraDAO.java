package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Camera;

import java.util.List;

/**
 * Created by fsommar on 2014-04-01.
 */
public interface CameraDAO {

  public int insertCamera(Camera camera);

  public Camera findCamera(int id);

  public boolean updateCamera(Camera camera);

  public boolean deleteCamera(int id);

  public List<Camera> getCameras();
}
