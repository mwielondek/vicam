package com.dreamteam.vicam.view.custom;

import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.events.OnDrawerCloseEvent;
import com.dreamteam.vicam.presenter.utility.Dagger;

import de.greenrobot.event.EventBus;

import javax.inject.Inject;

/**
 * A listener that manages the toggle of the preset drawer. When the drawer is closed a {@link
 * com.dreamteam.vicam.model.events.OnDrawerCloseEvent} is fired.
 *
 * @author Benny Tieu
 */
public class DrawerToggle extends ActionBarDrawerToggle {

  @Inject
  EventBus eventBus;

  public DrawerToggle(Activity activity, DrawerLayout drawerLayout) {
    super(activity, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
          R.string.drawer_close);
    Dagger.inject(this);
  }

  /**
   * Called when a drawer has settled in a completely closed state.
   */
  @Override
  public void onDrawerClosed(View view) {
    eventBus.post(new OnDrawerCloseEvent(view));
  }

}
