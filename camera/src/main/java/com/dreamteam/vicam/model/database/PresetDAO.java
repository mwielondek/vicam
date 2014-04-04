package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Preset;

import java.util.List;

/**
 * Created by fsommar on 2014-04-01.
 */
public interface PresetDAO {

  public int insertPreset(Preset preset);

  public Preset findPreset(int id);

  public boolean updatePreset(Preset preset);

  public boolean deletePreset(int id);

  public List<Preset> getPresets();
}
