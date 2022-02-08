package com.example.athletics.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    public static final String PREF_NAME = "AthleticsSession";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ID = "user_id";
    public static final String KEY_MOBILE = "mobile_no";
    public static final String API_TOKEN = "apitoken";
    public static final String FIREBASE_TOKEN = "firebasetoken";
    public static final String KEY_CART_COUNT = "keycartcount";
    public static final String KEY_SEARCH_PRODUCT = "keysearchproduct";


    // Shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }


    public String getUserID() {
        String value = pref.getString(KEY_ID, "");
        return value;
    }

    public void setUserID(String id) {
        editor.putString(KEY_ID, id);
        editor.commit();
    }

    public String getKeySearchProduct() {
        String value = pref.getString(KEY_SEARCH_PRODUCT, "");
        return value;
    }

    public void setKeySearchProduct(String ProductID) {
        editor.putString(KEY_SEARCH_PRODUCT, ProductID);
        editor.commit();
    }


    public String getKeyCartCount() {
        String value = pref.getString(KEY_CART_COUNT, "");
        return value;
    }

    public void setKeyCartCount(String count) {
        editor.putString(KEY_CART_COUNT, count);
        editor.commit();
    }


    public String getKeyUserName() {
        String value = pref.getString(KEY_NAME, "");
        return value;
    }

    public void setKeyUserName(String username) {
        editor.putString(KEY_NAME, username);
        editor.commit();
    }

    public String getKeyEmail() {
        String value = pref.getString(KEY_EMAIL, "");
        return value;
    }

    public void setKeyEmail(String emailid) {
        editor.putString(KEY_EMAIL, emailid);
        editor.commit();
    }

    public String getKeyMobileNo() {
        String value = pref.getString(KEY_MOBILE, "");
        return value;
    }

    public void setKeyMobileNo(String mobile) {
        editor.putString(KEY_MOBILE, mobile);
        editor.commit();
    }


    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

    public String getApiToken() {
        String value = pref.getString(API_TOKEN, "");
        return value;
    }

    public void setApiToken(String token) {
        editor.putString(API_TOKEN, token);
        editor.commit();
    }

    public String getFirebaseToken() {
        String value = pref.getString(FIREBASE_TOKEN, "");
        return value;
    }

    public void setFirebaseToken(String firebaseToken) {
        editor.putString(FIREBASE_TOKEN, firebaseToken);
        editor.commit();
    }


}
