package com.dreamteam.vicam.presenter.network.camera;

import com.dreamteam.vicam.model.errors.CameraResponseException;
import com.dreamteam.vicam.model.pojo.Position;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by fsommar on 2014-04-01.
 */
public class CameraCommands {

  private static final String COMMAND_PREFIX = "%23"; // HTML-encoded '#'

  private static final Pattern PAN_TILT_RESPONSE = Pattern.compile("aPC(\\d{4})(\\d{4})");
  private static final Pattern ZOOM_LEVEL_RESPONSE = Pattern.compile("gz(\\d{3})");
  private static final Pattern FOCUS_LEVEL_RESPONSE = Pattern.compile("gf(\\d{3})");
  private static final Pattern AUTOFOCUS_RESPONSE = Pattern.compile("OAF:(\\d)");

  CameraService cameraService;

  public CameraCommands(CameraService cameraService) {
    this.cameraService = cameraService;
  }

  public Observable<String> pan(int panSpeed) {
    return sendCommand("PS", padTwoHex(panSpeed));
  }

  public Observable<String> tilt(int tiltSpeed) {
    return sendCommand("TS", padTwoHex(tiltSpeed));
  }

  public Observable<String> panTilt(int panSpeed, int tiltSpeed) {
    return sendCommand("PTS", padTwoHex(panSpeed), padTwoHex(tiltSpeed));
  }

  public Observable<String> zoom(int zoomSpeed) {
    return sendCommand("Z", padThreeHex(zoomSpeed));
  }

  public Observable<String> focus(int focusSpeed) {
    return sendCommand("F", padThreeHex(focusSpeed));
  }

  public Observable<String> focusManual() {
    return sendControl("OAF:", 0);
  }

  public Observable<String> focusAuto() {
    return sendControl("OAF:", 1);
  }

  public Observable<String> panTiltAbsolute(int panPosition, int tiltPosition) {
    return sendCommand("APC", padTwoHex(panPosition), padTwoHex(tiltPosition));
  }

  public Observable<String> zoomAbsolute(int zoomLevel) {
    return sendCommand("AXZ", padThreeHex(zoomLevel));
  }

  public Observable<String> focusAbsolute(int focusLevel) {
    return sendCommand("AXF", padThreeHex(focusLevel));
  }

  public Observable<String> oneTouchAutofocus() {
    return sendControl("OSE:69:1");
  }

  public Observable<Position> getPanTilt() {
    return sendCommand("APC").map(new Func1<String, Position>() {
      @Override
      public Position call(String s) {
        Matcher m = PAN_TILT_RESPONSE.matcher(s);
        if (m.matches()) {
          try {
            return new Position(parseHex(m.group(1)), parseHex(m.group(2)));
          } catch (NumberFormatException ignored) {
            // Shouldn't be reached since regex matches digits only
          }
        }
        throw new CameraResponseException(s);
      }
    });
  }

  public Observable<Integer> getZoomLevel() {
    return sendCommand("GZ").map(new Func1<String, Integer>() {
      @Override
      public Integer call(String s) {
        Matcher m = ZOOM_LEVEL_RESPONSE.matcher(s);
        if (m.matches()) {
          try {
            return parseHex(m.group(1));
          } catch (NumberFormatException ignored) {
            // Shouldn't be reached since regex matches digits only
          }
        }
        throw new CameraResponseException(s);
      }
    });
  }

  public Observable<Integer> getFocusLevel() {
    return sendCommand("GF").map(new Func1<String, Integer>() {
      @Override
      public Integer call(String s) {
        Matcher m = FOCUS_LEVEL_RESPONSE.matcher(s);
        if (m.matches()) {
          try {
            return parseHex(m.group(1));
          } catch (NumberFormatException ignored) {
            // Shouldn't be reached since regex matches digits only
          }
        }
        throw new CameraResponseException(s);
      }
    });
  }

  public Observable<Boolean> getAF() {
    return sendControl("QAF").map(new Func1<String, Boolean>() {
      @Override
      public Boolean call(String s) {
        Matcher m = AUTOFOCUS_RESPONSE.matcher(s);
        if (m.matches()) {
          return m.group(1).equals("1");
        }
        throw new CameraResponseException(s);
      }
    });
  }

  private Observable<String> sendCommand(String command) {
    return cameraService.sendCommand(String.format("%s%s", COMMAND_PREFIX, command));
  }

  private Observable<String> sendCommand(String command, String data) {
    return cameraService.sendCommand(String.format("%s%s%s", COMMAND_PREFIX, command, data));
  }

  private Observable<String> sendCommand(String command, String dataOne, String dataTwo) {
    return cameraService.sendCommand(
        String.format("%s%s%s%s", COMMAND_PREFIX, command, dataOne, dataTwo));
  }

  private Observable<String> sendControl(String control) {
    return cameraService.sendControl(control);
  }

  private Observable<String> sendControl(String control, int data) {
    return cameraService.sendControl(control + Integer.toString(data));
  }

  private String padZeroesHex(int hexValue, int decimalPlaces) {
    return String.format("%0" + decimalPlaces + "X", hexValue);
  }

  private String padTwoHex(int hexValue) {
    return padZeroesHex(hexValue, 2);
  }

  private String padThreeHex(int hexValue) {
    return padZeroesHex(hexValue, 3);
  }

  private Integer parseHex(String hexString) {
    return Integer.parseInt(hexString, 16);
  }

}
