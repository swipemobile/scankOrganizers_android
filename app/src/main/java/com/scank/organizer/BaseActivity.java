package com.scank.organizer;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.scank.organizer.activity.LoginActivity;

/**
 * Created on 5.09.2020.
 */

public abstract class BaseActivity extends AppCompatActivity  {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedPreferences = getActivity().getSharedPreferences("userFile", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public Activity getActivity(){return this;}


    public void userLogOut(){
        sharedPreferences.edit().clear().apply();
    }


    public void showAuthenticationDialog(final Context context) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Authentication Required");
        builder1.setMessage("You need to login for this operation!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(context, LoginActivity.class));

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }
}
