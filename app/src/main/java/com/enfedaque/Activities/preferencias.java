package com.enfedaque.Activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import androidx.preference.PreferenceManager;

import com.enfedaque.R;

public class preferencias extends PreferenceActivity {


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);

    }



}
