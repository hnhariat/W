package com.iey.w;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 1100160 on 2016. 10. 25..
 */

public class PreferenceManager {
    public static PreferenceManager sInstance;
    private Context context;

    public static synchronized PreferenceManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (PreferenceManager.class) {
                sInstance = new PreferenceManager();
            }
        }
        sInstance.setContext(context);
        return sInstance;
    }

    public void putString(String key, String value) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        pref.edit().putString(key, value).commit();
    }

    public void putStringSet(String key, Set<String> set) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        pref.edit().putStringSet(key, set).commit();

    }

    public void putInt(String key, int value) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        pref.edit().putInt(key, value).commit();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        pref.edit().putBoolean(key, value).commit();
    }

    public void putLong(String key, long value) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        pref.edit().putLong(key, value).commit();
    }


    public String getString(String key) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public String getString(String key, String value) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        return pref.getString(key, value);
    }


    public HashSet<String> getStringSet(String key) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        return (HashSet<String>) pref.getStringSet(key, null);
    }

    public int getInt(String key) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        return pref.getInt(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        return pref.getBoolean(key, defaultValue);
    }

    public long getLong(String key) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        return pref.getLong(key, 0L);
    }

    public long getLong(String key, int defaultValue) {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        return pref.getLong(key, defaultValue);
    }

    public void clearAll() {
        SharedPreferences pref = context.getSharedPreferences("w", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().commit();
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
