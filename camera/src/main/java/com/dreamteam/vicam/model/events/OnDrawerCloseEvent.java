package com.dreamteam.vicam.model.events;

import android.view.View;

/**
 * This event is fired when the navigation drawer on the left side of the application holding all
 * the presets is closing.
 *
 * @author Fredrik Sommar
 * @since 2014-04-26.
 */
public class OnDrawerCloseEvent {

  /**
   * The {@link android.view.View} object representation of the navigation drawer that is closing.
   */
  public final View view;

  /**
   * An event indicating that the navigation drawer on the left side of the application is closing.
   *
   * @param view The view of the navigation drawer that is closing.
   */
  public OnDrawerCloseEvent(View view) {
    this.view = view;
  }
}
