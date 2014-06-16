package com.dreamteam.vicam.model.errors;

/**
 * Thrown when trying to access a camera in the interface that doesn't currently exist.
 *
 * @author Fredrik Sommar
 * @since 2014-05-07.
 */
public class CameraDoesNotExistException extends Throwable {

  public CameraDoesNotExistException(String detailMessage) {
    super(detailMessage);
  }
}
