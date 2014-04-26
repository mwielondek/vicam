package com.dreamteam.vicam.view.custom;

import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.dreamteam.camera.R;

/**
 * Created by fsommar on 2014-04-26.
 */
public class DrawerToggle extends ActionBarDrawerToggle {

  private final Activity activity;

  public DrawerToggle(Activity activity, DrawerLayout drawerLayout) {
    super(activity, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
          R.string.drawer_close);
    this.activity = activity;
  }

  /**
   * Called when a drawer has settled in a completely closed state.
   */
  @Override
  public void onDrawerClosed(View view) {
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
