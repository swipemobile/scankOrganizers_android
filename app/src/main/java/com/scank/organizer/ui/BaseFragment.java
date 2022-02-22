package com.scank.organizer.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Type;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created on 29.08.2020.
 */

public abstract class BaseFragment extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences preferencesTicket;
    SharedPreferences.Editor editorTicket;
    boolean drawer_lock_state = true;

    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("userFile", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        preferencesTicket = getActivity().getSharedPreferences("ticket", MODE_PRIVATE);
        editorTicket = preferencesTicket.edit();
    }

   /* public void openLoginScreen() {
        Intent i = new Intent(getActivity(), LoginRegisterControlActivity.class);
        startActivityForResult(i, RETURN_LOGIN);
    }*/

    public static boolean isImmersiveAvailable() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public void setFullscreen(Activity activity) {
        /*
        if (Build.VERSION.SDK_INT > 10) {
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;

            if (isImmersiveAvailable()) {
                flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }

            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        } else {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        */
    }

    public String getUserEmail() {
        return sharedPreferences.getString("userEmail", null);
    }

    public String getUserPassword() {
        return sharedPreferences.getString("userPassword", null);
    }

    public String getUserId() {
        return sharedPreferences.getString("userId", null);
    }

    public String getUserName() {
        return sharedPreferences.getString("userName", null);
    }

    public boolean getWalkthrough() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        return sharedPreferences.getBoolean("walkthrough", false);
    }

    public boolean getToolTips() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        return sharedPreferences.getBoolean("tooltips", false);
    }

    public String getProfilePic() {
        return sharedPreferences.getString("profilPicture", null);
    }

    public String getTicketCount() {
        return sharedPreferences.getString("ticketCount", null);
    }

    public String getSavedCount() {
        int deger = 0;
        try {
            deger = Integer.parseInt(sharedPreferences.getString("savedCount", null));
        } catch (Exception e) {
        }
        if (deger >= 0)
            return sharedPreferences.getString("savedCount", null);
        else return "0";
    }

    public String getCityId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        return sharedPreferences.getString("cityId", null);
    }

    public String getCityName() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        return sharedPreferences.getString("cityName", null);
    }

    //Hastag
    public String getHashtagId(int i) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        return sharedPreferences.getString("hastagId" + i, null);
    }

    public void setHashtagId(String hastagId, int i) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        sharedPreferences.edit().putString("hastagId" + i, hastagId).apply();
    }



    public void setTempFilter(String json) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        sharedPreferences.edit().putString("setTempFilter", json).apply();
    }

    public void setTempNavgHashtagList(String json) {
        try {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
            sharedPreferences.edit().putString("setTempHashtagList", json).apply();
        } catch (Exception e) {
        }
    }


    public String getNotificationState() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        return sharedPreferences.getString("NotificationState", "false");
    }

    public void setNotificationState(String notf) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        sharedPreferences.edit().putString("NotificationState", notf).apply();
    }

    public void setCalendar(String eventId, boolean state) {
        SharedPreferences calPref = getActivity().getSharedPreferences("calenderPref", MODE_PRIVATE);
        calPref.edit().putBoolean("cal_" + 1, state);
    }

    public boolean getCalendar(String eventId) {
        return getActivity().getSharedPreferences("calenderPref", MODE_PRIVATE).getBoolean("cal_" + eventId, false);
    }


    public void setLinkToBitmap(String url, Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            //Gson gson=new Gson();
            //String json=gson.toJson(encoded);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("bitmapNavg", MODE_PRIVATE);
            sharedPreferences.edit().putString("" + url, encoded).apply();
        } catch (Exception e) {
        }

    }

    public Bitmap getLinkToBitmap(String url) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("bitmapNavg", MODE_PRIVATE);
        try {
            byte[] decodedBytes = Base64.decode(
                    sharedPreferences.getString(url, "").substring(sharedPreferences.getString(url, "")
                            .indexOf(",") + 1),
                    Base64.DEFAULT
            );

            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            //Gson gson=new Gson();
            //String json2 = sharedPreferences.getString(url,"");
            //Type type = new TypeToken<Bitmap>() {
            //}.getType();
            //return gson.fromJson(json2, type);
        } catch (Exception e) {
            return null;
        }
    }

    public void clearBitmapsForNavgDrawer() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("bitmapNavg", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public void setNavgDrawerAnimList(List<String> animList) {
        Gson gson = new Gson();
        String json = gson.toJson(animList);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        sharedPreferences.edit().putString("drawerAnimList", json).apply();
        ;
    }

    public List<String> getNavgDrawerAnimList() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        try {
            Gson gson = new Gson();
            String json2 = sharedPreferences.getString("drawerAnimList", "");
            Type type = new TypeToken<List<String>>() {
            }.getType();
            return gson.fromJson(json2, type);
        } catch (Exception e) {
            return null;
        }
    }

    public gifStructure ifGifExist() {
        gifStructure gfs = new gifStructure();
        if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            String pathh = Environment.getExternalStorageDirectory().getPath() +
                    "/" + "animated.gif";

            gfs.setPath(pathh);
            File file = new File(pathh);
            if (!file.exists()) {
                gfs.setExist(false);
            } else {
                gfs.setExist(true);
            }
        } else {
            gfs.setPath(null);
            gfs.setExist(false);
        }
        return gfs;
    }

    public class gifStructure {
        String path;
        boolean isExist;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isExist() {
            return isExist;
        }

        public void setExist(boolean exist) {
            isExist = exist;
        }
    }

    public void clearTicket() {
        editorTicket.clear().apply();
    }

    public void setPUSH_TOKEN(String token) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("push", MODE_PRIVATE);
        sharedPreferences.edit().putString("pushToken", token).apply();
    }

    public String getPUSH_TOKEN() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("push", MODE_PRIVATE);
        return sharedPreferences.getString("pushToken", "");
    }

    public boolean getDistanceActive() {
        SharedPreferences sharedPreferencesFilter = getActivity().getSharedPreferences("filter", MODE_PRIVATE);
        return sharedPreferencesFilter.getBoolean("DistanceActive", false);
    }

    public void showErrorMessage(List<String> Message) {
        String msj = "";
        if (Message != null) {
            for (String m : Message) {
                msj += m + "\n";
            }
        } else msj = "Error";
        Toast.makeText(getActivity(), "" + msj, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), "" + text, Toast.LENGTH_SHORT).show();
    }

    public void userLogOut() {
        sharedPreferences.edit().clear().apply();
        preferencesTicket.edit().clear().apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(int colorId) {

        Window window = getActivity().getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ContextCompat.getColor(getActivity(), colorId));

    }

 /*   protected void openDraver() {
        try {
            ((MainActivity) getActivity()).openDraver();
        } catch (Exception e) {
        }
    }

    protected void closeDraver() {
        try {
            if (getActivity() instanceof MainActivity)
                ((MainActivity) getActivity()).closeDraver();
            else if (getActivity() instanceof FragmentControlActivity)
                ((FragmentControlActivity) getActivity()).closeDraver();

        } catch (Exception e) {
        }

    }
*/

    /**
     * Measure used memory and give garbage collector time to free up some
     * space.
     *
     * @param callback Callback operations to be done when memory is free.
     */
    public static void waitForGarbageCollector(final Runnable callback) {

        Runtime runtime;
        long maxMemory;
        long usedMemory;
        double availableMemoryPercentage = 1.0;
        final double MIN_AVAILABLE_MEMORY_PERCENTAGE = 0.1;
        final int DELAY_TIME = 5 * 1000;

        runtime =
                Runtime.getRuntime();

        maxMemory =
                runtime.maxMemory();

        usedMemory =
                runtime.totalMemory() -
                        runtime.freeMemory();

        availableMemoryPercentage =
                1 -
                        (double) usedMemory /
                                maxMemory;

        if (availableMemoryPercentage < MIN_AVAILABLE_MEMORY_PERCENTAGE) {

            try {
                Thread.sleep(DELAY_TIME);
                Log.d("Heap yetersiz ", "" + availableMemoryPercentage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            waitForGarbageCollector(
                    callback);
        } else {

            // Memory resources are availavle, go to next operation:

            callback.run();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getExStor(Uri uri) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        if ("primary".equalsIgnoreCase(type)) {
            return Environment.getExternalStorageDirectory() + "/" + split[1];
        }
        return "";
    }

    public String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getLollipopPath(Uri uri) {
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Video.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Video.Media._ID + "=?";

        Cursor cursor = getActivity().getContentResolver().
                query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);

        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public interface PermissionCallback {
        void onSuccess();

        void onFailure();
    }

    public HashMap<Integer, PermissionCallback> permissionCallbacks = new HashMap<Integer, PermissionCallback>();
    private int permissionRequestCodeSerial = 0;

    @TargetApi(23)
    public void requestExternalPermission(PermissionCallback callback) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                int requestCode = permissionRequestCodeSerial;
                permissionRequestCodeSerial += 1;
                permissionCallbacks.put(requestCode, callback);
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
            } else {
                callback.onSuccess();
            }
        } else {
            callback.onSuccess();
        }
    }

    @TargetApi(23)
    public void requestLocationPermission(PermissionCallback callback) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                int requestCode = permissionRequestCodeSerial;
                permissionRequestCodeSerial += 1;
                permissionCallbacks.put(requestCode, callback);
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
            } else if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                int requestCode = permissionRequestCodeSerial;
                permissionRequestCodeSerial += 1;
                permissionCallbacks.put(requestCode, callback);
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode);
            } else {
                callback.onSuccess();
            }
        } else {
            callback.onSuccess();
        }
    }

    public void getFileFromDeviceImg(Fragment activity, int REQUEST_CODE_GET_FILE_FROM_DEVICE) {
        Intent chooseFile = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        chooseFile.setType("image/*");
        Intent c = Intent.createChooser(chooseFile, "Resim Seç");
        activity.startActivityForResult(c, REQUEST_CODE_GET_FILE_FROM_DEVICE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("location61", "onActivityResult");
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        Log.e("location61", "Result OK");
                        break;
                    case RESULT_CANCELED:
                        Log.e("location61", "Result Cancel");
                        break;
                }
                break;
        }
    }

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
