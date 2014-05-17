package com.dreamteam.vicam.presenter.utility;

import com.dreamteam.vicam.view.BaseApplication;

/**
 * Acts as a layer on top of the {@link dagger.ObjectGraph.DaggerObjectGraph Dagger} library by
 * Square by providing a simpler function to inject Dagger in the current scope in lieu with {@link
 * butterknife.ButterKnife}.
 *
 * @author Fredrik Sommar
 * @since 2014-02-06
 */
public class Dagger {

  /**
   * Injects any fields annotated with {@link javax.inject.Inject @Inject} with the corresponding
   * concrete implementation using a {@link dagger.Provides @Provides} annotation.
   */
  public static void inject(Object target) {
    BaseApplication.getInstance().getObjectGraph().inject(target);
  }
}
