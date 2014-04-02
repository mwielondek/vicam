package com.dreamteam.vicam.presenter.network.camera;

import rx.Observable;

import retrofit.http.Query;

/**
 * Created by fsommar on 2014-04-01.
 */
public interface CameraService {
  public Observable<String> sendCommand(@Query("cmd") String cmd);
}
