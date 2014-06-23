package com.dreamteam.vicam.presenter.modules;

import com.dreamteam.vicam.model.database.DAOFactory;
import com.dreamteam.vicam.model.database.ormlite.DAOFactoryImpl;
import com.dreamteam.vicam.view.MainActivity;
import com.dreamteam.vicam.view.custom.dialogs.DeleteCameraDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.EditCameraDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.EditPresetDialogFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A {@link dagger.ObjectGraph.DaggerObjectGraph Dagger} module for injecting {@link
 * com.dreamteam.vicam.model.database.DAOFactory} instances.
 *
 * @author Fredrik Sommar
 * @since 2014-04-26.
 */
@Module(
    injects = {
        MainActivity.class,
        EditPresetDialogFragment.class,
        EditCameraDialogFragment.class,
        DeleteCameraDialogFragment.class
    },
    complete = false,
    library = true
)
public class DAOFactoryModule {

  @Provides
  @Singleton
  public DAOFactory provideDAOFactory() {
    return new DAOFactoryImpl();
  }
}
