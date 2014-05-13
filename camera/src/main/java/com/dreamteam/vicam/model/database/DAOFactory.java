package com.dreamteam.vicam.model.database;

import rx.Observable;

/**
 * Created by fsommar on 2014-04-01.
 */
public interface DAOFactory {

  public Observable<CameraDAO> getCameraDAO();

  public Observable<PresetDAO> getPresetDAO();

  void close();
}
