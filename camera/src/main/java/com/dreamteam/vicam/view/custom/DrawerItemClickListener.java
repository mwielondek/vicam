package com.dreamteam.vicam.view.custom;

import android.view.View;
import android.widget.AdapterView;

import com.dreamteam.vicam.model.events.DrawerCloseEvent;
import com.dreamteam.vicam.model.events.PresetChangedEvent;
import com.dreamteam.vicam.model.pojo.Preset;
import com.dreamteam.vicam.presenter.utility.Dagger;

import de.greenrobot.event.EventBus;

import javax.inject.Inject;

/**
 * Created by fsommar on 2014-04-26.
 */
public class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {

  @Inject
  EventBus mEventBus;

  public DrawerItemClickListener() {
    Dagger.inject(this);
  }

  @Override
  public void onItemClick(AdapterView parent, View view, int position, long id) {
    Preset preset = (Preset) parent.getItemAtPosition(position);
    mEventBus.post(new DrawerCloseEvent());
    if (preset != null) {
      mEventBus.post(new PresetChangedEvent(preset));
    }
  }

}