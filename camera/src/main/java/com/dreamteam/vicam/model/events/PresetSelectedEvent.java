package com.dreamteam.vicam.model.events;

import com.dreamteam.vicam.model.pojo.Preset;

/**
 * This event fires when a preset is selected.
 *
 * @author Fredrik Sommar
 * @since 2014-04-23.
 */
public class PresetSelectedEvent {

  /**
   * The preset that was selected.
   */
  public final Preset preset;

  /**
   * An event indicating that a preset was selected.
   *
   * @param preset The {@link com.dreamteam.vicam.model.pojo.Preset} object representation of the
   *               selected preset.
   */
  public PresetSelectedEvent(Preset preset) {
    this.preset = preset;
  }
}
