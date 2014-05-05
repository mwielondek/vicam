package com.dreamteam.vicam.presenter.network.camera;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by fsommar on 2014-04-01.
 */
public interface CameraService {

  static final String CGI_BIN = "/cgi-bin";

  @GET(CGI_BIN + "/aw_ptz")
  public Observable<String> sendCommand(@Query("cmd") String command, @Query("res") int res);

  @GET(CGI_BIN + "/aw_cam")
  public Observable<String> sendControl(@Query("cmd") String control, @Query("res") int res);
}
