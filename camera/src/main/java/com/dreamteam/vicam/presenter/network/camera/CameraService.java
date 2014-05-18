package com.dreamteam.vicam.presenter.network.camera;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * A service class used to simplify the sending of commands to the camera.
 *
 * @author Fredrik Sommar
 * @since 2014-04-01
 */
public interface CameraService {

  static final String CGI_BIN = "/cgi-bin";

  /**
   * Sends a command over a web request to the camera.
   *
   * @param command The camera command to be sent.
   * @param res     Should by default be 1 as per the camera protocol.
   * @return The body of the response.
   */
  @GET(CGI_BIN + "/aw_ptz")
  public Observable<String> sendCommand(@Query("cmd") String command, @Query("res") int res);

  /**
   * Sends a control command over a web request to the camera.
   *
   * @param control The camera control command to be sent.
   * @param res     Either 0 or 1. Most cases it is 1 per the camera protocol.
   * @return The body of the response.
   */
  @GET(CGI_BIN + "/aw_cam")
  public Observable<String> sendControl(@Query("cmd") String control, @Query("res") int res);
}
