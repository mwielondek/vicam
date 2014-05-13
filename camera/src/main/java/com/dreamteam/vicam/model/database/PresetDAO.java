package com.dreamteam.vicam.model.database;

import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.model.pojo.Preset;

import java.util.List;

import rx.Observable;

/**
 * Created by fsommar on 2014-04-01.
 */
public interface PresetDAO {

  public Observable<Integer> insertPreset(Preset preset);

  public Observable<Preset> findPreset(int id);

  public Observable<Boolean> updatePreset(Preset preset);

  public Observable<Boolean> deletePreset(int id);

  public Observable<List<Preset>> getPresets();

  public Observable<List<Preset>> getPresetsForCamera(Camera c);

}
