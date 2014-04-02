package com.dreamteam.vicam.model.database;

/**
 * Created by fsommar on 2014-04-01.
 */
public interface DAOFactory {
  public CameraDAO getCameraDAO();
  public PresetDAO getPresetDAO();
  public CameraStateDAO getCameraStateDAO();
}
