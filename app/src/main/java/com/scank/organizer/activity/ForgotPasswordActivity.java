package com.scank.organizer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.scank.organizer.R;
import com.scank.organizer.Utility.Common;
import com.scank.organizer.model.StaticModel;
import com.scank.organizer.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtForgotEmail;
    private Button btResetPassword;
    AppCompatButton ivBackForgot;

    private Common common;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        common = Common.getInstance();

        init();
        setListener();
    }

    public void init() {

        ivBackForgot = findViewById(R.id.ivBackForgot);

        edtForgotEmail = findViewById(R.id.edtForgotEmail);
        btResetPassword = findViewById(R.id.btResetPassword);
    }

    public void setListener() {

        btResetPassword.setOnClickListener(this);
        ivBackForgot.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.ivBackForgot) {
            onBackPressed();
        } else if(id == R.id.btResetPassword) {
            if(!edtForgotEmail.getText().toString().trim().equals("")) {
                Call<StaticModel> forgotCall = APIClient.getApiService().forgotPassword(edtForgotEmail.getText().toString());
                forgotCall.enqueue(new Callback<StaticModel>() {
                    @Override
                    public void onResponse(Call<StaticModel> call, Response<StaticModel> response) {

                        StaticModel staticModel = response.body();
                        if(staticModel.getStateCode() == 0) {
                            common.showToast("Please check your mail inbox");
                            edtForgotEmail.setText("");
                        } else {
                            common.showErrorMessage(staticModel.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<StaticModel> call, Throwable t) {

                    }
                });
            } else {
                common.showToast(getString(R.string.check_your_email));
            }
        }
    }

    /* @Override
    public void getResponseBody(Object model, int ii) {
        if(ii== Constants.REQUEST_CODE.FORGOT_PASSWORD){

            StaticModel m =(StaticModel)model;

            if (m.getState().equals("true")) {
                Toast.makeText(this, R.string.check_your_email, Toast.LENGTH_SHORT).show();
            }
            else {
                showErrorMessage(m.getMessage());
            }

        }
    }*/

}
