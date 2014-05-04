package com.dreamteam.vicam.model.events;

import com.dreamteam.vicam.model.pojo.Preset;

/**
 * Created by fsommar on 2014-05-04.
 */
public class EditPresetEvent {

  public final Preset preset;

  public EditPresetEvent(Preset preset) {
    this.preset = preset;
  }
}
