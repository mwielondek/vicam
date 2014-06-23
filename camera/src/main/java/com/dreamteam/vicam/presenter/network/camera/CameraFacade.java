package com.dreamteam.vicam.presenter.network.camera;


import com.dreamteam.vicam.model.pojo.CameraState;
import com.dreamteam.vicam.model.pojo.Focus;
import com.dreamteam.vicam.model.pojo.Position;
import com.dreamteam.vicam.model.pojo.Speed;
import com.dreamteam.vicam.model.pojo.Zoom;
import com.dreamteam.vicam.presenter.utility.Constants;
import com.dreamteam.vicam.presenter.utility.Utils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;

/**
 * Hides away the implementation-specific details for the camera protocol. When communicating with
 * the web camera it should always go through this facade to simplify future code maintenance.
 *
 * @author Milosz Wielondek
 * @author Fredrik Sommar
 * @since 2014-04-01.
 */
public class CameraFacade {

  private CameraCommands cameraCommands;

  /**
   * This facade builds upon a {@link com.dreamteam.vicam.presenter.network.camera.CameraService}
   * which is provided in this constructor.
   */
  public CameraFacade(CameraService cameraService) {
    this.cameraCommands = new CameraCommands(cameraService);
  }

  /**
   * Starts moving the camera with the provided {@link com.dreamteam.vicam.model.pojo.Speed} in the
   * x- and y-axes.
   *
   * @return An {@link rx.Observable} with a string that contains the body of the response.
   */
  public Observable<String> moveStart(Speed speed) {
    return cameraCommands.panTilt(speed.getX(), speed.getY());
  }

  /**
   * Stops moving the camera.
   *
   * @return An {@link rx.Observable} with a string that contains the body of the response.
   */
  public Observable<String> moveStop() {
    return cameraCommands.panTilt(Speed.STOP, Speed.STOP);
  }

  /**
   * Starts updating the camera zoom with the provided speed. <br> Anything below {@link
   * com.dreamteam.vicam.model.pojo.Speed#STOP} is zooming out. Conversely, anything above is
   * zooming in. Valid input is in the range {@link com.dreamteam.vicam.model.pojo.Speed#LOWER_BOUND}
   * to {@link com.dreamteam.vicam.model.pojo.Speed#UPPER_BOUND} inclusive.
   *
   * @return An {@link rx.Observable} with a string that contains the body of the response.
   */
  public Observable<String> zoomStart(int speed) {
    Utils.rangeCheck(speed, Speed.LOWER_BOUND, Speed.UPPER_BOUND);
    return cameraCommands.zoom(speed);
  }

  /**
   * Stops updating the camera zoom, meaning the zooming will stop.
   *
   * @return An {@link rx.Observable} with a string that contains the body of the response.
   */
  public Observable<String> zoomStop() {
    return cameraCommands.zoom(Speed.STOP);
  }

  /**
   * Triggers an auto focus event. Only usable in manual focus mode.
   *
   * @return An {@link rx.Observable} with a string that contains the body of the response.
   */
  public Observable<String> oneTouchFocus() {
    return cameraCommands.oneTouchAutofocus();
  }

  /**
   * Moves the camera to the provided {@link com.dreamteam.vicam.model.pojo.Position}.
   *
   * @return An {@link rx.Observable} with a string that contains the body of the response.
   */
  public Observable<String> moveAbsolute(Position pos) {
    return cameraCommands.panTiltAbsolute(pos.getPan(), pos.getTilt());
  }

  /**
   * Zooms the camera to the provided {@link com.dreamteam.vicam.model.pojo.Zoom} level.
   *
   * @return An {@link rx.Observable} with a string that contains the body of the response.
   */
  public Observable<String> zoomAbsolute(int level) {
    Utils.rangeCheck(level, Zoom.LOWER_BOUND, Zoom.UPPER_BOUND);
    return cameraCommands.zoomAbsolute(level);
  }

  /**
   * Focuses the camera to the provided {@link com.dreamteam.vicam.model.pojo.Focus} level.
   *
   * @return An {@link rx.Observable} with a string that contains the body of the response.
   */
  public Observable<String> focusAbsolute(int level) {
    Utils.rangeCheck(level, Focus.LOWER_BOUND, Focus.UPPER_BOUND);
    return cameraCommands.focusAbsolute(level);
  }

  /**
   * Sets the auto focus on or off accordingly.
   *
   * @return An {@link rx.Observable} with a string that contains the body of the response.
   */
  public Observable<String> setAF(boolean enabled) {
    return enabled ? cameraCommands.focusAuto() : cameraCommands.focusManual();
  }

  /**
   * Sets the state of the camera to the provided {@link com.dreamteam.vicam.model.pojo.CameraState}.
   * This action will add some delay between the requests that are being sent in order to adhere to
   * the camera network protocol.
   *
   * @return An {@link rx.Observable} with a string that contains the body of the response.
   */
  public Observable<String> setCameraState(CameraState cameraState) {
    Position pos = cameraState.getPosition();
    final int zoom = cameraState.getZoom().getLevel();
    final int focus = cameraState.getFocus().getLevel();
    final boolean autofocus = cameraState.isAF();

    return moveAbsolute(pos).flatMap(new Func1<String, Observable<String>>() {
      @Override
      public Observable<String> call(String s) {
        return accountForDelay(zoomAbsolute(zoom));
      }
    }).flatMap(new Func1<String, Observable<String>>() {
      @Override
      public Observable<String> call(String s) {
        return accountForDelay(focusAbsolute(focus));
      }
    }).flatMap(new Func1<String, Observable<String>>() {
      @Override
      public Observable<String> call(String s) {
        return accountForDelay(setAF(autofocus));
      }
    });
  }

  /**
   * This action will add some delay between the requests that are being sent in order to adhere to
   * the camera network protocol.
   *
   * @return The current {@link com.dreamteam.vicam.model.pojo.CameraState} of the camera in a
   * {@link rx.Observable}.
   */
  public Observable<CameraState> getCameraState() {
    return Observable.zip(
        cameraCommands.getPanTilt(), accountForDelay(getZoom()), accountForDelay(getFocus()),
        new Func3<Position, Zoom, Focus, CameraState>() {
          @Override
          public CameraState call(Position position, Zoom zoom, Focus focus) {
            return new CameraState(position, zoom, focus);
          }
        }
    );
  }

  /**
   * Returns the current {@link com.dreamteam.vicam.model.pojo.Focus} state of the camera in a
   * {@link rx.Observable}, including focus level and whether auto focus is enabled or not.
   */
  public Observable<Focus> getFocus() {
    return Observable.zip(
        cameraCommands.getFocusLevel(), accountForDelay(cameraCommands.getAF()),
        new Func2<Integer, Boolean, Focus>() {
          @Override
          public Focus call(Integer level, Boolean AF) {
            return new Focus(level, AF);
          }
        }
    );
  }

  /**
   * Returns the current {@link com.dreamteam.vicam.model.pojo.Zoom} state of the camera in a {@link
   * rx.Observable}, including zoom level.
   */
  public Observable<Zoom> getZoom() {
    return cameraCommands.getZoomLevel().map(new Func1<Integer, Zoom>() {
      @Override
      public Zoom call(Integer level) {
        return new Zoom(level);
      }
    });
  }

  /**
   * Returns the current focus level of the camera in a {@link rx.Observable}.
   */
  public Observable<Integer> getFocusLevel() {
    return cameraCommands.getFocusLevel();
  }

  /**
   * Adds a delay to the provided {@link rx.Observable} in order to adhere to the camera protocol;
   * at least 130 milliseconds should be given between network requests.
   */
  public static <T> Observable<T> accountForDelay(Observable<T> obs) {
    return obs.delay(Constants.DELAY_TIME_MILLIS, TimeUnit.MILLISECONDS);
  }
}
