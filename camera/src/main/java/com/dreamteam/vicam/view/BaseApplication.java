package com.dreamteam.vicam.view;

import android.app.Application;

import com.dreamteam.vicam.presenter.modules.MainModule;

import dagger.ObjectGraph;

/**
 * Created by fsommar on 2/5/14.
 */
public class BaseApplication extends Application {
  private static BaseApplication instance;
  private ObjectGraph objectGraph;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    objectGraph = ObjectGraph.create(new MainModule());
  }

  public ObjectGraph getObjectGraph() {
    return objectGraph;
  }

  public static BaseApplication getInstance() {
    return instance;
  }
}
