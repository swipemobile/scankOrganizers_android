package com.scank.organizer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.pushwoosh.Pushwoosh;
import com.pushwoosh.exception.PushwooshException;
import com.pushwoosh.notification.PushwooshNotificationSettings;
import com.scank.organizer.R;
import com.scank.organizer.Utility.ApplicationClass;
import com.scank.organizer.Utility.Common;
import com.scank.organizer.Utility.Constants;
import com.scank.organizer.activity.adapter.EventAdapter;
import com.scank.organizer.interfaces.itemClickListener;
import com.scank.organizer.model.EventData;
import com.scank.organizer.model.EventGuestDetail;
import com.scank.organizer.model.EventTicketResponse;
import com.scank.organizer.model.EventsResponse;
import com.scank.organizer.model.LoginResponse;
import com.scank.organizer.model.RequestModelDeviceCode;
import com.scank.organizer.model.StaticModel;
import com.scank.organizer.model.SubmitTicketResponse;
import com.scank.organizer.model.TicketList;
import com.scank.organizer.model.TicketRequest;
import com.scank.organizer.network.APIClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity implements itemClickListener, View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private NavigationView nav_view;
    private TextView tvTitle;

    private RelativeLayout llLogout;
    private LinearLayout llHelp, llFaqs;
    private RecyclerView recyclerView;
    ActionBarDrawerToggle drawerToggle;
    AppCompatImageView ivImageSync;
    ProgressBar pbLoading;

    private Common common;
    private EventAdapter adapter;
    private List<EventData> eventDataList = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;
    private Realm realm;

    private TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        registerDevice();

        init();
        setListener();
        process();

    }

    public void init() {

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        common = Common.getInstance();

        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        tvTitle = toolbar.findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.your_events);
        tvNoData = findViewById(R.id.tv_no_data);

        ivImageSync = toolbar.findViewById(R.id.ivImageSync);
        ivImageSync.setVisibility(View.VISIBLE);
        pbLoading = toolbar.findViewById(R.id.pbLoading);

        configureToolbar();

        drawerToggle = setupDrawerToggle();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        llLogout = nav_view.findViewById(R.id.llLogout);
        llHelp = nav_view.findViewById(R.id.llHelp);
        llFaqs = nav_view.findViewById(R.id.llFaqs);

    }

    public void setListener() {

        drawerToggle.setToolbarNavigationClickListener(v -> toggle());

       swipeRefreshLayout.setOnRefreshListener(() -> {
           if(common.isOnline(true)) {
               eventDataList.clear();
               getOfflineCheckInList();
           }
           swipeRefreshLayout.setRefreshing(false);
        });

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        llLogout.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        llFaqs.setOnClickListener(this);
        ivImageSync.setOnClickListener(this);
    }

    public void process() {


        if(common.isOnline(true)) {
            getOfflineCheckInList();
        } else {
            getDatafromDB();
        }

    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void toggle() {
        if (mDrawer.isDrawerVisible(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            mDrawer.openDrawer(GravityCompat.START);
        }
    }

    public void loadEventsList() {

        LoginResponse loginResponse = ApplicationClass.getInstance().getLoginResult();
        Call<EventsResponse> eventResponse = APIClient.getApiService().getEventsList(loginResponse.getToken(), loginResponse.getUserId());

        eventResponse.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {

                ivImageSync.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);
                EventsResponse eventsResponse = response.body();
                if (eventsResponse.getStateCode() == 0) {
                    if (eventsResponse.getList() != null && eventsResponse.getList().size() > 0) {
                        eventDataList = eventsResponse.getList();
                        setAdater();
                    }
                } else {
                    common.responseErrorToast(eventsResponse.getStateCode(), HomeActivity.this, eventsResponse.getMessage());
                    // common.showErrorMessage(eventsResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
                ivImageSync.setVisibility(View.VISIBLE);
            }
        });

       /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();*/
    }

    public void setAdater() {
        tvNoData.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        if (adapter == null) {
            adapter = new EventAdapter(this, eventDataList);
            adapter.setItemClickListener(this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setEventDataArrayList(eventDataList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void itemClick(Object object, int id) {
        EventData eventData = null;
        if (object instanceof EventData) {
            eventData = (EventData) object;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.INTENT_KEY.TAB_ID, id);
        intent.putExtra(Constants.INTENT_KEY.EVENT_DATA, eventData);
        intent.putExtra(Constants.INTENT_KEY.CHECKED_IN_GUEST, eventData.getCheckedInGuest());
        intent.putExtra(Constants.INTENT_KEY.CHECKED_IN_RATIO, eventData.getCheckedInRatio());
        intent.putExtra(Constants.INTENT_KEY.TOTAL_GUEST, eventData.getTotalGuest());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (id == R.id.llLogout) {
            LoginResponse loginResponse = ApplicationClass.getInstance().getLoginResult();

            Call<StaticModel> loginCall = APIClient.getApiService().logout(loginResponse.getToken(), loginResponse.getUserId());
            loginCall.enqueue(new Callback<StaticModel>() {
                @Override
                public void onResponse(Call<StaticModel> call, Response<StaticModel> response) {
                    StaticModel staticModel = response.body();
                    if (staticModel.getStateCode() == 0) {
                        common.showToast("Logout successfully.");
                        common.logOut(HomeActivity.this);

                    } else {
                        common.showErrorMessage(staticModel.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<StaticModel> call, Throwable t) {

                }
            });
        } else if (id == R.id.llFaqs) {

            startActivity(new Intent(this, AboutActivity.class));

            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }

        } else if (id == R.id.llHelp) {

            common.setMailForFeedBack(this);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        } else if(id == R.id.ivImageSync) {
            if(pbLoading.getVisibility() == View.GONE) {
                pbLoading.setVisibility(View.VISIBLE);
                ivImageSync.setVisibility(View.GONE);
                getOfflineCheckInList();
            }
        }
    }

    public void getEventTicketList() {

        LoginResponse loginResponse = ApplicationClass.getInstance().getLoginResult();

        Call<EventTicketResponse> eventResponse = APIClient.getApiService().getEventTicketList(loginResponse.getToken(), loginResponse.getUserId());
        eventResponse.enqueue(new Callback<EventTicketResponse>() {
            @Override
            public void onResponse(Call<EventTicketResponse> call, Response<EventTicketResponse> response) {

                EventTicketResponse staticModel = response.body();
                if (staticModel.getStateCode() == 0) {
                    if (staticModel.getEventGuestDetails() != null && staticModel.getEventGuestDetails().size() > 0) {
                        // Copy elements from Retrofit to Realm to persist them.
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();

                        for(EventGuestDetail eventGuestDetail: staticModel.getEventGuestDetails()) {
                            for(TicketList ticketList : eventGuestDetail.getList()) {
                                ticketList.setEventId(eventGuestDetail.getEventId());
                            }
                        }
                        //  List<EventGuestDetail> realmRepos = realm.copyToRealmOrUpdate(staticModel.getEventGuestDetails());
                        realm.insertOrUpdate(staticModel.getEventGuestDetails());
                        realm.commitTransaction();

                    }
                }

            }

            @Override
            public void onFailure(Call<EventTicketResponse> call, Throwable t) {
            }
        });

    }


    private void getDatafromDB() {
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm -> {

                final RealmResults<EventGuestDetail> itemListData = realm.where(EventGuestDetail.class).findAll();
                for (EventGuestDetail eventGuestDetail : itemListData) {
                    EventData eventData = new EventData();
                    eventData.setId(eventGuestDetail.getEventId());
                    eventData.setTitle(eventGuestDetail.getEventTitle());
                    eventData.setStartDateStr(eventGuestDetail.getStartDateStr());
                    eventData.setEndDateStr(eventGuestDetail.getEndDateStr());
                    eventData.setStartDate(eventGuestDetail.getStartDate());
                    eventData.setEndDate(eventGuestDetail.getEndDate());
                    eventData.setSales(eventGuestDetail.getSales());
                    eventData.setPicture(eventGuestDetail.getPicture());
                    eventData.setTicketCount(eventGuestDetail.getTicketCount());
                    eventData.setDescription(eventGuestDetail.getDescription());

                    eventData.setCheckedInGuest(eventGuestDetail.getCheckedInGuest());
                    eventData.setCheckedInRatio(eventGuestDetail.getCheckedInRatio());
                    eventData.setTotalGuest(eventGuestDetail.getTotalGuest());
                    eventDataList.add(eventData);
                }

                if(eventDataList.size() > 0) {
                    setAdater();
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

    }

    private void getOfflineCheckInList() {
        try {

            realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm -> {

                final RealmResults<TicketList> ticketListData = realm.where(TicketList.class).equalTo("offlineCheckIn", true).findAll();
                StringBuilder str = new StringBuilder("");

                if (ticketListData != null && ticketListData.size() > 0) {

                    String[] b = new String[ticketListData.size()];

                    for ( TicketList ticketData : ticketListData) {

                        str.append(""+ticketData.getId()).append(",");
                    }

                    String commaseparatedlist = str.toString();
                    if (commaseparatedlist.length() > 0)
                        commaseparatedlist = commaseparatedlist.substring( 0, commaseparatedlist.length() - 1);

                    TicketRequest ticketRequest = new TicketRequest();
                    ticketRequest.setCheckedInTicketId(commaseparatedlist);

                    if(commaseparatedlist != null ) {
                        updateTicketCheckedInApi(ticketRequest);
                    }

                } else {
                    loadEventsList();
                    getEventTicketList();
                }

            });
        } catch (Exception e) {
            loadEventsList();
            getEventTicketList();
            e.printStackTrace();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

    }

    public void updateTicketCheckedInApi(TicketRequest jsonObject) {

        LoginResponse loginResponse = ApplicationClass.getInstance().getLoginResult();

        Call<SubmitTicketResponse> eventResponse = APIClient.getApiService().updateTicketCheckedIn(loginResponse.getToken(), jsonObject);
        eventResponse.enqueue(new Callback<SubmitTicketResponse>() {
            @Override
            public void onResponse(Call<SubmitTicketResponse> call, Response<SubmitTicketResponse> response) {

                SubmitTicketResponse staticModel = response.body();
                if (staticModel.getStateCode() == 0 && staticModel.getState()) {
                    //common.showToast(staticModel.getSuccessCounts() + " ticket status updated.");
                    loadEventsList();
                    getEventTicketList();
                }

            }

            @Override
            public void onFailure(Call<SubmitTicketResponse> call, Throwable t) {
                // loginAction();
            }
        });

    }


    public void registerDevice( )
    {
        Pushwoosh.getInstance().registerForPushNotifications(result -> {
            if (result.isSuccess()) {
                String token = result.getData();

                // handle successful registration
                setPushToken(token);

                PushwooshNotificationSettings.setMultiNotificationMode(true);
                RequestModelDeviceCode deviceCode = new RequestModelDeviceCode();
                deviceCode.setDeviceCode(token);
                deviceCode.setType("1");
                deviceCode.setUserId(getUserId());

                Call<StaticModel> setDeviceCodeCall = APIClient.getApiService().updateDeviceCode(deviceCode);
                setDeviceCodeCall.enqueue(new Callback<StaticModel>() {
                    @Override
                    public void onResponse(Call<StaticModel> call, Response<StaticModel> response) {

                    }

                    @Override
                    public void onFailure(Call<StaticModel> call, Throwable t) {

                    }
                });

            }
            else {
                PushwooshException exception = result.getException();
                // handle registration error
                Log.i("exception",exception.toString());
            }
        });
    }

    public void setPushToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        sharedPreferences.edit().putString("pushToken",token).apply();
    }

    public int getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        return sharedPreferences.getInt("userId",0);
    }
}
