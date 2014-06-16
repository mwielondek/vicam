package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Camera;

import java.util.List;

import rx.Observable;

/**
 * A generic access layer used when storing, updating and retrieving {@link
 * com.dreamteam.vicam.model.pojo.Camera} objects from the database.
 *
 * @author Fredrik Sommar
 * @since 2014-04-01.
 */
public interface CameraDAO {

  /**
   * Inserts a {@link com.dreamteam.vicam.model.pojo.Camera} object into the database.
   *
   * @return An {@link rx.Observable} containing either an error with the {@link
   * java.sql.SQLException} that was thrown, or an Integer with the id of the inserted {@link
   * com.dreamteam.vicam.model.pojo.Camera}.
   */
  public Observable<Integer> insertCamera(Camera camera);

  /**
   * Finds the {@link com.dreamteam.vicam.model.pojo.Camera} object with the given id.
   *
   * @return An {@link rx.Observable} containing either an error with the {@link
   * java.sql.SQLException} that was thrown, or a {@link com.dreamteam.vicam.model.pojo.Camera}
   * object with the queried id.
   */
  public Observable<Camera> findCamera(int id);

  /**
   * Updates the {@link com.dreamteam.vicam.model.pojo.Camera} representation in the database
   * corresponding to the passed {@link com.dreamteam.vicam.model.pojo.Camera} object. <br> Make
   * sure that whatever field is used as key exists on the provided object.
   *
   * @return An {@link rx.Observable} containing either an error with the {@link
   * java.sql.SQLException} that was thrown, or a boolean of whether the update was successful or
   * not. In most cases the boolean can be discarded and the update can be considered successful as
   * long as en error isn't returned.
   */
  public Observable<Boolean> updateCamera(Camera camera);

  /**
   * Deletes the {@link com.dreamteam.vicam.model.pojo.Camera} representation in the database with
   * the given id.
   *
   * @return An {@link rx.Observable} containing either an error with the {@link
   * java.sql.SQLException} that was thrown, or a boolean of whether the deletion was successful or
   * not. In most cases the boolean can be discarded and the deletion can be considered successful
   * as long as en error isn't returned.
   */
  public Observable<Boolean> deleteCamera(int id);

  /**
   * Gets a list of all the {@link com.dreamteam.vicam.model.pojo.Camera} objects in the database.
   *
   * @return An {@link rx.Observable} containing either an error with the {@link
   * java.sql.SQLException} that was thrown, or a {@link java.util.List} of all the {@link
   * com.dreamteam.vicam.model.pojo.Camera} objects in the database.
   */
  public Observable<List<Camera>> getCameras();
}
