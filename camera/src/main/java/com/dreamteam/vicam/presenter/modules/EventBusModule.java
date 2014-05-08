package com.dreamteam.vicam.presenter.modules;

import com.dreamteam.vicam.view.MainActivity;
import com.dreamteam.vicam.view.custom.dialogs.AddCameraDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.EditCameraDialogFragment;
import com.dreamteam.vicam.view.custom.listeners.CameraSpinnerItemListener;
import com.dreamteam.vicam.view.custom.listeners.DrawerItemClickListener;
import com.dreamteam.vicam.view.custom.listeners.DrawerMultiChoiceListener;
import com.dreamteam.vicam.view.custom.DrawerToggle;
import com.dreamteam.vicam.view.custom.dialogs.EditPresetDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.SavePresetDialogFragment;

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
        EditPresetDialogFragment.class,
        DrawerToggle.class,
        DrawerMultiChoiceListener.class,
        AddCameraDialogFragment.class,
        EditCameraDialogFragment.class
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
