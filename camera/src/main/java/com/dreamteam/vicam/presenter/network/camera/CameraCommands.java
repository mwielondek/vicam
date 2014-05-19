package com.dreamteam.vicam.presenter.network.camera;

import com.dreamteam.vicam.model.errors.CameraResponseException;
import com.dreamteam.vicam.model.pojo.Position;
import com.dreamteam.vicam.presenter.utility.Constants;
import com.dreamteam.vicam.presenter.utility.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.functions.Func1;

/**
 * Provides methods for a one-to-one mapping with the camera protocol, using {@link
 * com.dreamteam.vicam.presenter.network.camera.CameraService}.
 *
 * @author Fredrik Sommar
 * @since 2014-04-01
 */
public class CameraCommands {

  /**
   * A regex used for the response from the camera for current pan and tilt position.
   */
  private static final Pattern PAN_TILT_RESPONSE = Pattern.compile(
      "aPC([\\da-fA-F]{4})([\\da-fA-F]{4})");
  /**
   * A regex used for the response from the camera for current zoom level in {@code [555, FFF]}.
   */
  private static final Pattern ZOOM_LEVEL_RESPONSE = Pattern.compile("gz([\\da-fA-F]{3})");
  /**
   * A regex used for the response from the camera for current focus level in {@code [555, FFF]}.
   */
  private static final Pattern FOCUS_LEVEL_RESPONSE = Pattern.compile("gf([\\da-fA-F]{3})");
  /**
   * A regex used for the response from the camera for current autofocus setting (1 or 0).
   */
  private static final Pattern AUTOFOCUS_RESPONSE = Pattern.compile("OAF:(\\d)");

  private CameraService cameraService;

  /**
   * Uses the provided {@link com.dreamteam.vicam.presenter.network.camera.CameraService} as a basis
   * for network communications.
   */
  public CameraCommands(CameraService cameraService) {
    this.cameraService = cameraService;
  }

  /**
   * Starts moving the camera with the provided speed in the horizontal (pan) axis.
   *
   * @param panSpeed The speed of which the camera moves horizontally. Valid range is {@code [1,
   *                 99]} where everything below {@code 50} is decreasing and everything above is
   *                 increasing. A speed of {@code 50} signals the camera to stop moving
   *                 horizontally.
   * @return The body of the response.
   */
  public Observable<String> pan(int panSpeed) {
    return sendCommand("PS", Utils.padZeroesHex(panSpeed, 2));
  }

  /**
   * Starts moving the camera with the provided speed in the vertical (tilt) axis.
   *
   * @param tiltSpeed The speed of which the camera moves vertically. Valid range is {@code [1, 99]}
   *                  where everything below {@code 50} is decreasing and everything above is
   *                  increasing. A speed of {@code 50} signals the camera to stop moving
   *                  vertically.
   * @return The body of the response.
   */
  public Observable<String> tilt(int tiltSpeed) {
    return sendCommand("TS", Utils.padZeroesHex(tiltSpeed, 2));
  }

  /**
   * Starts moving the camera with the provided speeds in the horizontal (pan) and vertical (tilt)
   * axes.
   *
   * @param panSpeed  The speed of which the camera moves horizontally. Valid range is {@code [1,
   *                  99]} where everything below {@code 50} is decreasing and everything above is
   *                  increasing. A speed of {@code 50} signals the camera to stop moving
   *                  horizontally.
   * @param tiltSpeed The speed of which the camera moves vertically. Valid range is {@code [1, 99]}
   *                  where everything below {@code 50} is decreasing and everything above is
   *                  increasing. A speed of {@code 50} signals the camera to stop moving
   *                  vertically.
   * @return The body of the response.
   */
  public Observable<String> panTilt(int panSpeed, int tiltSpeed) {
    return sendCommand("PTS", Utils.padZeroes(panSpeed, 2), Utils.padZeroes(tiltSpeed, 2));
  }

  /**
   * Starts moving the zoom with the provided speed.
   *
   * @param zoomSpeed The speed of which the camera moves its zoom. Valid range is {@code [1, 99]}
   *                  where everything below {@code 50} is decreasing and everything above is
   *                  increasing. A speed of {@code 50} signals the camera to stop updating its
   *                  zoom.
   * @return The body of the response.
   */
  public Observable<String> zoom(int zoomSpeed) {
    return sendCommand("Z", Utils.padZeroes(zoomSpeed, 2));
  }

  /**
   * Starts moving the focus with the provided speed.
   *
   * @param focusSpeed The speed of which the camera moves its focus. Valid range is {@code [1, 99]}
   *                   where everything below {@code 50} is decreasing and everything above is
   *                   increasing. A speed of {@code 50} signals the camera to stop updating its
   *                   focus.
   * @return The body of the response.
   */
  public Observable<String> focus(int focusSpeed) {
    return sendCommand("F", Utils.padZeroes(focusSpeed, 2));
  }

  /**
   * Disables autofocus on the camera.
   *
   * @return The body of the response.
   */
  public Observable<String> focusManual() {
    return sendControl("OAF:", 0);
  }

  /**
   * Enables autofocus on the camera.
   *
   * @return The body of the response.
   */
  public Observable<String> focusAuto() {
    return sendControl("OAF:", 1);
  }

  /**
   * Positions the camera with the absolute positions provided.
   *
   * @param panPosition  The absolute horizontal position in the range {@code [0000, 8000]}.
   * @param tiltPosition The absolute vertical position in the range {@code [0000, 8000]}.
   * @return The body of the response.
   */
  public Observable<String> panTiltAbsolute(int panPosition, int tiltPosition) {
    return sendCommand("APC",
                       Utils.padZeroesHex(panPosition, 3), Utils.padZeroesHex(tiltPosition, 3));
  }

  /**
   * Updates the zoom with the absolute level provided.
   *
   * @param zoomLevel The zoom level in the range {@code [555, FFF]} (hexadecimal).
   * @return The body of the response.
   */
  public Observable<String> zoomAbsolute(int zoomLevel) {
    return sendCommand("AXZ", Utils.padZeroesHex(zoomLevel, 3));
  }

  /**
   * Updates the focus with the absolute level provided.
   *
   * @param focusLevel The focus level in the range {@code [555, FFF]} (hexadecimal).
   * @return The body of the response.
   */
  public Observable<String> focusAbsolute(int focusLevel) {
    return sendCommand("AXF", Utils.padZeroesHex(focusLevel, 3));
  }

  /**
   * Sends a request for the camera to automatically update the focus. Autofocus needs to be disable
   * in order for this to work.
   */
  public Observable<String> oneTouchAutofocus() {
    return sendControl("OSE:69:1");
  }

  /**
   * Returns an {@link rx.Observable} with an {@link java.lang.Integer} indicating the current pan
   * and tilt in the range of {@code [0000, 8000]} each, using a {@link
   * com.dreamteam.vicam.model.pojo.Position} object. Returns an {@link rx.Observable} with a {@link
   * com.dreamteam.vicam.model.errors.CameraResponseException} if the camera returns a different
   * result than expected.
   */
  public Observable<Position> getPanTilt() {
    return sendCommand("APC").map(new Func1<String, Position>() {
      @Override
      public Position call(String s) {
        Matcher m = PAN_TILT_RESPONSE.matcher(s);
        if (m.matches()) {
          try {
            return new Position(Utils.parseHex(m.group(1)), Utils.parseHex(m.group(2)));
          } catch (NumberFormatException ignored) {
            // Shouldn't be reached since regex matches digits only
          }
        }
        throw new CameraResponseException(s);
      }
    });
  }

  /**
   * Returns an {@link rx.Observable} with an {@link java.lang.Integer} indicating the current zoom
   * level in the range of {@code [555, FFF]}. Returns an {@link rx.Observable} with a {@link
   * com.dreamteam.vicam.model.errors.CameraResponseException} if the camera returns a different
   * result than expected.
   */
  public Observable<Integer> getZoomLevel() {
    return sendCommand("GZ").map(new Func1<String, Integer>() {
      @Override
      public Integer call(String s) {
        Matcher m = ZOOM_LEVEL_RESPONSE.matcher(s);
        if (m.matches()) {
          try {
            return Utils.parseHex(m.group(1));
          } catch (NumberFormatException ignored) {
            // Shouldn't be reached since regex matches digits only
          }
        }
        throw new CameraResponseException(s);
      }
    });
  }

  /**
   * Returns an {@link rx.Observable} with an {@link java.lang.Integer} indicating the current focus
   * level in the range of {@code [555, FFF]}. Returns an {@link rx.Observable} with a {@link
   * com.dreamteam.vicam.model.errors.CameraResponseException} if the camera returns a different
   * result than expected.
   */
  public Observable<Integer> getFocusLevel() {
    return sendCommand("GF").map(new Func1<String, Integer>() {
      @Override
      public Integer call(String s) {
        Matcher m = FOCUS_LEVEL_RESPONSE.matcher(s);
        if (m.matches()) {
          try {
            return Utils.parseHex(m.group(1));
          } catch (NumberFormatException ignored) {
            // Shouldn't be reached since regex matches digits only
          }
        }
        throw new CameraResponseException(s);
      }
    });
  }

  /**
   * Returns an {@link rx.Observable} with a {@link java.lang.Boolean} indicating whether autofocus
   * is on or off. Returns an {@link rx.Observable} with a {@link com.dreamteam.vicam.model.errors.CameraResponseException}
   * if the camera returns a different result than expected.
   */
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

  /**
   * Delegates the command to {@link com.dreamteam.vicam.presenter.network.camera.CameraService},
   * using 1 as default for the second parameter, as required by the camera protocol. <br/><br/>It
   * also adds the necessary command prefix in use by the protocol.
   *
   * @param command The command to be sent to the camera sans the {@code #} prefix.
   * @return The body of the response.
   */
  private Observable<String> sendCommand(String command) {
    return cameraService.sendCommand(Constants.COMMAND_PREFIX + command, 1);
  }

  private Observable<String> sendCommand(String command, String data) {
    return sendCommand(command.concat(data));
  }

  private Observable<String> sendCommand(String command, String dataOne, String dataTwo) {
    return sendCommand(command.concat(dataOne).concat(dataTwo));
  }

  /**
   * Delegates the control command to {@link com.dreamteam.vicam.presenter.network.camera.CameraService},
   * using 1 as default for the second parameter, as required by the camera protocol. <br/><br/>It
   * also adds the necessary command prefix in use by the protocol.
   *
   * @param control The control command to be sent to the camera.
   * @return The body of the response.
   */
  private Observable<String> sendControl(String control) {
    return cameraService.sendControl(control, 1);
  }

  private Observable<String> sendControl(String control, int data) {
    return cameraService.sendControl(control + Integer.toString(data), 1);
  }

}
