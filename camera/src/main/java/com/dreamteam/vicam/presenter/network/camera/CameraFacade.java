package com.dreamteam.vicam.presenter.network.camera;


import com.dreamteam.vicam.model.pojo.CameraState;
import com.dreamteam.vicam.model.pojo.Focus;
import com.dreamteam.vicam.model.pojo.Position;
import com.dreamteam.vicam.model.pojo.Speed;
import com.dreamteam.vicam.model.pojo.Zoom;

import rx.Observable;
import rx.functions.Func2;
import rx.functions.Func3;

/**
 * Created by fsommar on 2014-04-01.
 */
public class CameraFacade {

  private CameraCommands cameraCommands;

  public CameraFacade(CameraService cameraService) {
    this.cameraCommands = new CameraCommands(cameraService);
  }

  public Observable<String> moveStart(Speed speed) {
    return cameraCommands.panTilt(speed.getX(), speed.getY());
  }

  public Observable<String> moveStop() {
    return cameraCommands.panTilt(50, 50);
  }

  public Observable<String> zoomStart(int speed) {
    rangeCheck(speed, 0, 99);
    return cameraCommands.zoom(speed);
  }

  public Observable<String> zoomStop() {
    return cameraCommands.zoom(50);
  }

  public Observable<String> oneTouchFocus() {
    return cameraCommands.oneTouchAutofocus();
  }

  public Observable<String> moveAbsolute(Position pos) {
    return cameraCommands.panTiltAbsolute(pos.getPan(), pos.getTilt());
  }

  public Observable<String> zoomAbsolute(int level) {
    rangeCheck(level, 0x555, 0xFFF);
    return cameraCommands.zoomAbsolute(level);
  }

  public Observable<String> focusAbsolute(int level) {
    rangeCheck(level, 0x555, 0xFFF);
    return cameraCommands.focusAbsolute(level);
  }

  public Observable<CameraState> getCameraState() {
    return Observable.zip(
        cameraCommands.getPanTilt(), cameraCommands.getZoomLevel(), getFocus(),
        new Func3<Position, Integer, Focus, CameraState>() {
          @Override
          public CameraState call(Position position, Integer zoomLevel, Focus focus) {
            return new CameraState(position, new Zoom(zoomLevel), focus);
          }
        }
    );
  }

  public Observable<Focus> getFocus() {
    return Observable.zip(
        cameraCommands.getFocusLevel(), cameraCommands.getAF(),
        new Func2<Integer, Boolean, Focus>() {
          @Override
          public Focus call(Integer level, Boolean AF) {
            return new Focus(level, AF);
          }
        }
    );
  }

  private void rangeCheck(int param, int lower, int upper) {
    if (param < lower || param > upper) {
      throw new IllegalArgumentException(
          String.format("Parameter needs to be in range [%d, %d] - was %d.", lower, upper, param));
    }
  }

}
