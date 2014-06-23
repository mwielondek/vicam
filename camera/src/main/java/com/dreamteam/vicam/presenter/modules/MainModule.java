package com.dreamteam.vicam.presenter.modules;

import dagger.Module;

/**
 * A {@link dagger.ObjectGraph.DaggerObjectGraph Dagger} module for collecting the other modules.
 *
 * @author Fredrik Sommar
 * @since 2014-02-20.
 */
@Module(
    includes = {
        EventBusModule.class,
        DAOFactoryModule.class
    }
)
public class MainModule {

}
