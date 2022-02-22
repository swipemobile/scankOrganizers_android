package com.scank.organizer.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.scank.organizer.R;
import com.scank.organizer.Utility.ApplicationClass;
import com.scank.organizer.Utility.Common;
import com.scank.organizer.Utility.Constants;
import com.scank.organizer.model.EventData;
import com.scank.organizer.model.LoginResponse;
import com.scank.organizer.model.StaticModel;
import com.scank.organizer.network.APIClient;
import com.scank.organizer.ui.guestlist.GuestListFragment;
import com.scank.organizer.ui.manage.ManageFragment;
import com.scank.organizer.ui.reports.ReportsFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private NavigationView nav_view;

    private TextView tvTitle, tvDesc;
    private RelativeLayout llLogout;
    private LinearLayout llHelp, llFaqs;

    ActionBarDrawerToggle drawerToggle;
    private int selectedTab = 0;
    private EventData eventData;

    private Common common;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_tab_page);

        init();
        process();
        setListener();


        selectedTab = getIntent().getIntExtra(Constants.INTENT_KEY.TAB_ID, 1);
        eventData = getIntent().getParcelableExtra(Constants.INTENT_KEY.EVENT_DATA);

        if(selectedTab == 1) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_reports);
        } else if(selectedTab == 2) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_guestlist);
        } else if(selectedTab == 3) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_manage);
        }

    }

    public void init() {

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);

        common = Common.getInstance();

        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        tvTitle = toolbar.findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.your_events);
        tvDesc = toolbar.findViewById(R.id.tvDesc);

        configureToolbar();

        drawerToggle = setupDrawerToggle();
        llLogout = nav_view.findViewById(R.id.llLogout);
        llHelp = nav_view.findViewById(R.id.llHelp);
        llFaqs = nav_view.findViewById(R.id.llFaqs);

    }

    public void process() {
        tvTitle.setText(getString(R.string.your_events));
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
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
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

    public void setListener() {

        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                       /* tvTitle.setText("Your Events");
                        tvDesc.setVisibility(View.GONE);
                        loadFragment(new ReportsFragment());*/
                        selectedTab = 0;
                        onBackPressed();
                        break;
                    case R.id.navigation_reports:
                        tvTitle.setText(R.string.reports);
                        tvDesc.setText(eventData.getTitle());
                        tvDesc.setVisibility(View.VISIBLE);
                        loadFragment(new ReportsFragment(eventData));
                        break;
                    case R.id.navigation_guestlist:
                        tvTitle.setText(R.string.guest_list);
                        tvDesc.setText(eventData.getTitle());
                        tvDesc.setVisibility(View.VISIBLE);

                        loadFragment(new GuestListFragment(eventData, getIntent()));
                        break;
                    case R.id.navigation_manage:
                        tvTitle.setText(R.string.manage);
                        tvDesc.setVisibility(View.GONE);

                        loadFragment(new ManageFragment(eventData));
                        break;
                }
                return true;
            }
        });


        llLogout.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        llFaqs.setOnClickListener(this);

    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        if (drawer.isDrawerOpen(GravityCompat. START )) {
            drawer.closeDrawer(GravityCompat. START ) ;
        } else {
            super.onBackPressed() ;
        }
    }

 /*   public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, intent);
        }
    }*/

    @Override
    public void onClick(View view) {
        int id = view.getId();
        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;

        if(id == R.id.llLogout) {
            LoginResponse loginResponse = ApplicationClass.getInstance().getLoginResult();

            Call<StaticModel> loginCall = APIClient.getApiService().logout(loginResponse.getToken(), loginResponse.getUserId());
            loginCall.enqueue(new Callback<StaticModel>() {
                @Override
                public void onResponse(Call<StaticModel> call, Response<StaticModel> response) {
                    StaticModel staticModel = response.body();
                    if(staticModel.getStateCode() == 0) {
                        common.showToast("Logout successfully.");
                        common.logOut(MainActivity.this);

                    } else {
                        common.showErrorMessage(staticModel.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<StaticModel> call, Throwable t) {

                }
            });
        } else if(id == R.id.llFaqs) {

            startActivity(new Intent(this, AboutActivity.class));

            if (drawer.isDrawerOpen(GravityCompat. START )) {
                drawer.closeDrawer(GravityCompat. START ) ;
            }

        } else if(id == R.id.llHelp) {

            common.setMailForFeedBack(this);
            if (drawer.isDrawerOpen(GravityCompat. START )) {
                drawer.closeDrawer(GravityCompat. START ) ;
            }
        }
    }

}
