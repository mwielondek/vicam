package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.CameraState;

import java.util.List;

/**
 * Created by fsommar on 2014-04-01.
 */
public interface CameraStateDAO {
  public int insertCameraState(CameraState cameraState);
  public CameraState findCameraState(int id);
  public boolean updateCameraState(CameraState cameraState);
  public boolean deleteCameraState(int id);
  public List<CameraState> getCameraStates();

}
