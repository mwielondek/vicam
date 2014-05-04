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
 * Created by fsommar on 2014-04-26.
 */
public class DrawerToggle extends ActionBarDrawerToggle {

  @Inject
  EventBus eventBus;
  private final Activity activity;

  public DrawerToggle(Activity activity, DrawerLayout drawerLayout) {
    super(activity, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
          R.string.drawer_close);
    Dagger.inject(this);
    this.activity = activity;
  }

  /**
   * Called when a drawer has settled in a completely closed state.
   */
  @Override
  public void onDrawerClosed(View view) {
    eventBus.post(new OnDrawerCloseEvent(view));
    activity.getActionBar().setTitle(activity.getString(R.string.app_name));
  }

  /**
   * Called when a drawer has settled in a completely open state.
   */
  @Override
  public void onDrawerOpened(View view) {
    activity.getActionBar().setTitle(activity.getString(R.string.change_preset));
  }
}
