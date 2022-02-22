package com.scank.organizer.Utility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * for checking the App is in Background or not
 */
public class BackgroundManager implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "BackgroundManager";

    public static final long BACKGROUND_DELAY = 500;

    private static BackgroundManager sInstance;

    private Context context = null;

    public interface Listener {
        void onBecameForeground();
        void onBecameBackground();
    }

    private boolean mInBackground = true;
    private final List<Listener> listeners = new ArrayList<Listener>();
    private final Handler mBackgroundDelayHandler = new Handler();
    private Runnable mBackgroundTransition;

    public static BackgroundManager getInstance() {
        if (sInstance == null) {
            sInstance = new BackgroundManager(ApplicationClass.getInstance());
        }
        return sInstance;
    }

    public void init(Context context) {
        this.context = context;
    }

    private BackgroundManager(Application application) {
        application.registerActivityLifecycleCallbacks(this);
    }

    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        listeners.remove(listener);
    }

    public boolean isInBackground() {
        return mInBackground;
    }

    private Context previousContext = null;

    public Context getPreviousContext() {
        return previousContext;
    }

    public void setPreviousContext(Context previousContext) {
        this.previousContext = previousContext;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (mBackgroundTransition != null) {
            mBackgroundDelayHandler.removeCallbacks(mBackgroundTransition);
            mBackgroundTransition = null;
        }

        if (mInBackground) {
            mInBackground = false;
            notifyOnBecameForeground();

            //        LOG.info("Application went to foreground");
        }
    }

    private void notifyOnBecameForeground() {

        for (Listener listener : listeners) {
            try {
                listener.onBecameForeground();
            } catch (Exception e) {
                Common.getInstance().showExceptionLog(e);
            }
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (!mInBackground && mBackgroundTransition == null) {
            mBackgroundTransition = () -> {
                mInBackground = true;
                mBackgroundTransition = null;
                notifyOnBecameBackground();
            };

            mBackgroundDelayHandler.postDelayed(mBackgroundTransition, BACKGROUND_DELAY);
        }
    }

    private void notifyOnBecameBackground() {
        for (Listener listener : listeners) {
            try {
                listener.onBecameBackground();
            } catch (Exception e) {
//                LOG.error("Listener threw exception!", e);
            }
        }

    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {
        // Set the application context when activity start

        ApplicationClass.getInstance().setCurrentActivity(activity);

      //  AppMethods.clearAllNotifications(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {
        Common.getInstance().logI(TAG, "Destroy activity: " + activity.getLocalClassName());

       /* if(activity instanceof VideoCallActivity || activity instanceof AudioCallActivity) {
            Common.getInstance().logI(TAG, "Finish Call");
        }*/
    }
}
