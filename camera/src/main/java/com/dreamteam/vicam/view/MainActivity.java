package com.dreamteam.vicam.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.database.CameraDAO;
import com.dreamteam.vicam.model.database.DAOFactory;
import com.dreamteam.vicam.model.database.PresetDAO;
import com.dreamteam.vicam.model.errors.CameraDoesNotExistException;
import com.dreamteam.vicam.model.errors.CameraResponseException;
import com.dreamteam.vicam.model.events.CameraChangedEvent;
import com.dreamteam.vicam.model.events.DeleteCameraEvent;
import com.dreamteam.vicam.model.events.DeletePresetsEvent;
import com.dreamteam.vicam.model.events.EditCameraEvent;
import com.dreamteam.vicam.model.events.EditPresetDialogEvent;
import com.dreamteam.vicam.model.events.EditPresetEvent;
import com.dreamteam.vicam.model.events.OnDrawerCloseEvent;
import com.dreamteam.vicam.model.events.PresetChangedEvent;
import com.dreamteam.vicam.model.events.SaveCameraEvent;
import com.dreamteam.vicam.model.events.SavePresetEvent;
import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.model.pojo.CameraState;
import com.dreamteam.vicam.model.pojo.Focus;
import com.dreamteam.vicam.model.pojo.Position;
import com.dreamteam.vicam.model.pojo.Preset;
import com.dreamteam.vicam.model.pojo.Zoom;
import com.dreamteam.vicam.presenter.CameraServiceManager;
import com.dreamteam.vicam.presenter.network.camera.CameraFacade;
import com.dreamteam.vicam.presenter.utility.Dagger;
import com.dreamteam.vicam.presenter.utility.Utils;
import com.dreamteam.vicam.view.custom.CameraArrayAdapter;
import com.dreamteam.vicam.view.custom.DrawerToggle;
import com.dreamteam.vicam.view.custom.PresetArrayAdapter;
import com.dreamteam.vicam.view.custom.dialogs.AboutPageDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.AddCameraDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.DeleteCameraDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.EditCameraDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.EditPresetDialogFragment;
import com.dreamteam.vicam.view.custom.dialogs.SavePresetDialogFragment;
import com.dreamteam.vicam.view.custom.listeners.CameraSpinnerItemListener;
import com.dreamteam.vicam.view.custom.listeners.DrawerItemClickListener;
import com.dreamteam.vicam.view.custom.listeners.DrawerMultiChoiceListener;
import com.dreamteam.vicam.view.custom.listeners.SeekBarChangeListener;
import com.dreamteam.vicam.view.custom.listeners.SwitchButtonCheckedListener;
import com.dreamteam.vicam.view.custom.listeners.TouchpadTouchListener;

import de.greenrobot.event.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.dreamteam.vicam.view.custom.listeners.SeekBarChangeListener.Type;

public class MainActivity extends Activity {

  private final String SELECTED_CAMERA = "SELECTED_CAMERA";

  @Inject
  EventBus mEventBus;
  @Inject
  DAOFactory mDAOFactory;

  @InjectView(R.id.sync_loader)
  RelativeLayout mLoaderSpinner;
  @InjectView(R.id.drawer_layout)
  DrawerLayout mDrawerLayout;
  @InjectView(R.id.navigation_drawer)
  ListView mDrawerList;
  @InjectView(R.id.focus_seekbar)
  SeekBar mFocusSeekBar;
  @InjectView(R.id.zoom_seekbar)
  SeekBar mZoomSeekBar;
  @InjectView(R.id.camera_touchpad)
  View mTouchpad;
  @InjectView(R.id.one_touch_autofocus)
  Button mAutofocusButton;
  @InjectView(R.id.autofocus_switch)
  Switch mAutofocusSwitch;

  private Camera mCurrentCamera;
  private CharSequence mTitle;
  private List<Preset> mPresets;
  private List<Camera> mCameras;
  private ActionBarDrawerToggle mDrawerToggle;
  private CameraArrayAdapter mCameraAdapter;
  private PresetArrayAdapter mPresetAdapter;
  private DrawerMultiChoiceListener mContextualActionBar;
  private Spinner mCameraSpinner;
  private SharedPreferences mSharedPreferences;
  private MenuItem mConnectedIcon;
  private Action1<Throwable> mErrorHandler;
  private Action0 mSuccessHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Dagger.inject(this);
    ButterKnife.inject(this);

    // Sets default values defined in camera_preferences if empty
    // Only useful if settings activity is used
    // PreferenceManager.setDefaultValues(this, R.xml.camera_preferences, false);

    // Get set camera_preferences
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    mTitle = getString(R.string.app_name);

    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);
    getActionBar().setDisplayShowTitleEnabled(true);

    mPresets = new ArrayList<>();
    mPresetAdapter = new PresetArrayAdapter(this, mPresets);

    mDrawerList.setAdapter(mPresetAdapter);
    mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this));
    mDrawerList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
    mContextualActionBar = new DrawerMultiChoiceListener(this, mDrawerList);
    mDrawerList.setMultiChoiceModeListener(mContextualActionBar);
    mDrawerToggle = new DrawerToggle(this, mDrawerLayout);
    mDrawerLayout.setDrawerListener(mDrawerToggle);

    mFocusSeekBar.setOnSeekBarChangeListener(new SeekBarChangeListener(this, Type.FOCUS));
    mZoomSeekBar.setOnSeekBarChangeListener(new SeekBarChangeListener(this, Type.ZOOM));

    mTouchpad.setOnTouchListener(new TouchpadTouchListener(this));

    final SwitchButtonCheckedListener switchListener = new SwitchButtonCheckedListener(this);
    mAutofocusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton switchButton, boolean isAutofocus) {
        mAutofocusButton.setEnabled(!isAutofocus);
        mFocusSeekBar.setEnabled(!isAutofocus);
        switchListener.onCheckedChanged(switchButton, isAutofocus);
      }
    });

    mCameras = getCameraDAO().getCameras();
    if (mCameras == null) {
      mCameras = new ArrayList<>();
    }
    mCameraAdapter = new CameraArrayAdapter(this, mCameras);

    // Always show settings drop down (works with e.g. Samsung S3)
    getOverflowMenu();

    mErrorHandler = new Action1<Throwable>() {
      @Override
      public void call(Throwable throwable) {
        if (throwable instanceof RetrofitError) {
          RetrofitError err = (RetrofitError) throwable;
          Utils.errorLog("RetroFitError URL: " + err.getUrl());
        } else if (throwable instanceof CameraResponseException) {
          CameraResponseException err = (CameraResponseException) throwable;
          Utils.errorLog("CameraResponseException: " + err.getMessage());
        } else if (throwable instanceof CameraDoesNotExistException) {
          showToast("Add a camera first!", Toast.LENGTH_SHORT);
          // TODO: Show the add camera dialog directly?
        }
        Utils.errorLog(Utils.throwableToString(throwable));
        connectionError();
      }
    };

    mSuccessHandler = new Action0() {
      @Override
      public void call() {
        connectionSuccess();
      }
    };
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.

    getMenuInflater().inflate(R.menu.main, menu);

    MenuItem cameraSpinner = menu.findItem(R.id.action_change_camera);
    View view = cameraSpinner.getActionView();
    if (view instanceof Spinner) {
      mCameraSpinner = (Spinner) view;
      mCameraSpinner.setAdapter(mCameraAdapter);
      mCameraSpinner.setOnItemSelectedListener(new CameraSpinnerItemListener());
      int selected = mSharedPreferences.getInt(SELECTED_CAMERA, 0);
      mCameraSpinner.setSelection(selected);
    }
    mConnectedIcon = menu.findItem(R.id.connection_state);

    return true;
  }
/*
  @Override
  public boolean onPrepareOptionsMenu (Menu menu) {

    // Disables menu items when not in use
    if(mCurrentCamera == null) {
      menu.findItem(R.id.action_edit_camera).setEnabled(false);
      menu.findItem(R.id.action_delete_camera).setEnabled(false);
      menu.findItem(R.id.action_save_preset).setEnabled(false);
    } else {
      menu.findItem(R.id.action_edit_camera).setEnabled(true);
      menu.findItem(R.id.action_delete_camera).setEnabled(true);
      menu.findItem(R.id.action_save_preset).setEnabled(true);
    }
    return true;
  }
  */

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    // Sync the toggle state after onRestoreInstanceState has occurred.
    mDrawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);


  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    // Pass the event to ActionBarDrawerToggle, if it returns
    // true, then it has handled the app icon touch event
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    // Handle menu items
    // TODO: check for null mCurrentCamera
    switch (item.getItemId()) {
      case R.id.action_edit_camera:
        //startActivity(new Intent(this, SettingsActivity.class));
        if (mCurrentCamera == null) {
          showToast("There's no camera to be edited!", Toast.LENGTH_SHORT);
        } else {
          showDialog(EditCameraDialogFragment.newInstance(mCurrentCamera.getId()),
                     "edit_camera_dialog");
        }
        return true;

      case R.id.action_add_camera:
        showDialog(AddCameraDialogFragment.newInstance(), "add_camera_dialog");
        mCameraSpinner.setVisibility(View.VISIBLE);
        return true;

      case R.id.action_delete_camera:
        if (mCurrentCamera == null) {

          showToast("There's no camera to be deleted!", Toast.LENGTH_SHORT);
        } else {
          showDialog(DeleteCameraDialogFragment.newInstance(mCurrentCamera.getId()), "delete_camera_dialog");
        }

        return true;

      case R.id.action_about:
        showDialog(AboutPageDialogFragment.newInstance(), "about_page_dialog");
        return true;

      case R.id.action_save_preset:
        showDialog(SavePresetDialogFragment.newInstance(), "save_preset_dialog");
        return true;

      case R.id.action_sync_presets:
        mLoaderSpinner.setVisibility(View.VISIBLE);
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void setTitle(CharSequence title) {
    mTitle = title;
    getActionBar().setTitle(mTitle);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mEventBus.register(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    mEventBus.unregister(this);
    if (mCameraSpinner != null) {
      mSharedPreferences.edit()
          .putInt(SELECTED_CAMERA, mCameraSpinner.getSelectedItemPosition())
          .commit();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mDAOFactory.close();
  }

  // Disable exit app when back pressed.
  @Override
  public void onBackPressed() {
    new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Exit VICAM?")
        .setMessage("Are you sure you want to exit VICAM?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            finish();
          }

        })
        .setNegativeButton("No", null)
        .show();
  }

  public Observable<CameraFacade> getFacade() {
    return getCurrentCamera().map(new Func1<Camera, CameraFacade>() {
      @Override
      public CameraFacade call(Camera camera) {
        return CameraServiceManager.getFacadeFor(camera);
      }
    });
  }

  public <T> Observable<T> prepareObservable(Observable<T> observable) {
    return observable
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .doOnError(mErrorHandler)
        .doOnCompleted(mSuccessHandler);
  }

  public void showToast(String msg, int length) {
    Toast.makeText(this, msg, length).show();
  }

  public void connectionSuccess() {
    mConnectedIcon.setIcon(android.R.drawable.presence_online);
  }

  public void connectionError() {
    mConnectedIcon.setIcon(android.R.drawable.presence_busy);
  }

  public Observable<Camera> getCurrentCamera() {
    if (mCurrentCamera == null) {
      return Observable.error(
          new CameraDoesNotExistException("No camera is currently selected."));
    }
    return Observable.just(mCurrentCamera);
  }

  @OnClick(R.id.one_touch_autofocus)
  @SuppressWarnings("unused")
  public void OneTouchAutofocusClick(Button button) {
    prepareObservable(
        getFacade()
            .flatMap(new Func1<CameraFacade, Observable<Integer>>() {
              @Override
              public Observable<Integer> call(final CameraFacade cameraFacade) {
                return cameraFacade.oneTouchFocus().flatMap(
                    new Func1<String, Observable<Integer>>() {
                      @Override
                      public Observable<Integer> call(String s) {
                        return CameraFacade.accountForDelay(cameraFacade.getFocusLevel());
                      }
                    }
                );
              }
            })
    ).subscribe(
        new Action1<Integer>() {
          @Override
          public void call(Integer focusLevel) {
            updateFocusLevel(focusLevel);
          }
        }, Utils.<Throwable>noop()
    );
  }

  public void closeDrawer() {
    mDrawerLayout.closeDrawer(mDrawerList);
  }

  private void showDialog(DialogFragment dialog, String tag) {
    FragmentManager manager = getFragmentManager();
    FragmentTransaction ft = manager.beginTransaction();
    Fragment prev = manager.findFragmentByTag(tag);
    if (prev != null) {
      ft.remove(prev);
    }

    // Create and show the dialog.
    dialog.show(ft, tag);
  }

  private void updateWithCameraState(CameraState cameraState) {
    updateFocusLevel(cameraState.getFocus().getLevel());
    updateZoomLevel(cameraState.getZoom().getLevel());
    mAutofocusSwitch.setChecked(cameraState.isAF());
  }

  public void updateFocusLevel(int focusLevel) {
    int progress = SeekBarChangeListener.levelToProgress(focusLevel, mFocusSeekBar.getMax());
    mFocusSeekBar.setProgress(progress);
  }

  public void updateZoomLevel(int zoomLevel) {
    int progress = SeekBarChangeListener.levelToProgress(zoomLevel, mZoomSeekBar.getMax());
    mZoomSeekBar.setProgress(progress);
  }

  private void getOverflowMenu() {
    try {
      ViewConfiguration config = ViewConfiguration.get(this);
      Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
      if (menuKeyField != null) {
        menuKeyField.setAccessible(true);
        menuKeyField.setBoolean(config, false);
      }
    } catch (Exception e) {
      Utils.errorLog(Utils.throwableToString(e));
    }
  }

  private void updateCameraState() {
    prepareObservable(
        getFacade().flatMap(new Func1<CameraFacade, Observable<CameraState>>() {
          @Override
          public Observable<CameraState> call(CameraFacade cameraFacade) {
            return cameraFacade.getCameraState();
          }
        })
    ).subscribe(
        new Action1<CameraState>() {
          @Override
          public void call(CameraState cameraState) {
            updateWithCameraState(cameraState);
          }
        }, Utils.<Throwable>noop()
    );
  }

  @SuppressWarnings("unused")
  public void onEventMainThread(CameraChangedEvent e) {
    mCurrentCamera = e.camera;
    List<Preset> presets = getPresetDAO().getPresetsForCamera(mCurrentCamera);
    mPresets.clear();
    mPresets.addAll(presets);
    mPresetAdapter.notifyDataSetChanged();
    updateCameraState();
  }

  @SuppressWarnings("unused")
  public void onEventMainThread(SaveCameraEvent e) {
    insertCamera(new Camera(e.ip, e.name, Camera.parsePort(e.port.trim()), e.invertX, e.invertY));
    // Selects the inserted camera as current
    mCameraSpinner.setSelection(mCameras.size() - 1);
  }

  @SuppressWarnings("unused")
  public void onEventMainThread(EditCameraEvent e) {
    updateCamera(e.camera);
  }
  @SuppressWarnings("unused")
  public void onEventMainThread(DeleteCameraEvent e) {
    deleteCamera(e.camera);
  }

  @SuppressWarnings("unused")
  public void onEventMainThread(PresetChangedEvent e) {
    final CameraState cameraState = e.preset.getCameraState();

    prepareObservable(
        getFacade().flatMap(new Func1<CameraFacade, Observable<Boolean>>() {
          @Override
          public Observable<Boolean> call(CameraFacade cameraFacade) {
            return cameraFacade.setCameraState(cameraState);
          }
        })
    ).subscribe(
        new Action1<Boolean>() {
          @Override
          public void call(Boolean b) {
            updateWithCameraState(cameraState);
          }
        }, Utils.<Throwable>noop()
    );
  }

  @SuppressWarnings("unused")
  public void onEventMainThread(OnDrawerCloseEvent e) {
    mContextualActionBar.close();
  }

  @SuppressWarnings("unused")
  public void onEventMainThread(final SavePresetEvent e) {
    prepareObservable(
        getFacade().flatMap(new Func1<CameraFacade, Observable<CameraState>>() {
          @Override
          public Observable<CameraState> call(CameraFacade cameraFacade) {
            return cameraFacade.getCameraState();
          }
        })
    ).subscribe(
        new Action1<CameraState>() {
          @Override
          public void call(CameraState cameraState) {
            insertPreset(new Preset(e.name, mCurrentCamera, cameraState));
          }
        },
        new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            Utils.infoLog("Failed getting state from camera when saving preset");
            // TODO Remove when done with debugging
            insertPreset(new Preset(e.name, mCurrentCamera, new CameraState(
                new Position(0x5000, 0x5000),
                new Zoom(0x666),
                new Focus(0x777, true))));
          }
        }
    );
  }

  @SuppressWarnings("unused")
  public void onEventMainThread(DeletePresetsEvent e) {
    deletePresets(e.presets);
  }

  @SuppressWarnings("unused")
  public void onEventMainThread(EditPresetEvent e) {
    updatePreset(e.preset);
  }

  @SuppressWarnings("unused")
  public void onEventMainThread(EditPresetDialogEvent e) {
    showDialog(EditPresetDialogFragment.newInstance(e.preset.getId()), "edit_preset_dialog");
  }

  private CameraDAO getCameraDAO() {
    return mDAOFactory.getCameraDAO();
  }

  private PresetDAO getPresetDAO() {
    return mDAOFactory.getPresetDAO();
  }

  public void insertPreset(Preset preset) {
    PresetDAO presetDao = getPresetDAO();
    presetDao.insertPreset(preset);
    mPresets.add(preset);
    mPresetAdapter.notifyDataSetChanged();
  }

  public void updatePreset(Preset preset) {
    PresetDAO presetDao = getPresetDAO();
    presetDao.updatePreset(preset);
    for (int i = 0; i < mPresets.size(); i++) {
      if (mPresets.get(i).getId() == preset.getId()) {
        mPresets.set(i, preset);
        break;
      }
    }
    mPresetAdapter.notifyDataSetChanged();
  }

  public void deletePresets(List<Preset> presets) {
    PresetDAO presetDao = getPresetDAO();
    for (Preset p : presets) {
      presetDao.deletePreset(p.getId());
      for (int i = 0; i < mPresets.size(); i++) {
        if (mPresets.get(i).getId() == p.getId()) {
          mPresets.remove(i);
          break;
        }
      }
    }
    mPresetAdapter.notifyDataSetChanged();
  }

  public void insertCamera(Camera camera) {
    CameraDAO cameraDAO = getCameraDAO();
    cameraDAO.insertCamera(camera);
    mCameras.add(camera);
    mCameraAdapter.notifyDataSetChanged();
  }

  public void updateCamera(Camera camera) {
    CameraDAO cameraDao = getCameraDAO();
    cameraDao.updateCamera(camera);
    for (int i = 0; i < mCameras.size(); i++) {
      if (mCameras.get(i).getId() == camera.getId()) {
        mCameras.set(i, camera);
        break;
      }
    }
    mCameraAdapter.notifyDataSetChanged();
  }

  public void deleteCamera(Camera camera) {
    CameraDAO cameraDAO = getCameraDAO();
    cameraDAO.deleteCamera(camera.getId());
    for (int i = 0; i < mCameras.size(); i++) {
      if (mCameras.get(i).getId() == camera.getId()) {
        mCameras.remove(i);
        break;
      }
    }
    mCurrentCamera = null;
    mCameraAdapter.notifyDataSetChanged();
  }

}
