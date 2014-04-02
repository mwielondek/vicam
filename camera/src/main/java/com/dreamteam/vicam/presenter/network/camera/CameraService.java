package com.dreamteam.vicam.presenter.network.camera;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by fsommar on 2014-04-01.
 */
public interface CameraService {

  static final String CGI_BIN = "/cgi-bin";
  static final String RES_1 = "?res=1";

  @GET(CGI_BIN + "/aw_ptz" + RES_1)
  public Observable<String> sendCommand(@Query("cmd") String command);

  @GET(CGI_BIN + "/aw_cam" + RES_1)
  public Observable<String> sendControl(@Query("cmd") String control);
}
