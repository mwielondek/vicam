package com.dreamteam.vicam.view;

import android.app.ActionBar;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.dreamteam.camera.R;
import com.dreamteam.vicam.model.pojo.Camera;

public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

  private String[] mPlanetTitles;
  private DrawerLayout mDrawerLayout;
  private ListView mDrawerList;
  private CharSequence mTitle;
  private ActionBarDrawerToggle mDrawerToggle;
  private MenuItem mSpinnerItem;
  private ArrayAdapter changeCameraAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // Sets default values defined in camera_preferences if empty
    PreferenceManager.setDefaultValues(this, R.xml.camera_preferences, false);
    // Get set camera_preferences
    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

    mTitle = getString(R.string.app_name);

    mPlanetTitles = new String[]{"Preset 1", "Preset 2", "Preset 3"};

    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerList = (ListView) findViewById(R.id.left_drawer);

    // Set the changeCameraAdapter for the list view
    mDrawerList.setAdapter(
        new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles));
    // Set the list's click listener
    mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    mDrawerToggle = new ActionBarDrawerToggle(
        this, /* host Activity */
        mDrawerLayout, /* DrawerLayout object */
        R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
        R.string.drawer_open, /* "open drawer" description */
        R.string.drawer_close /* "close drawer" description */
    ) {
      /**
       * Called when a drawer has settled in a completely closed state.
       */
      @Override
      public void onDrawerClosed(View view) {
        // Toast.makeText(this, R.string.auto_focus, Toast.LENGTH_SHORT).show();
        getActionBar().setTitle(getString(R.string.change_preset));
      }

      /**
       * Called when a drawer has settled in a completely open state.
       */
      @Override
      public void onDrawerOpened(View drawerView) {
        getActionBar().setTitle(getString(R.string.auto_focus));
      }
    };

    // Set the drawer toggle as the DrawerListener
    mDrawerLayout.setDrawerListener(mDrawerToggle);

    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);

    // Hide the action bar title
    getActionBar().setDisplayShowTitleEnabled(true);

    changeCameraAdapter =
        new ArrayAdapter<Camera>(this, android.R.layout.simple_expandable_list_item_1);
    changeCameraAdapter.add(new Camera("127.0.0.1", "Test :3", null));
    changeCameraAdapter.add(new Camera("localhost", "Test2 >:3", null));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.

    getMenuInflater().inflate(R.menu.main, menu);

    mSpinnerItem = menu.findItem(R.id.action_change_camera);
    View spinnerView = mSpinnerItem.getActionView();
    if (spinnerView instanceof Spinner) {
      final Spinner spinner = (Spinner) spinnerView;
      spinner.setAdapter(changeCameraAdapter);

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
    // getActionBar().setDisplayShowTitleEnabled(false);

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

  /**
   * Swaps fragments in the main ontent view
   */
  private void selectItem(int position) {
    Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT).show();

    // Highlight the selected item, update the title, and close the drawer
    mDrawerList.setItemChecked(position, true);
    setTitle(mPlanetTitles[position]);
    mDrawerLayout.closeDrawer(mDrawerList);
  }

  @Override
  public void setTitle(CharSequence title) {
    mTitle = title;
    getActionBar().setTitle(mTitle);
  }

  private class DrawerItemClickListener implements ListView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
      selectItem(position);
    }
  }

  /**
   * Actionbar navigation item select listener
   */
  @Override
  public boolean onNavigationItemSelected(int itemPosition, long itemId) {
    // Action to be taken after selecting a spinner item
    return false;
  }

}