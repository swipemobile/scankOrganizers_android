package com.scank.organizer.Utility;

import android.app.Activity;
import android.app.Application;

import com.scank.organizer.R;
import com.scank.organizer.model.Details;
import com.scank.organizer.model.LoginResponse;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.Cache;

/**
 * This class is a global, it contains many information which application require
 * through out the application, It contains following information
 * <ul>
 * <li>Current running activity reference</li>
 * <li>Create Retrofilt client</li>
 * </ul>
 *
 * @see Application
 */
public class ApplicationClass extends Application {

    private static final String TAG = "ApplicationClass";

    private static ApplicationClass sInstance;

    private Activity currentClass = null;

    private Common common;

    private boolean isPlay;

    public static final int DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

    private LoginResponse loginResult;
    private boolean locationEnabled = true;


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        common = Common.getInstance();
        common.init(this);

        Realm.init(this);


        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(getString(R.string.db_name))
                .deleteRealmIfMigrationNeeded()
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.setDefaultConfiguration(config);

        BackgroundManager backgroundManager = BackgroundManager.getInstance();
        backgroundManager.init(this);

    }

    /**
     * @return ApplicationClass singleton instance
     */
    public static ApplicationClass getInstance() {
        return sInstance;
    }


    public Cache getCache() {
        File cacheDir = new File(getCacheDir(), "cache");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        return cache;
    }

    public boolean isInternetAvailable() {
        return Common.getInstance().isOnline(false);
    }

    /**
     * This method use for identify currently which activity in user focus
     *
     * @param currentClass
     */
    public void setCurrentActivity(Activity currentClass) {
        this.currentClass = currentClass;
    }


    /**
     * This method return activity reference which activity is on user front
     *
     * @return
     */
    public Activity getCurrentClass() {
        return currentClass;
    }


    public void updateLoginData() {
        loginResult = PreferencesHelper.getLoginData(this);
    }

    public void setLoginResult(LoginResponse loginResult) {
        PreferencesHelper.setLoginResult(this, loginResult);
    }

    public LoginResponse getLoginResult() {
        if (loginResult == null) {
            updateLoginData();
        }

        return loginResult;
    }

    public boolean isUserLogin() {
        getLoginResult();
        return loginResult != null;
    }

    public void resetUserData() {
        loginResult = null;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public boolean isLocationEnabled() {
        return locationEnabled;
    }

    public void setLocationEnabled(boolean locationEnabled) {
        this.locationEnabled = locationEnabled;
    }
}
