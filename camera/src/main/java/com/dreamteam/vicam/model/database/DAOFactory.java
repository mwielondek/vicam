package com.dreamteam.vicam.model.database;

import rx.Observable;

/**
 * A generic interface used to abstract away implementation-specific details from the ORM used.
 *
 * @author Fredrik Sommar
 * @since 2014-04-01.
 */
public interface DAOFactory {

  /**
   * Returns a generic interface for a data access object for the {@link
   * com.dreamteam.vicam.model.pojo.Camera} class.
   */
  public Observable<CameraDAO> getCameraDAO();

  /**
   * Returns a generic interface for a data access object for the {@link
   * com.dreamteam.vicam.model.pojo.Preset} class.
   */
  public Observable<PresetDAO> getPresetDAO();

  /**
   * Closes the database and frees all resources.
   */
  void close();
}
