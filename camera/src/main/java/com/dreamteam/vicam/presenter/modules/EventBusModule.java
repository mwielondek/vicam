package com.dreamteam.vicam.presenter.modules;

import com.dreamteam.vicam.view.MainActivity;

import de.greenrobot.event.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fsommar on 2/5/14.
 */
@Module(
    injects = {
        MainActivity.class
    },
    complete = false,
    library = true
)
public class EventBusModule {

  @Provides
  @Singleton
  public EventBus provideEventBus() {
    return new EventBus();
  }
}
