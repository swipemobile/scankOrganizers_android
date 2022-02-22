package com.scank.organizer.helper;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.pushwoosh.notification.NotificationServiceExtension;
import com.pushwoosh.notification.PushMessage;
import com.scank.organizer.activity.MainActivity;
import com.scank.organizer.activity.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created on 8.09.2021.
 */

public class NotificationReceiver extends NotificationServiceExtension {
    @Override
    protected boolean onMessageReceived(PushMessage pushMessage) {


        try {
            JSONObject dataObject = pushMessage.toJson();
            JSONObject userObject = dataObject.getJSONObject("userdata");
            String customObject = userObject.getString("custom");
            JSONObject object = new JSONObject(customObject);
            String type = object.getString("Type");
            if (type.equals("0"))
                updateMainActivityForNewMessage(object.getString("Id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return super.onMessageReceived(pushMessage);
    }

    public String getUserId() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userFile", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        return userId;
    }

    @Override
    protected void startActivityForPushMessage(PushMessage message) {

        try {
            JSONObject dataObject = message.toJson();
            JSONObject userObject = dataObject.getJSONObject("userdata");
            String customObject = userObject.getString("custom");

            JSONObject object = new JSONObject(customObject);
            String type = object.getString("Type");
            String id = object.getString("Id");
            //String name = object.getString("Username");


            if (type.equals("1")){//Sale

            }else if (type.equals("2")){//Order

            }else {
                //Main açtivity aç
                getApplicationContext().startActivity(new Intent(getApplicationContext(), SplashActivity.class));
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void updateMainActivityForNewMessage(String message) {

        Intent intent = new Intent("message_receiver");

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }
}