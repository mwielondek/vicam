package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Preset;

import java.util.List;

/**
 * Created by fsommar on 2014-04-03.
 */
public class PresetDAOImpl implements PresetDAO {

  @Override
  public int insertPreset(Preset preset) {
    return -1;
  }

  @Override
  public Preset findPreset(int id) {
    return null;
  }

  @Override
  public boolean updatePreset(Preset preset) {
    return false;
  }

  @Override
  public boolean deletePreset(int id) {
    return false;
  }

  @Override
  public List<Preset> getPresets() {
    return null;
  }
}
