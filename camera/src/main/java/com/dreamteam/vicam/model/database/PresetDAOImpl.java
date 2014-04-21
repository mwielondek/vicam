package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Preset;
import com.j256.ormlite.dao.Dao;

import java.util.List;

import static com.dreamteam.vicam.presenter.utility.Utils.ORMLite;

/**
 * Created by fsommar on 2014-04-03.
 */
public class PresetDAOImpl implements PresetDAO {

  private Dao<Preset, Integer> presetDaoOrmLite;

  public PresetDAOImpl(
      Dao<Preset, Integer> presetDaoOrmLite) {
    this.presetDaoOrmLite = presetDaoOrmLite;
  }

  @Override
  public int insertPreset(Preset preset) {
    boolean success = ORMLite.insert(presetDaoOrmLite, preset);
    return success ? preset.getId() : -1;
  }

  @Override
  public Preset findPreset(int id) {
    return ORMLite.find(presetDaoOrmLite, id);
  }

  @Override
  public boolean updatePreset(Preset preset) {
    return ORMLite.update(presetDaoOrmLite, preset);
  }

  @Override
  public boolean deletePreset(int id) {
    return ORMLite.delete(presetDaoOrmLite, id);
  }

  @Override
  public List<Preset> getPresets() {
    return ORMLite.getAll(presetDaoOrmLite);
  }
}
