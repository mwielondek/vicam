package com.dreamteam.vicam.model.errors;

/**
 * An exception that is thrown when the camera returns an unexpected result. Its response can be
 * retrieved from {@link #getMessage()}.
 *
 * @author Fredrik Sommar
 * @since 2014-04-14.
 */
public class CameraResponseException extends RuntimeException {

  public CameraResponseException(String s) {
    super(s);
  }
}
