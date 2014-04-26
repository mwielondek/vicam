package com.dreamteam.vicam.presenter.modules;

import com.dreamteam.vicam.model.database.DAOFactory;
import com.dreamteam.vicam.model.database.ormlite.DAOFactoryImpl;
import com.dreamteam.vicam.view.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fsommar on 2014-04-26.
 */
@Module(
    injects = {
        MainActivity.class,
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
