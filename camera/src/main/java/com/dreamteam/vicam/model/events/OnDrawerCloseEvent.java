package com.dreamteam.vicam.model.events;

import android.view.View;

/**
 * Created by fsommar on 2014-04-26.
 */
public class OnDrawerCloseEvent {

  public final View view;

  public OnDrawerCloseEvent(View view) {
    this.view = view;
  }
}
