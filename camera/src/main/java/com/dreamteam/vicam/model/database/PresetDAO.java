package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.model.pojo.Preset;

import java.util.List;

import rx.Observable;

/**
 * A generic access layer used when storing, updating and retrieving {@link
 * com.dreamteam.vicam.model.pojo.Preset} objects from the database.
 *
 * @author Fredrik Sommar
 * @since 2014-04-01.
 */
public interface PresetDAO {

  /**
   * Inserts a {@link com.dreamteam.vicam.model.pojo.Preset} object into the database.
   *
   * @return An {@link rx.Observable} containing either an error with the {@link
   * java.sql.SQLException} that was thrown, or an Integer with the id of the inserted {@link
   * com.dreamteam.vicam.model.pojo.Preset}.
   */
  public Observable<Integer> insertPreset(Preset preset);

  /**
   * Finds the {@link com.dreamteam.vicam.model.pojo.Preset} object with the given id.
   *
   * @return An {@link rx.Observable} containing either an error with the {@link
   * java.sql.SQLException} that was thrown, or a {@link com.dreamteam.vicam.model.pojo.Preset}
   * object with the queried id.
   */
  public Observable<Preset> findPreset(int id);

  /**
   * Updates the {@link com.dreamteam.vicam.model.pojo.Preset} representation in the database
   * corresponding to the passed {@link com.dreamteam.vicam.model.pojo.Preset} object. <br> Make
   * sure that whatever field is used as key exists on the provided object.
   *
   * @return An {@link rx.Observable} containing either an error with the {@link
   * java.sql.SQLException} that was thrown, or a boolean of whether the update was successful or
   * not. In most cases the boolean can be discarded and the update can be considered successful as
   * long as en error isn't returned.
   */
  public Observable<Boolean> updatePreset(Preset preset);

  /**
   * Deletes the {@link com.dreamteam.vicam.model.pojo.Preset} representation in the database with
   * the given id.
   *
   * @return An {@link rx.Observable} containing either an error with the {@link
   * java.sql.SQLException} that was thrown, or a boolean of whether the deletion was successful or
   * not. In most cases the boolean can be discarded and the deletion can be considered successful
   * as long as en error isn't returned.
   */
  public Observable<Boolean> deletePreset(int id);

  /**
   * Gets a list of all the {@link com.dreamteam.vicam.model.pojo.Preset} objects in the database.
   *
   * @return An {@link rx.Observable} containing either an error with the {@link
   * java.sql.SQLException} that was thrown, or a {@link java.util.List} of all the {@link
   * com.dreamteam.vicam.model.pojo.Preset} object in the database.
   */
  public Observable<List<Preset>> getPresets();

  /**
   * Gets a list of all the {@link com.dreamteam.vicam.model.pojo.Preset} objects related to a
   * specific {@link com.dreamteam.vicam.model.pojo.Camera} in the database.
   *
   * @param c The {@link com.dreamteam.vicam.model.pojo.Camera} to get all the {@link
   *          com.dreamteam.vicam.model.pojo.Preset} objects for.
   * @return An {@link rx.Observable} containing either an error with the {@link
   * java.sql.SQLException} that was thrown, or a {@link java.util.List} of all the {@link
   * com.dreamteam.vicam.model.pojo.Camera} objects related to the provided {@link
   * com.dreamteam.vicam.model.pojo.Camera} in the database.
   */
  public Observable<List<Preset>> getPresetsForCamera(Camera c);

}
