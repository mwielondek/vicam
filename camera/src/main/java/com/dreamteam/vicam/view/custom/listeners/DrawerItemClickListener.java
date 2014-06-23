package com.dreamteam.vicam.view.custom.listeners;

import android.view.View;
import android.widget.AdapterView;

import com.dreamteam.vicam.model.events.PresetSelectedEvent;
import com.dreamteam.vicam.model.pojo.Preset;
import com.dreamteam.vicam.presenter.utility.Dagger;
import com.dreamteam.vicam.view.MainActivity;

import de.greenrobot.event.EventBus;

import javax.inject.Inject;

/**
 * Manages drawer selections and when an item is selected it closes the drawer and sends a {@link
 * com.dreamteam.vicam.model.events.PresetSelectedEvent} with the selected {@link Preset}.
 *
 * @author Fredrik Sommar
 * @since 2014-04-26.
 */
public class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {

  @Inject
  EventBus mEventBus;
  private final MainActivity mActivity;

  public DrawerItemClickListener(MainActivity activity) {
    Dagger.inject(this);
    this.mActivity = activity;
  }

  @Override
  public void onItemClick(AdapterView parent, View view, int position, long id) {
    Preset preset = (Preset) parent.getItemAtPosition(position);
    mActivity.closeDrawer();
    if (preset != null) {
      mEventBus.post(new PresetSelectedEvent(preset));
    }
  }

}