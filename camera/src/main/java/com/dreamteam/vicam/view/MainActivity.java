package com.dreamteam.vicam.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.pojo.Camera;
import com.dreamteam.vicam.model.pojo.Preset;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity {

  private CharSequence mTitle;
  private Preset[] mPresets;

  private ActionBarDrawerToggle mDrawerToggle;
  private ArrayAdapter<Camera> mCameraAdapter;
  private ArrayAdapter<Preset> mPresetAdapter;

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
    ButterKnife.inject(this);
    // Sets default values defined in camera_preferences if empty
    PreferenceManager.setDefaultValues(this, R.xml.camera_preferences, false);
    // Get set camera_preferences
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

    mTitle = getString(R.string.app_name);

    // Temporary dummy data
    mPresets = new Preset[]{
        new Preset("Preset 1", null), new Preset("Preset 2", null),
        new Preset("Preset 3", null)};

    mPresetAdapter = new ArrayAdapter<Preset>(this, R.layout.drawer_list_item, mPresets);
    mDrawerList.setAdapter(mPresetAdapter);
    mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    mDrawerToggle = new DrawerToggle(this, mDrawerLayout);
    mDrawerLayout.setDrawerListener(mDrawerToggle);

    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);

    // Hide the action bar title
    getActionBar().setDisplayShowTitleEnabled(true);

    mCameraAdapter = new ArrayAdapter<Camera>(this, R.layout.change_camera_spinner);
    mCameraAdapter.add(new Camera("127.0.0.1", "Test :3", null));
    mCameraAdapter.add(new Camera("localhost", "Test2 >:3", null));

    mFocusSeekBar.setOnSeekBarChangeListener(new SeekBarChangeListener(mFocusValue));
    mZoomSeekBar.setOnSeekBarChangeListener(new SeekBarChangeListener(mZoomValue));
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
        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int arg2, long arg3) {
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
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
    // Handle your other action bar items...
    switch (item.getItemId()) {
      case R.id.action_settings:
        startActivity(new Intent(this, SettingsActivity.class));
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

  /**
   * Swaps fragments in the main content view
   */
  private void selectItem(int position) {

    // Shows a toast of the selected preset in main content view
    Toast.makeText(this, mPresets[position].toString(), Toast.LENGTH_SHORT).show();

    // Closes the drawer
    mDrawerLayout.closeDrawer(mDrawerList);
  }

  private class DrawerItemClickListener implements ListView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
      selectItem(position);
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

    private final TextView mTextValue;

    private SeekBarChangeListener(TextView mTextValue) {
      this.mTextValue = mTextValue;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
      mTextValue.setText(Integer.toString(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
  }

}