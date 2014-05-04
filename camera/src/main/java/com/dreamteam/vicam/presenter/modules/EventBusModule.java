package com.dreamteam.vicam.presenter.modules;

import com.dreamteam.vicam.view.MainActivity;
import com.dreamteam.vicam.view.custom.CameraSpinnerItemListener;
import com.dreamteam.vicam.view.custom.DrawerItemClickListener;
import com.dreamteam.vicam.view.custom.DrawerMultiChoiceListener;
import com.dreamteam.vicam.view.custom.DrawerToggle;
import com.dreamteam.vicam.view.custom.SavePresetDialogFragment;

import de.greenrobot.event.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fsommar on 2/5/14.
 */
@Module(
    injects = {
        MainActivity.class,
        CameraSpinnerItemListener.class,
        DrawerItemClickListener.class,
        SavePresetDialogFragment.class,
        DrawerToggle.class,
        DrawerMultiChoiceListener.class
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
