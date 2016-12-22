package com.theah64.soundclouddownloader.ui.activities.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.theah64.musicdog.R;

import static com.theah64.soundclouddownloader.ui.activities.settings.SettingsActivity.SettingsFragment.KEY_STORAGE_LOCATION;

/**
 * This class used as a compatible version of SettingsActivity
 * Created by theapache64 on 29/2/16.
 */
@SuppressWarnings("deprecation")
public class SettingsActivityCompat extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    public static final String X = SettingsActivityCompat.class.getSimpleName();
    private SharedPreferences defaultSharedPref;
    private AppCompatDelegate mDelegate;


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    private void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_2);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addPreferencesFromResource(R.xml.settings_screen);
        this.defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    }


    @Override
    public void onResume() {
        super.onResume();

        //Log.d(X, "registering listener");
        defaultSharedPref.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, final String key) {

    }

    //Clicked on preference item.
    @Override
    public boolean onPreferenceClick(Preference preference) {

        switch (preference.getKey()) {
            case KEY_STORAGE_LOCATION:
                final Intent storageIntent = new Intent(Intent.ACTION_VIEW);
                storageIntent.setDataAndType(Uri.parse(preference.getSummary().toString()), "resource/folder");
                if (storageIntent.resolveActivityInfo(getPackageManager(), 0) != null) {
                    startActivity(storageIntent);
                } else {
                    Toast.makeText(this, R.string.No_file_browser_found, Toast.LENGTH_SHORT).show();
                }
                return true;
        }

        return false;
    }

}

