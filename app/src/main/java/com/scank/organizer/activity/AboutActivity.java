package com.scank.organizer.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.scank.organizer.R;
import com.scank.organizer.model.ResponseWalkthroughModel;
import com.scank.organizer.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity {

    TextView tvAboutUs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setToolbar();
        tvAboutUs = findViewById(R.id.tvAboutUs);

        Call<ResponseWalkthroughModel> aboutCall = APIClient.getApiService().getWalkthroughList();
        aboutCall.enqueue(new Callback<ResponseWalkthroughModel>() {
            @Override
            public void onResponse(Call<ResponseWalkthroughModel> call, Response<ResponseWalkthroughModel> response) {

                ResponseWalkthroughModel m = response.body();
                if (m.getAbout() != null && m.getAbout().length() > 0){
                    tvAboutUs.setText(Html.fromHtml(m.getAbout()));
                }

            }

            @Override
            public void onFailure(Call<ResponseWalkthroughModel> call, Throwable t) {

            }
        });
    }

    public void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
