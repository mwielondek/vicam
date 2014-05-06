package com.dreamteam.vicam.model.errors;

/**
 * Created by fsommar on 2014-05-07.
 */
public class CameraDoesNotExistException extends Throwable {

  public CameraDoesNotExistException(String detailMessage) {
    super(detailMessage);
  }
}
