package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Camera;

import java.util.List;

import rx.Observable;

/**
 * Created by fsommar on 2014-04-01.
 */
public interface CameraDAO {

  public Observable<Integer> insertCamera(Camera camera);

  public Observable<Camera> findCamera(int id);

  public Observable<Boolean> updateCamera(Camera camera);

  public Observable<Boolean> deleteCamera(int id);

  public Observable<List<Camera>> getCameras();
}
