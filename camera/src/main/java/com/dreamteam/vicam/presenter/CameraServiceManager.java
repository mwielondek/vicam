package com.dreamteam.vicam.presenter;

import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.presenter.network.camera.CameraFacade;
import com.dreamteam.vicam.presenter.network.camera.CameraService;
import com.dreamteam.vicam.presenter.utility.RetrofitStringParser;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * Created by fsommar on 2014-04-07.
 */
public class CameraServiceManager {

  private static Map<String, CameraFacade> cameraFacades = new HashMap<>();

  public static CameraFacade getFacadeFor(Camera c) {
    String key = c.getAddress();

    if (cameraFacades.containsKey(key)) {
      return cameraFacades.get(key);
    }
    CameraFacade cf = new CameraFacade(createServiceFor(c));
    cameraFacades.put(key, cf);
    return cf;
  }

  public static CameraService createServiceFor(Camera c) {
    return new RestAdapter
        .Builder()
        .setEndpoint(c.getAddress())
        .setConverter(new RetrofitStringParser())
        .build()
        .create(CameraService.class);
  }
}
