package com.example.athletics.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ExtraPrefrence {
    public static final String PREF_NAME = "AthleticsSharePrefrence";
    public static final String LANGUAGE = "language";
    public static final String KEY_APP_FIRST = "keyappfirst";

    // Shared Preferences
    SharedPreferences SharePref;
    Editor SharePrefeditor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public ExtraPrefrence(Context context) {
        this._context = context;
        SharePref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharePrefeditor = SharePref.edit();
        SharePrefeditor.commit();
    }



    public String getLanguage() {
        return SharePref.getString(LANGUAGE, "English");
    }

    public void setLanguage(String language) {
        SharePrefeditor.putString(LANGUAGE, language);
        SharePrefeditor.commit();
    }

    public String getKeyAppFirst() {
        return SharePref.getString(KEY_APP_FIRST, "");
    }

    public void setKeyAppFirst(String appFirst) {
        SharePrefeditor.putString(KEY_APP_FIRST, appFirst);
        SharePrefeditor.commit();
    }


}
