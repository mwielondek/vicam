package com.dreamteam.vicam.presenter.network.camera;

import com.dreamteam.vicam.model.pojo.Position;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by fsommar on 2014-04-01.
 */
public class CameraCommands {

  private static final String COMMAND_PREFIX = "#";

  CameraService cameraService;

  public CameraCommands(CameraService cameraService) {
    this.cameraService = cameraService;
  }

  private Observable<String> sendCommand(String command) {
    return cameraService.sendCommand(COMMAND_PREFIX + command);
  }

  private Observable<String> sendCommand(String command, int data) {
    return cameraService.sendCommand(COMMAND_PREFIX + command + Integer.toString(data));
  }

  private Observable<String> sendCommand(String command, int dataOne, int dataTwo) {
    return cameraService.sendCommand(
        COMMAND_PREFIX + command + Integer.toString(dataOne) + Integer.toString(dataTwo));
  }

  private Observable<String> sendControl(String control) {
    return cameraService.sendControl(control);
  }

  private Observable<String> sendControl(String control, int data) {
    return cameraService.sendControl(control + Integer.toString(data));
  }

  public Observable<String> pan(int panSpeed) {
    return sendCommand("PS", panSpeed);
  }

  public Observable<String> tilt(int tiltSpeed) {
    return sendCommand("TS", tiltSpeed);
  }

  public Observable<String> panTilt(int panSpeed, int tiltSpeed) {
    return sendCommand("PTS", panSpeed, tiltSpeed);
  }

  public Observable<String> zoom(int zoomSpeed) {
    return sendCommand("Z", zoomSpeed);
  }

  public Observable<String> focus(int focusSpeed) {
    return sendCommand("F", focusSpeed);
  }

  public Observable<String> focusManual() {
    return sendCommand("D", 10); // 1 is for autofocus and 0 for disable
    // return sendControl("OAF:", 0);
  }

  public Observable<String> focusAuto() {
    return sendCommand("D", 11); // 1 is for autofocus and 1 for enable
    // return sendControl("OAF:", 1);
  }

  public Observable<String> panTiltAbsolute(int panPosition, int tiltPosition) {
    return sendCommand("APC", panPosition, tiltPosition);
  }

  public Observable<String> zoomAbsolute(int zoomLevel) {
    return sendCommand("AXZ", zoomLevel);
  }

  public Observable<String> focusAbsolute(int focusLevel) {
    return sendCommand("AXF", focusLevel);
  }

  public Observable<String> oneTouchAutofocus() {
    return sendControl("OSE:69:", 1);
  }

  public Observable<Position> getPanTilt() {
    return sendCommand("APC").map(new Func1<String, Position>() {
      @Override
      public Position call(String s) {
        Pattern p = Pattern.compile("aPC(\\d{4})(\\d{4})");
        Matcher m = p.matcher(s);
        Position pos = new Position(
           Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))
        );
        return pos;
      }
    });
  }

  public Observable<Integer> getZoom() {
    return sendCommand("GZ").map(new Func1<String, Integer>() {
      @Override
      public Integer call(String s) {
        Pattern p = Pattern.compile("gz(\\d{3})");
        Matcher m = p.matcher(s);
        int level = Integer.parseInt(m.group(1));
        return level;
      }
    });
  }

  public Observable<Integer> getFocus() {
    return sendCommand("GF").map(new Func1<String, Integer>() {
      @Override
      public Integer call(String s) {
        Pattern p = Pattern.compile("gf(\\d{3})");
        Matcher m = p.matcher(s);
        int level = Integer.parseInt(m.group(1));
        return level;
      }
    });
  }

  public Observable<Boolean> getAF() {
    return sendControl("QAF").map(new Func1<String, Boolean>() {
      @Override
      public Boolean call(String s) {
        Pattern p = Pattern.compile("OAF:(\\d)");
        Matcher m = p.matcher(s);
        Boolean bool = m.group(1).equals("1");
        return bool;
      }
    });
  }
}
