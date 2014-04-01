package com.dreamteam.vicam.presenter.events;

import android.view.MenuItem;

/**
 * Created by fsommar on 2014-03-24.
 */
public class NavigationDrawerItemSelected {
  public MenuItem item;

  public NavigationDrawerItemSelected(MenuItem item) {
    this.item = item;
  }
}
