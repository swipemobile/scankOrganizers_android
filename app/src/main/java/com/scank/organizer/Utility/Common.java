package com.scank.organizer.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.scank.organizer.BuildConfig;
import com.scank.organizer.R;
import com.scank.organizer.activity.LoginActivity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.VISIBILITY_PRIVATE;


public class Common {

    @SuppressLint("StaticFieldLeak")
    private static final Common ourInstance = new Common();

    private Context context;

    public static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*?[A-Z])(?=.*?[a-z])(?=.*[!@#$%_+=]).{8,}$";

    public static Common getInstance() {
        return ourInstance;
    }

    private Common() {
    }

    public void init(Context context) {

        this.context = context;

    }

    /**
     * <b>Description: </b> use to check internet newtwork connection if network
     * connection not available than alert for open network
     * settings
     *
     * @param message - toast message
     * @return - true / false
     */
    public final boolean isOnline(boolean message) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
            if (netInfo != null) {
                if (netInfo.isConnectedOrConnecting()) {
                    return true;
                }
            }

            if (message) {
                showToast(context.getString(R.string.internet_not_available));
                return false;
            }
            return false;
        } catch (Exception e) {
            showExceptionLog(e);
            return false;
        }
    }

    /**
     * <b>Description</b> - hide soft keyboard
     *
     * @param context - app context
     * @param view    - ui view
     */
    public final void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    /**
     * <b>Description</b> - hide soft keyboard
     *
     * @param context - context
     * @param view    - view
     */
    public final void showKeyboard(Context context, View view) {

        if (context == null || (context instanceof Activity && ((Activity) context).isFinishing()))
            return;

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * <b>Description: </b> Based on DisplayMetrics on windowManager it calculate total screen width
     *
     * @param activity - activity
     * @return screen total width
     */
    public final int getScreenWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.widthPixels;
    }

    public final int getScreenHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.heightPixels;
    }

    public void debugShowToast(final String MESSAGE) {
        if (BuildConfig.DEBUG && !isStringEmpty(MESSAGE)) {
            Toast.makeText(context, MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    public void showToast(final String MESSAGE) {
        if (!isStringEmpty(MESSAGE)) {
            Toast.makeText(context, MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    public void logI(final String TAG, final String MESSAGE) {
        if (BuildConfig.DEBUG && !isStringEmpty(MESSAGE)) {
            Log.i("app_name " + TAG, MESSAGE);
        }
    }

    public void logW(final String message, final Exception exception) {
        if (BuildConfig.DEBUG && !isStringEmpty(message)) {
            Log.w("app_name ", message, exception);
        }
    }


    public void logD(final String TAG, final String MESSAGE) {
        if (BuildConfig.DEBUG) {
            Log.d("app_name " + TAG, MESSAGE);
        }
    }

    public void logE(final String TAG, final String MESSAGE) {
        if (BuildConfig.DEBUG) {
            Log.e("app_name " + TAG, MESSAGE);
        }
    }

    /**
     * Validate input email address
     *
     * @param emailAddress requested email id
     * @return true if valid else false
     */
    public boolean validateEmail(CharSequence emailAddress) {
        return emailAddress != null && android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches();
    }

    /**
     * Validate input email address
     *
     * @param password requested email id
     * @return true if valid else false
     */
    public boolean validatePassword(final String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * check string is empty or not
     *
     * @param value
     * @return
     */
    public boolean isStringEmpty(String value) {
        return value == null || value.trim().equals("null") || value.trim().isEmpty();
    }

    /**
     * This method use to check is device is table or mobile
     *
     * @param context current context
     * @return boolean true if is tablet or false if not
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * This method capitalize first charachter of word
     *
     * @param string a word in which user want to capital a first letter
     * @return String with capitalized first letter of specified string
     */
    public String setCapitalizeFirstChar(String string) {
        if (string != null && !string.isEmpty() && !TextUtils.equals(string.substring(0, 1), string.substring(0, 1).toUpperCase()))
            string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();

        return string;
    }

    /**
     * Check date is blank or it contains default value like "0000-00-00" or "0000-00-00 00:00:00"
     *
     * @param date - date
     * @return - true / false
     */
    public boolean isDateBlank(String date) {
        return Common.getInstance().isStringEmpty(date) || date.equals("0000-00-00") || date.equals("0000-00-00 00:00:00");
    }


    /**
     * Display the Exception log if App is in Debug mode.
     *
     * @param e
     */
    public void showExceptionLog(Exception e) {
        if (BuildConfig.DEBUG)
            e.printStackTrace();
    }

    public String getFirebaseToken() {
        return PreferencesHelper.getDeviceId(context);
    }

    @SuppressLint("HardwareIds")
    public String getUDID() {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public String getDeviceType() {
        return "Android";
    }

    /**
     * Utility that get application version
     *
     * @return application version
     */
    public String getAppVersion() {
        String versionName = "1.0";

        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            showExceptionLog(e);
            versionName = null;
        }

        return versionName;
    }


    /**
     * This method parse text and remove html tag from specified text
     *
     * @param source - source
     * @return - spanned veriable
     */
    @SuppressWarnings("deprecation")
    public Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    /**
     * @param str - str
     * @param c   - character
     * @return String
     */
    public final String removeLastCharacter(String str, char c) {
        if (str.length() > 0 && str.charAt(str.length() - 1) == c) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * Check application have specified permission
     *
     * @param permission - permission
     * @return - true / false
     */
    public boolean isPermissionAvailable(final String permission) {
        return (ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED);
    }

    /**
     * @param grantResults granted permission status array
     * @return true if all permission granted otherwise false
     */
    public boolean isAllPermissionGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == -1)
                return false;
        }
        return true;
    }


    /**
     * get date by required format
     *
     * @param date   - date data
     * @param format - requited format
     * @return - date value
     */
    public String getDateDisplayValue(String date, String format) {
        return getFormatedDatebyValue(date, format);
    }

    /**
     * @param date   - date
     * @param format - format
     * @return - date value
     */
    private String getFormatedDatebyValue(String date, String format) {

        if (!TextUtils.isEmpty(date)) {
            return getDateFormate(date, Constants.DATE_FORMAT.DATE_YMD_TIME_HMS_DEFAULT, format);
        }
        return "";
    }

    /**
     * @param dateOfBirth - date value
     * @param from        - from format
     * @param to          - to format
     * @return - converted value
     */
    public static String getDateFormate(String dateOfBirth, String from, String to) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(from, Locale.UK);
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(String.valueOf(dateOfBirth));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat targetFormat = new SimpleDateFormat(to, Locale.UK);
        assert sourceDate != null;
        return targetFormat.format(sourceDate);
    }

    /**
     * @return Current timestamp in second
     * @Description getCurrentTimestamp method use to get current date UNIX timestamp
     */
    public final long getCurrentTimestamp() {
        try {
            return System.currentTimeMillis() / 1000L;
        } catch (Exception e) {
            showExceptionLog(e);
            return 0;
        }
    }

    /**
     * Make comma separated string from arraylist
     */
    public String convertListToCommaSeparatedString(List<?> arrayList) {
        return arrayList.toString().replace("[", "").replace("]", "").replace(", ", ",");
    }

    /**
     * Make comma separated string from arraylist
     */
    public String convertIntegerListToCommaSeparated(List<Integer> arrayList) {
        return arrayList.toString().replace("[", "").replace("]", "").replace(", ", ",");
    }


    /**
     * create target location folder if not exist
     *
     * @param path - path
     */
    public void dirChecker(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * @param email
     * @return true/false - is email valid or not
     */
    public boolean isValidEmail(CharSequence email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public void logOut(Activity mActivity) {
        try {
            NotificationManager nmg = (NotificationManager) context.getApplicationContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            if (nmg != null)
                nmg.cancelAll();

            boolean isIntroPage = PreferencesHelper.isIntroPage(mActivity);

            PreferencesHelper.clearAllPref(mActivity);
            PreferencesHelper.setIntroPage(mActivity, isIntroPage);
            ApplicationClass.getInstance().resetUserData();

            Intent intent = new Intent(mActivity, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            mActivity.finish();
        } catch (Exception e) {

        }

    }

    public void showViewWithFirstText(String name, TextView tvFirstLetter) {
        if (!TextUtils.isEmpty(name)) {
            char firstLetter = name.charAt(0);
            tvFirstLetter.setText(String.valueOf(firstLetter).toUpperCase());
        }
    }

    public void setFirebaseToken(Context context, String token) {
        PreferencesHelper.saveDeviceId(context, token);
    }

    /**
     * create local notification
     *
     * @param mActivity - activity
     * @param message   - message
     */
    public void createNotification(Activity mActivity, String message) {

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(mActivity.getApplicationContext(), mActivity.getString(R.string.channel_id))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("nhs")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message));

        mBuilder.setDefaults(DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) mActivity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = mActivity.getString(R.string.channel_name);
            //int importance = NotificationManager.IMPORTANCE_HIGH;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(mActivity.getString(R.string.channel_id), name, importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            channel.setLockscreenVisibility(VISIBILITY_PRIVATE);
            //NotificationManager notificationManager = getSystemService(NotificationManager.class);
            mNotificationManager.createNotificationChannel(channel);
        }
        mNotificationManager.notify(1, mBuilder.build());
    }


    /**
     * use for sendEmail via default mail app
     *
     * @param activity    - activity
     * @param email       - email address
     * @param subject     - purpose of mail
     * @param extraDetail - content of mail
     */
    public void sendEmail(Activity activity, String email, String subject, String extraDetail) {

        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, extraDetail);

        final PackageManager pm = activity.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        activity.startActivityForResult(intent, 1);
    }

    public void responseErrorToast(int code, Activity mActivity, List<String> Message) {

        if (Message != null && Message.size() > 0) {

            if (isOnline(false)) {
                if (code == 401) {
                    String msj = "";
                    for (String m : Message) {
                        msj += m + "\n";
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setMessage(msj)
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, id) -> logOut(mActivity));
                    AlertDialog alert = builder.create();
                    alert.show();

                } else {
                    showErrorMessage(Message);
                }
            } else {
                showToast(context.getString(R.string.internet_error_when_api));
            }

        } else {
            switch (code) {
                case 0:
                    if (isOnline(false)) {
                        showToast(mActivity.getString(R.string.something_went_wrong));
                    } else {
                        showToast(context.getString(R.string.internet_error_when_api));
                    }
                    break;
                case 400:
                    showToast("Invalid request.");
                    break;
                case 404:
                    showToast("Not Found");
                    break;
                case 500:
                    showToast("Internal Server Error");
                    break;
                case 401:
                    showToast("The user is not logged in or their token has expired.");
                    logOut(mActivity);
                    break;
                case 403:
                    showToast("The user is not authorized to access this endpoint or resource.\n");
                    break;
                default:
                    if (isOnline(false)) {
                        showToast("Unknown Error");
                    } else {
                        showToast(context.getString(R.string.internet_error_when_api));
                    }

                    break;
            }
        }
    }

    public boolean hasPermissions(Context context, ArrayList<String> permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void showErrorMessage(List<String> Message) {
        String msj = "";
        if (Message != null) {
            for (String m : Message) {
                msj += m + "\n";
            }
        } else msj = "Error";

        showToast(msj);
    }

    public void setMailForFeedBack(Activity activity) {
        // Device model
        String PhoneModel = android.os.Build.MODEL;
        // Android version
        String AndroidVersion = android.os.Build.VERSION.RELEASE;
        PackageInfo packageInfo = null;
        try {
            packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = packageInfo.versionName;
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:help@scank.com"));
        intent.putExtra("subject", "Help");
        intent.putExtra(Intent.EXTRA_TEXT, "\n\n\n\n\n\n\n\n-------------------------------" +
                "\nPlatform: Android" +
                "\nPhone Model: " + PhoneModel +
                "\nAndroid Version: " + AndroidVersion +
                "\nApp Version: " + versionName);
        //intent.putExtra("body", " "+icerik);
        activity.startActivity(intent);

    }

}
