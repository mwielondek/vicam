package com.dreamteam.vicam.model.events;

import com.dreamteam.vicam.model.pojo.Preset;

/**
 * This event is fired when a {@link com.dreamteam.vicam.model.pojo.Preset} object is set for
 * editing.
 *
 * @author Fredrik Sommar
 * @since 2014-05-04.
 */
public class EditPresetDialogEvent {

  /**
   * The {@link com.dreamteam.vicam.model.pojo.Preset} object to be edited in a dialog.
   */
  public final Preset preset;

  /**
   * An event indicating that the provided {@link com.dreamteam.vicam.model.pojo.Preset} object
   * should be edited.
   *
   * @param preset The preset to be edited in a dialog.
   */
  public EditPresetDialogEvent(Preset preset) {
    this.preset = preset;
  }
}
