package com.alexander.udacity.technews.controller

import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import com.alexander.udacity.technews.R

class SettingsFragment : PreferenceFragment(), Preference.OnPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings_main)

        val sortByPreference = findPreference(getString(R.string.settings_sort_by_key))
        bindPreferenceSummaryToValue(sortByPreference)
    }

    override fun onPreferenceChange(preference: Preference, value: Any): Boolean {
        val stringValue = value.toString()

        if (preference is ListPreference) {
            val listPref = preference as ListPreference
            val prefIndex = listPref.findIndexOfValue(stringValue)
            if (prefIndex >= 0) {
                val labels = listPref.entries
                preference.setSummary(labels[prefIndex])
            }
        } else {
            preference.setSummary(stringValue)
        }

        return true
    }

    private fun bindPreferenceSummaryToValue(preference: Preference) {
        preference.setOnPreferenceChangeListener(this)
        val preferences = PreferenceManager.getDefaultSharedPreferences(preference.context)
        val preferenceString = preferences.getString(preference.key, "")
        onPreferenceChange(preference, preferenceString)
    }
}