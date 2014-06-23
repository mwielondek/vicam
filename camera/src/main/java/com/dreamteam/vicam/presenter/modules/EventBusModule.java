package com.dreamteam.vicam.presenter.modules;

import com.dreamteam.vicam.view.MainActivity;
import com.dreamteam.vicam.view.custom.DrawerToggle;
import com.dreamteam.vicam.view.custom.dialogs.AddCameraDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.DeleteCameraDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.EditCameraDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.EditPresetDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.SavePresetDialogFragment;
import com.dreamteam.vicam.view.custom.listeners.CameraSpinnerItemListener;
import com.dreamteam.vicam.view.custom.listeners.DrawerItemClickListener;
import com.dreamteam.vicam.view.custom.listeners.DrawerMultiChoiceListener;

import de.greenrobot.event.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A {@link dagger.ObjectGraph.DaggerObjectGraph Dagger} module for injecting {@link
 * de.greenrobot.event.EventBus} instances.
 *
 * @author Fredrik Sommar
 * @since 2014-02-05.
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
        EditCameraDialogFragment.class,
        DeleteCameraDialogFragment.class
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
