package com.scank.organizer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.pushwoosh.Pushwoosh;
import com.pushwoosh.notification.PushwooshNotificationSettings;
import com.scank.organizer.R;
import com.scank.organizer.Utility.ApplicationClass;
import com.scank.organizer.Utility.Common;
import com.scank.organizer.model.LoginRequest;
import com.scank.organizer.model.LoginResponse;
import com.scank.organizer.network.APIClient;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton btSignup, btSignin;

    AppCompatEditText edtEmail, edtPassword;
    AppCompatTextView tvSignup, tvForgotPassword;
    ProgressBar progressBar;
    AppCompatImageView ivHideShow;
    AppCompatCheckBox cbRememberMe;

    RelativeLayout rlMain;

    private Common common;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerDevice();

        init();
        setListener();
    }

    public void init() {

        edtEmail = findViewById(R.id.edtEmail);
        rlMain = findViewById(R.id.rlMain);
        edtPassword = findViewById(R.id.edtPassword);
        progressBar = findViewById(R.id.progressBar);

        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignup = findViewById(R.id.tvSignup);
        ivHideShow = findViewById(R.id.ivVisiblePassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);

        btSignup = findViewById(R.id.btSignup);
        btSignin = findViewById(R.id.btSignin);

        common = Common.getInstance();
        cbRememberMe.setChecked(isRemember());

        if (cbRememberMe.isChecked()) {
            edtEmail.setText(getLoginUserName());
            edtPassword.setText(getLoginPassWord());
        }
    }

    public String getLoginPassWord() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Scank", MODE_PRIVATE);
        return sharedPreferences.getString("loginPassword", "");
    }

    public String getLoginUserName() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Scank", MODE_PRIVATE);
        return sharedPreferences.getString("loginId", "");
    }

    public void setRememberData(String userName, String passWord, boolean isRemeber) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Scank", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isRemember",isRemeber);
        editor.putString("loginId",userName);
        editor.putString("loginPassword",passWord);
        editor.apply();
    }

    public boolean isRemember() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Scank", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isRemember", false);
    }

    public void setListener() {

        tvForgotPassword.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
        ivHideShow.setOnClickListener(this);

        btSignup.setOnClickListener(this);
        btSignin.setOnClickListener(this);

        cbRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btSignup || id == R.id.tvSignup) {
            startActivity(new Intent(this, SignUpActivity.class));
        } else if (id == R.id.btSignin) {
            validateLogin();
        } else if (id == R.id.tvForgotPassword) {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        } else if (id == R.id.ivVisiblePassword) {
            if (edtPassword.getTransformationMethod() == null) {
                edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                ivHideShow.setImageResource(R.drawable.ic_eye_red);
            } else {
                edtPassword.setTransformationMethod(null);
                ivHideShow.setImageResource(R.drawable.ic_eye_gray);
            }
        }
    }

    public void validateLogin() {
        if (common.isValidEmail(edtEmail.getText().toString().trim())) {
            if (edtPassword.length() > 0) {

                String uniqueID = UUID.randomUUID().toString();

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(edtEmail.getText().toString());
                loginRequest.setPassword(edtPassword.getText().toString());
                loginRequest.setUdId(uniqueID);
                loginRequest.setDeviceCode(getPushToken());

                Call<LoginResponse> loginCall = APIClient.getApiService().login(loginRequest);
                progressBar.setVisibility(View.VISIBLE);
                loginCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if (cbRememberMe.isChecked()) {
                            setRememberData(edtEmail.getText().toString(), edtPassword.getText().toString(), true);
                        } else {
                            setRememberData("", "", false);
                        }

                        progressBar.setVisibility(View.GONE);

                        LoginResponse loginResponse = response.body();
                        if (loginResponse.getStateCode() == 0 && loginResponse.getState()) {
                            if(loginResponse.getUserId() != null) {
                                setUserId(loginResponse.getUserId());
                            }
                            ApplicationClass.getInstance().setLoginResult(loginResponse);
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            common.showErrorMessage(loginResponse.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        common.showToast(t.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });

            } else {
                common.showToast(getResources().getString(R.string.password_not_empty));
            }
        } else {
            common.showToast(getResources().getString(R.string.wrong_email_format));
        }
    }

    public void registerDevice( )
    {
        Pushwoosh.getInstance().registerForPushNotifications(result -> {
            if (result.isSuccess()) {
                String token = result.getData();
                // handle successful registration
                setPushToken(token);
                PushwooshNotificationSettings.setMultiNotificationMode(true);
            }
        });
    }

    public void setPushToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        sharedPreferences.edit().putString("pushToken",token).apply();
    }

    public String getPushToken() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        return sharedPreferences.getString("pushToken","");
    }

    public void setUserId(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        sharedPreferences.edit().putInt("userId",userId).apply();
    }
}
