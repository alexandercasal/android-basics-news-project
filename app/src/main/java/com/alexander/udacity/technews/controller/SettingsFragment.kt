package com.alexander.udacity.technews.controller

import android.os.Bundle
import android.preference.PreferenceFragment
import com.alexander.udacity.technews.R

class SettingsFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings_main)

        val sortByPreference = findPreference(getString(R.string.settings_sort_by_key))
    }
}