package com.dreamteam.vicam.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.database.CameraDAO;
import com.dreamteam.vicam.model.database.DatabaseOrmLiteHelper;
import com.dreamteam.vicam.model.database.PresetDAO;
import com.dreamteam.vicam.model.events.CameraChangedEvent;
import com.dreamteam.vicam.model.events.PresetChangedEvent;
import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.model.pojo.CameraState;
import com.dreamteam.vicam.model.pojo.Focus;
import com.dreamteam.vicam.model.pojo.Position;
import com.dreamteam.vicam.model.pojo.Preset;
import com.dreamteam.vicam.model.pojo.Zoom;
import com.dreamteam.vicam.presenter.utility.Dagger;
import com.dreamteam.vicam.view.custom.CameraArrayAdapter;
import com.dreamteam.vicam.view.custom.PresetArrayAdapter;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends Activity {

  @Inject
  EventBus eventBus;

  private Camera mCurrentCamera;
  private CharSequence mTitle;
  private List<Preset> mPresets;

  private ActionBarDrawerToggle mDrawerToggle;
  private CameraArrayAdapter mCameraAdapter;
  private PresetArrayAdapter mPresetAdapter;
  private AlertDialog mDialogSavePreset;
  private DatabaseOrmLiteHelper mDatabaseHelper;

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
  @InjectView(R.id.focus_value)
  TextView mFocusValue;
  @InjectView(R.id.zoom_value)
  TextView mZoomValue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Dagger.inject(this);
    ButterKnife.inject(this);
    // Sets default values defined in camera_preferences if empty
    PreferenceManager.setDefaultValues(this, R.xml.camera_preferences, false);
    // Get set camera_preferences
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

    mTitle = getString(R.string.app_name);

    CameraState dummy = new CameraState(
        new Position(0x8000, 0x8000), new Zoom(0x555), new Focus(0x555, true));

    PresetDAO presetDao = getHelper().getPresetDAO();
    List<Preset> presets = presetDao.getPresets();
    if (presets != null) {
      mPresets = presets;
    } else {
      mPresets = new ArrayList<Preset>();
    }

    mPresetAdapter = new PresetArrayAdapter(this, mPresets);
    mDrawerList.setAdapter(mPresetAdapter);
    mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    mDrawerToggle = new DrawerToggle(this, mDrawerLayout);
    mDrawerLayout.setDrawerListener(mDrawerToggle);

    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);
    getActionBar().setDisplayShowTitleEnabled(true);

    CameraDAO cameraDao = getHelper().getCameraDAO();
    List<Camera> cameras = cameraDao.getCameras();
    if (cameras == null) {
      cameras = new ArrayList<Camera>();
    }
    if (cameras.isEmpty()) {
      cameraDao.insertCamera(new Camera("127.0.0.1", "Camera 1", null));
      cameraDao.insertCamera(new Camera("localhost", "Camera 2", null));
      cameraDao.insertCamera(new Camera("localhost", "Camera 3", null));
      cameraDao.insertCamera(new Camera("localhost", "Camera 4", null));
      cameraDao.insertCamera(new Camera("localhost", "Camera 5", null));
      cameras = cameraDao.getCameras();
    }
    mCameraAdapter = new CameraArrayAdapter(this, cameras);

    mFocusSeekBar.setOnSeekBarChangeListener(new SeekBarChangeListener(SeekBarType.FOCUS));
    mZoomSeekBar.setOnSeekBarChangeListener(new SeekBarChangeListener(SeekBarType.ZOOM));


    AlertDialog.Builder builderSavePreset = new AlertDialog.Builder(this);
    builderSavePreset.setTitle(R.string.dialog_save_preset_title);

    // Set an EditText view to get user input
    final EditText input = new EditText(this);
    builderSavePreset.setView(input);

    builderSavePreset.setPositiveButton(
        R.string.dialog_ok,
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            mLoaderSpinner.setVisibility(View.GONE);
          }
        }
    );
    builderSavePreset.setNegativeButton(
        R.string.dialog_cancel,
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
          }
        }
    );
    mDialogSavePreset = builderSavePreset.create();
    mLoaderSpinner.setVisibility(View.GONE);

    mFocusSeekBar.setOnSeekBarChangeListener(new SeekBarChangeListener(SeekBarType.FOCUS));
    mZoomSeekBar.setOnSeekBarChangeListener(new SeekBarChangeListener(SeekBarType.ZOOM));
  }

  @OnClick(R.id.one_touch_autofocus)
  public void OneTouchAutofocusClick(Button button) {
    // Temporary for testing only!
    Preset preset = new Preset("Preset " + (int) (1 + Math.random() * 100),
                               mCurrentCamera,
                               new CameraState(new Position(0x8500, 0x9500),
                                               new Zoom(0xFFF),
                                               new Focus(0xFFF, false))
    );
    int id = getHelper().getPresetDAO().insertPreset(preset);
    if (id > 0) {
      mPresets.add(preset);
      mPresetAdapter.notifyDataSetChanged();
    }
  }





  @OnClick(R.id.camera_touchpad)
  public void testclick(View v) {
    // Temporary for testing only!
    PresetDAO presetDao = getHelper().getPresetDAO();
    Preset prevPreset = presetDao.findPreset(1); // will cause NPE if Preset with ID 1 doesn't exist
    Log.i("CUSTOM", String.format("getCamera(%s)", prevPreset.getCamera()));
    Preset newPreset = prevPreset.copy()
        .name(prevPreset.getName() + "3")
        .cameraState(prevPreset.getCameraState().copy()
                         .zoom(new Zoom(0x666))
                         .commit())
        .commit();
    presetDao.updatePreset(newPreset);
    for (int i = 0; i < mPresets.size(); i++) {
      if (mPresets.get(i).getId() == newPreset.getId()) {
        mPresets.set(i, newPreset);
        break;
      }
    }
    mPresetAdapter.notifyDataSetChanged();
    showToast("Updated preset!", Toast.LENGTH_SHORT);
  }

  // Load to loader spinner
  public void load(View view) {
    mLoaderSpinner.setVisibility(View.VISIBLE);

    // Trying to disable all the background and put a grey transparent shadow over the whole view
    /*
    ListView layout = (ListView)findViewById(R.id.navigation_drawer);
    layout.setEnabled(false);

    for (int i = 0; i < layout.getChildCount(); i++) {
      View child = layout.getChildAt(i);
      child.setEnabled(false);
    }
    */
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);

    MenuItem cameraSpinner = menu.findItem(R.id.action_change_camera);
    View spinnerView = cameraSpinner.getActionView();
    if (spinnerView instanceof Spinner) {
      final Spinner spinner = (Spinner) spinnerView;
      spinner.setAdapter(mCameraAdapter);

      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          Camera camera = (Camera) parent.getItemAtPosition(position);
          updateCamera(camera);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
      });

    }
    return true;
  }

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
    switch (item.getItemId()) {
      case R.id.action_settings:
        startActivity(new Intent(this, SettingsActivity.class));
        return true;
      case R.id.action_save_preset:
        mDialogSavePreset.show();
        return true;
      case R.id.action_sync_presets:
        load(mLoaderSpinner);

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
    eventBus.register(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    eventBus.unregister(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mDatabaseHelper != null) {
      OpenHelperManager.releaseHelper();
      mDatabaseHelper = null;
    }
  }

  private DatabaseOrmLiteHelper getHelper() {
    if (mDatabaseHelper == null) {
      mDatabaseHelper = OpenHelperManager.getHelper(this, DatabaseOrmLiteHelper.class);
    }
    return mDatabaseHelper;
  }

  private void showToast(String msg, int length) {
    Toast.makeText(this, msg, length).show();
  }

  private void updatePreset(Preset preset) {
    mDrawerLayout.closeDrawer(mDrawerList);
    eventBus.post(new PresetChangedEvent(preset));
  }

  private void updateCamera(Camera camera) {
    if (camera == null) {
      throw new IllegalArgumentException("Camera cannot be null!");
    }
    mCurrentCamera = camera;
    eventBus.post(new CameraChangedEvent(camera));
  }

  public void onEventMainThread(CameraChangedEvent e) {
    showToast("Current Camera: " + e.camera, Toast.LENGTH_SHORT);
  }

  public void onEventMainThread(PresetChangedEvent e) {
    showToast("Selected Preset: " + e.preset, Toast.LENGTH_SHORT);
  }

  private class DrawerItemClickListener implements ListView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
      Preset preset = (Preset) parent.getItemAtPosition(position);
      updatePreset(preset);
    }
  }

  private class DrawerToggle extends ActionBarDrawerToggle {

    private DrawerToggle(Activity activity, DrawerLayout drawerLayout) {
      super(activity, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
            R.string.drawer_close);
    }

    /**
     * Called when a drawer has settled in a completely closed state.
     */
    @Override
    public void onDrawerClosed(View view) {
      getActionBar().setTitle(getString(R.string.app_name));
    }

    /**
     * Called when a drawer has settled in a completely open state.
     */
    @Override
    public void onDrawerOpened(View view) {
      getActionBar().setTitle(getString(R.string.change_preset));
    }
  }

  private class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    private TextView textValue;

    private SeekBarChangeListener(SeekBarType type) {
      if (SeekBarType.FOCUS == type) {
        textValue = mFocusValue;
      } else if (SeekBarType.ZOOM == type) {
        textValue = mZoomValue;
      }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
      textValue.setText(Integer.toString(progress));
      // TODO: Network request updating focus/zoom
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
  }

  private enum SeekBarType {FOCUS, ZOOM}

}