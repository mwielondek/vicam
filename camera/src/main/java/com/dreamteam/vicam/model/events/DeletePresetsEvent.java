package com.dreamteam.vicam.model.events;

import com.dreamteam.vicam.model.pojo.Preset;

import java.util.List;

/**
 * Created by fsommar on 2014-05-04.
 */
public class DeletePresetsEvent {

  public final List<Preset> presets;

  public DeletePresetsEvent(List<Preset> presets) {
    this.presets = presets;
  }
}
