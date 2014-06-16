package com.dreamteam.vicam.presenter;

import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.presenter.network.camera.CameraFacade;
import com.dreamteam.vicam.presenter.network.camera.CameraService;
import com.dreamteam.vicam.presenter.utility.RetrofitStringParser;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * Contains static functions for getting class objects representing an abstraction over the camera
 * web interface connection.
 *
 * @author Fredrik Sommar
 * @since 2014-04-07.
 */
public class CameraServiceManager {

  private static Map<String, CameraFacade> cameraFacades = new HashMap<>();

  /**
   * Gets a {@link com.dreamteam.vicam.presenter.network.camera.CameraFacade} for the camera passed
   * along as a parameter.
   *
   * @param camera The representation of the camera to get a {@link com.dreamteam.vicam.presenter.network.camera.CameraFacade}
   *               for. <br/>The IP address field needs to be populated in order for this method to
   *               work.
   * @return The CameraFacade corresponding to the IP address of the supplied {@link
   * com.dreamteam.vicam.model.pojo.Camera}.
   */
  public static CameraFacade getFacadeFor(Camera camera) {
    String key = camera.getAddress();

    if (cameraFacades.containsKey(key)) {
      return cameraFacades.get(key);
    }
    CameraFacade cf = new CameraFacade(createServiceFor(camera));
    cameraFacades.put(key, cf);
    return cf;
  }

  /**
   * Provides the {@link com.dreamteam.vicam.presenter.network.camera.CameraService} for the
   * supplied camera's address.
   */
  public static CameraService createServiceFor(Camera camera) {
    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(camera.getAddress())
        .setConverter(new RetrofitStringParser())
        .build();
    return restAdapter.create(CameraService.class);
  }
}
