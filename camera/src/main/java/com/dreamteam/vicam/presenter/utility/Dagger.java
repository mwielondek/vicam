package com.dreamteam.vicam.presenter.utility;


import com.dreamteam.vicam.view.BaseApplication;

/**
 * Created by fsommar on 2/6/14.
 */
public class Dagger {

  public static void inject(Object activity) {
    BaseApplication.getInstance().getObjectGraph().inject(activity);
  }
}
