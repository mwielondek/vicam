package com.dreamteam.vicam.presenter.modules;

import dagger.Module;

/**
 * Created by fsommar on 2/20/14.
 */
@Module(
    includes = {
        EventBusModule.class,
        DAOFactoryModule.class
    }
)
public class MainModule {

}
