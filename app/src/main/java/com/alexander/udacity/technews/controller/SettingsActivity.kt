package com.alexander.udacity.technews.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alexander.udacity.technews.R
import kotlinx.android.synthetic.main.activity_settings.toolbar_settings

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}
