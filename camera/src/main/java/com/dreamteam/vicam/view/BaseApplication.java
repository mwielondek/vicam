package com.dreamteam.vicam.view;

import android.app.Application;

import com.dreamteam.vicam.presenter.modules.MainModule;

import dagger.ObjectGraph;

/**
 * This class is always instantiated when the application is running and can be used for variables
 * that need to survive the lifetime of the application.
 *
 * @author Fredrik Sommar
 * @since 2014-02-05.
 */
public class BaseApplication extends Application {

  private static BaseApplication instance;
  private ObjectGraph objectGraph;

  /**
   * Returns the only instance of the application that exists, using the singleton pattern.
   */
  public static BaseApplication getInstance() {
    return instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    objectGraph = ObjectGraph.create(new MainModule());
  }

  /**
   * Returns the {@link dagger.ObjectGraph} for the current application instance.
   */
  public ObjectGraph getObjectGraph() {
    return objectGraph;
  }
}
