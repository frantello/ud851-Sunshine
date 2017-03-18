package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Settings fragment
 */

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);

        int count = getPreferenceScreen().getPreferenceCount();

        for (int i = 0; i < count; i++) {

            Preference preference = getPreferenceScreen().getPreference(i);
            setPreferenceSummary(preference, getPreferenceManager().getSharedPreferences()
                    .getString(preference.getKey(), ""));
        }
    }

    private void setPreferenceSummary(Preference preference, Object value) {
        if (!(preference instanceof CheckBoxPreference)) {
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                preference.setSummary(listPreference.getEntry());
            } else {
                preference.setSummary(value.toString());
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preference = findPreference(key);
        setPreferenceSummary(preference, sharedPreferences.getString(key, ""));
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
