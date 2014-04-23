package com.dreamteam.vicam.model.events;

import com.dreamteam.vicam.model.pojo.Preset;

/**
 * Created by fsommar on 2014-04-23.
 */
public class PresetChangedEvent {
  public final Preset preset;

  public PresetChangedEvent(Preset preset) {
    this.preset = preset;
  }
}
