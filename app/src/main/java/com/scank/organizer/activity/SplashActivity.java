package com.scank.organizer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.pushwoosh.Pushwoosh;
import com.scank.organizer.R;
import com.scank.organizer.Utility.ApplicationClass;
import com.scank.organizer.Utility.Common;
import com.scank.organizer.Utility.PreferencesHelper;
import com.scank.organizer.model.LoginResponse;
import com.scank.organizer.model.SubmitTicketResponse;
import com.scank.organizer.model.TicketList;
import com.scank.organizer.model.TicketRequest;
import com.scank.organizer.network.APIClient;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {

    private Common common;
    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        common = Common.getInstance();
        getOfflineCheckInList();

    }

    public void loginAction() {

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            if (ApplicationClass.getInstance().isUserLogin()) {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            } else {
                if (PreferencesHelper.isIntroPage(SplashActivity.this)) {
                    //redirect on Login screen because user not logged in
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, IntroPageActivity.class));
                    finish();
                }
            }
        }, 3000);

    }


    private void getOfflineCheckInList() {
        try {

            realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm -> {

                final RealmResults<TicketList> ticketListData = realm.where(TicketList.class).equalTo("offlineCheckIn", true).findAll();
                StringBuilder str = new StringBuilder("");

                if (ticketListData != null && ticketListData.size() > 0) {

                    for ( TicketList ticketData : ticketListData) {

                        str.append(""+ticketData.getId()).append(",");
                    }

                    String commaseparatedlist = str.toString();
                    if (commaseparatedlist.length() > 0)
                        commaseparatedlist = commaseparatedlist.substring( 0, commaseparatedlist.length() - 1);

                    TicketRequest ticketRequest = new TicketRequest();
                    ticketRequest.setCheckedInTicketId(commaseparatedlist);
                    updateEventTicketList(ticketRequest);

                } else {
                    loginAction();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

    }

    public void updateEventTicketList(TicketRequest jsonObject) {

        LoginResponse loginResponse = ApplicationClass.getInstance().getLoginResult();

        Call<SubmitTicketResponse> eventResponse = APIClient.getApiService().updateTicketCheckedIn(loginResponse.getToken(), jsonObject);
        eventResponse.enqueue(new Callback<SubmitTicketResponse>() {
            @Override
            public void onResponse(Call<SubmitTicketResponse> call, Response<SubmitTicketResponse> response) {

                SubmitTicketResponse staticModel = response.body();
                if (staticModel.getStateCode() == 0 && staticModel.getState()) {
                    common.showToast(staticModel.getSuccessCounts() + " ticket status updated.");
                    loginAction();
                } else {
                    loginAction();
                }

            }

            @Override
            public void onFailure(Call<SubmitTicketResponse> call, Throwable t) {
                loginAction();
            }
        });

    }

   /* private void updateOfflineCheckInList() {
        try {
            realm = Realm.getDefaultInstance();
            final RealmResults<TicketList> ticketListData = realm.where(TicketList.class).equalTo("offlineCheckIn", true).findAll();

            if (ticketListData != null && ticketListData.size() > 0) {
                for (TicketList ticketList : ticketListData) {
                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    ticketList.setOfflineCheckIn(false);
                    realm.insertOrUpdate(ticketList);
                    realm.commitTransaction();
                }
                loginAction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
}
