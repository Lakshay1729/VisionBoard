package com.heartmeetsoul.visionboard;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class App extends Application {


        public static final String TAG = "App";
        private boolean isNightModeEnabled = false;
        @Override
        public void onCreate() {
            super.onCreate();
// We load the Night Mode state here
            SharedPreferences mPrefs =  PreferenceManager.getDefaultSharedPreferences(this);
            this.isNightModeEnabled = mPrefs.getBoolean("NIGHT_MODE", false);
        }
        public boolean isNightModeEnabled() {
            return isNightModeEnabled;
        }
        public void setIsNightModeEnabled(boolean isNightModeEnabled) {
            this.isNightModeEnabled = isNightModeEnabled;
        }
    }

