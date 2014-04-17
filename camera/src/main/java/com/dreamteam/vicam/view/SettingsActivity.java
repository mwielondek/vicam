package com.dreamteam.vicam.view;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.dreamteam.camera.R;

import java.util.List;

public class SettingsActivity extends PreferenceActivity {

  @Override
  public boolean onIsMultiPane() {
    return true;
  }

  @Override
  protected boolean isValidFragment(String fragmentName) {
    return CameraFragment.class.getName().equals(fragmentName)
           || SyncFragment.class.getName().equals(fragmentName);
  }

  @Override
  public void onBuildHeaders(List<Header> target) {
    loadHeadersFromResource(R.xml.preference_headers, target);
  }

  public static class CameraFragment extends PreferenceFragment {

    public CameraFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Load the camera_preferences from an XML resource
      addPreferencesFromResource(R.xml.camera_preferences);
    }
  }

  public static class SyncFragment extends PreferenceFragment {

    public SyncFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Load the camera_preferences from an XML resource
      addPreferencesFromResource(R.xml.sync_preferences);
    }
  }
}
