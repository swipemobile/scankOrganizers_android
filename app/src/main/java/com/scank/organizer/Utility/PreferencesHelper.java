package com.scank.organizer.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.scank.organizer.R;
import com.scank.organizer.model.Details;
import com.scank.organizer.model.LoginResponse;

import java.util.Calendar;

/**
 * Preference Getter/Setter class to save/retrive data into/from temporary storage
 */

public class PreferencesHelper {
    private static SharedPreferences mPrefs;
    private static final String Token_PREFERENCE = "Token_PREFERENCE";
    private static final String PREF_KEY_TOKEN = "PREF_KEY_TOKEN";
    private static final String USER_PREFERENCE = "USER_PREFERENCE";
    private static final String PREF_KEY_USER_ID = "PREF_KEY_USER_ID";
    private static final String PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME";
    private static final String PREF_KEY_USER_PASSWORD = "PREF_KEY_USER_PASSWORD";
    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
    private static final String PREF_KEY_INTRO = "PREF_KEY_INTRO";

    private static final String PREF_KEY_USER_URL = "PREF_KEY_USER_URL";
    private static final String PREF_KEY_USER_OFFICE_ID = "PREF_KEY_USER_OFFICE_ID";
    private static final String PREF_KEY_TIME_INTERVAL = "PREF_KEY_TIME_INTERVAL";
    private static final String USER_LOGIN_DATA = "user_login_data";
    private static final String LOCATION_DATA = "location_data";

    private static final String PREF_KEY_CALL_POPUP = "call_popup";
    private static final String COUNTER = "counter";
    private static final String CURRENT_DATE = "current_date";
    private static final String LOCATION_MESSAGE = "location_message";

    private static final String AUTO_START = "auto_start";

    /**
     * @param context
     * @return true/false - is user logged in or not
     */
    public static boolean isLoggedIn(Context context) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        return mPrefs.getBoolean(PREF_KEY_USER_LOGGED_IN_MODE, false);
    }

    /**
     * @param context
     * @return true/false - is user logged in or not
     */
    public static boolean isIntroPage(Context context) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        return mPrefs.getBoolean(PREF_KEY_INTRO, false);
    }


    /**
     * @param context
     * @return true/false - is user logged in or not
     */
    public static void setIntroPage(Context context, boolean isIntro) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(PREF_KEY_INTRO, isIntro);
        editor.apply();
    }

    /** save user details
     * @param context
     * @param userid
     * @param username
     * @param password
     * @param url
     */
    // save login status of user
    public static void setUser(Context context, int userid, String username, String password, String url) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(PREF_KEY_USER_LOGGED_IN_MODE, true);
        editor.putInt(PREF_KEY_USER_ID, userid);
        editor.putString(PREF_KEY_USER_NAME, username);
        editor.putString(PREF_KEY_USER_PASSWORD, password);
        editor.putString(PREF_KEY_USER_URL, url);
        editor.apply();
    }


    /**
     * @param context
     * @return saved userid
     */
    public static int getUserId(Context context) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        return mPrefs.getInt(PREF_KEY_USER_ID, 0);
    }


    /**
     * @param context
     * @return saved username
     */
    public static String getUsername(Context context) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        return mPrefs.getString(PREF_KEY_USER_NAME, "");
    }

    /**
     * @param context
     * @return saved isCallPopupOnOff
     */
    public static boolean isCallPopupOnOff(Context context) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        return mPrefs.getBoolean(PREF_KEY_CALL_POPUP, true);
    }

    /** save call popup show
     * @param context
     * @param isCallPopupOn
     */
    public static void setCallPopupOnOff(Context context, boolean isCallPopupOn) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(PREF_KEY_CALL_POPUP, isCallPopupOn);
        editor.apply();
    }


    /**
     * @param context
     * @return saved password
     */
    public static String getPassword(Context context) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        return mPrefs.getString(PREF_KEY_USER_PASSWORD, "");
    }

    /** clear saved data
     * @param context
     */
    static void clearPref(Context context) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * @param context
     * @return saved url
     */
    public static String getUserUrl(Context context) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        return mPrefs.getString(PREF_KEY_USER_URL, "");
    }

    /**
     * @param context
     * @return saved officeid
     */
    static String getOfficeId(Context context) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        return mPrefs.getString(PREF_KEY_USER_OFFICE_ID, "");
    }

    /** save deviceid
     * @param context
     * @param deviceid
     */
    public static void saveDeviceId(Context context, String deviceid) {
        mPrefs = context.getSharedPreferences(Token_PREFERENCE, 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_KEY_TOKEN, deviceid);
        editor.apply();
    }


    /**
     * @param context
     * @return saved device id
     */
    public static String getDeviceId(Context context) {
        mPrefs = context.getSharedPreferences(Token_PREFERENCE, 0);
        return mPrefs.getString(PREF_KEY_TOKEN, "");
    }


    /** save time interval for location api call
     * @param context
     * @param interval
     */
    public static void setTimeInterval(Context context, long interval) {
        mPrefs = context.getSharedPreferences(Token_PREFERENCE, 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putLong(PREF_KEY_TIME_INTERVAL, interval);
        editor.apply();
    }

    /**
     * @param context
     * @return saved time interval for location api call
     */
    public static long getTimeInterval(Context context) {
        mPrefs = context.getSharedPreferences(Token_PREFERENCE, 0);
        return mPrefs.getLong(PREF_KEY_TIME_INTERVAL, 600);
    }


    public static void clearAllPref(Context context) {
        String fcmToken = PreferencesHelper.getDeviceId((context));

        SharedPreferences prefs = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        PreferencesHelper.saveDeviceId(context, fcmToken);
        ApplicationClass.getInstance().updateLoginData();

    }

    /**
     * Set session data
     *
     * @param context
     * @param loginResult class object
     */
    public static void setLoginResult(Context context, LoginResponse loginResult) {
        SharedPreferences sp = context.getSharedPreferences(USER_PREFERENCE, 0);
        Gson gson = new Gson();
        String userDetails = gson.toJson(loginResult);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_LOGIN_DATA, userDetails);
        editor.apply();

        ApplicationClass.getInstance().updateLoginData();
    }

    /**
     * Get session obj
     *
     * @param context
     * @return object of Session
     */
    public static LoginResponse getLoginData(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_PREFERENCE, 0);
        String sessionInfo = sp.getString(USER_LOGIN_DATA, null);

        if (sessionInfo != null) {
            Gson gson = new Gson();
            return gson.fromJson(sessionInfo, LoginResponse.class);
        }

        return null;
    }


    /**
     * Get session obj
     *
     * @param context
     * @return object of Session
     */
    public static void setLocationCounter(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_PREFERENCE, 0);

        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH);

        int counter = 1;
        if(currentDay == getCurrentDate(context)) {
           counter = getLocationCounter(context) + 1;
        } else {
            setCurrentDate(context, currentDay);
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(COUNTER, counter);
        editor.apply();
    }

    /**
     * Get session obj
     *
     * @param context
     * @return object of Session
     */
    public static int getLocationCounter(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_PREFERENCE, 0);
        return sp.getInt(COUNTER, 1);
    }


    /**
     * Get session obj
     *
     * @param context
     * @return object of Session
     */
    public static void setCurrentDate(Context context, int day) {
        SharedPreferences sp = context.getSharedPreferences(USER_PREFERENCE, 0);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(CURRENT_DATE, day);
        editor.apply();
    }

    /**
     * Get session obj
     *
     * @param context
     * @return object of Session
     */
    public static int getCurrentDate(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_PREFERENCE, 0);
        return sp.getInt(CURRENT_DATE, 0);
    }


    /**
     * @param context
     * @return saved username
     */
    public static String getLocationMessage(Context context) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        return mPrefs.getString(LOCATION_MESSAGE, ApplicationClass.getInstance().getResources().getString(R.string.not_able_to_get_location));
    }

    /**
     * @param context
     * @return saved username
     */
    public static void setLocationMessage(Context context, String message) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(LOCATION_MESSAGE, message);
        editor.apply();
    }

    public static boolean isAutoRunEnabled(Context context) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        return mPrefs.getBoolean(AUTO_START, false);
    }

    public static void saveAutoRunStatus(Context context, boolean autoRunStatus) {
        mPrefs = context.getSharedPreferences(USER_PREFERENCE, 0);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(AUTO_START, autoRunStatus);
        editor.apply();
    }
}
