package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Preset;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

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
    int insertRows = 0;
    try {
      insertRows = presetDaoOrmLite.create(preset);
    } catch (SQLException e) {
      return -1;
    }
    if (insertRows == 0) {
      return -1;
    }
    return preset.getId();
  }

  @Override
  public Preset findPreset(int id) {
    try {
      return presetDaoOrmLite.queryForId(id);
    } catch (SQLException e) {
      return null;
    }
  }

  @Override
  public boolean updatePreset(Preset preset) {
    try {
      int updatedRows = presetDaoOrmLite.update(preset);
      return updatedRows == 1;
    } catch (SQLException e) {
      return false;
    }
  }

  @Override
  public boolean deletePreset(int id) {
    try {
      int deletedRows = presetDaoOrmLite.deleteById(id);
      return deletedRows == 1;
    } catch (SQLException e) {
      return false;
    }
  }

  @Override
  public List<Preset> getPresets() {
    try {
      return presetDaoOrmLite.queryForAll();
    } catch (SQLException e) {
      return null;
    }
  }
}
