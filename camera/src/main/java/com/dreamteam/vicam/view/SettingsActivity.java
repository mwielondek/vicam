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
    return SettingsFragment.class.getName().equals(fragmentName)
           // || MyPreferenceFragmentB.class.getName().equals(fragmentName)
        ;
  }

  @Override
  public void onBuildHeaders(List<Header> target) {
    loadHeadersFromResource(R.xml.preference_headers, target);
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class SettingsFragment extends PreferenceFragment {

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // Load the preferences from an XML resource
      addPreferencesFromResource(R.xml.preferences);
    }
  }
}
