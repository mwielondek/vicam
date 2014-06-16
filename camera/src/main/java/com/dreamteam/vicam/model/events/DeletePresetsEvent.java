package com.dreamteam.vicam.model.events;

import com.dreamteam.vicam.model.pojo.Preset;

import java.util.List;

/**
 * This event is fired when a list of {@link com.dreamteam.vicam.model.pojo.Preset} objects is set
 * for deletion.
 *
 * @author Fredrik Sommar
 * @since 2014-05-04.
 */
public class DeletePresetsEvent {

  /**
   * The list of {@link com.dreamteam.vicam.model.pojo.Preset} objects set for deletion.
   */
  public final List<Preset> presets;

  /**
   * This event indicates that the provided list of {@link com.dreamteam.vicam.model.pojo.Preset}
   * objects should be deleted from the database.
   *
   * @param presets The list of {@link com.dreamteam.vicam.model.pojo.Preset} objects to be
   *                deleted.
   */
  public DeletePresetsEvent(List<Preset> presets) {
    this.presets = presets;
  }
}
