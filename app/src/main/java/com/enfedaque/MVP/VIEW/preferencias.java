package com.enfedaque.MVP.VIEW;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.enfedaque.R;

public class preferencias extends PreferenceActivity {


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);

    }

}
