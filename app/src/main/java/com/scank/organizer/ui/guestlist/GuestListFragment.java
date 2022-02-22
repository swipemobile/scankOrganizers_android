package com.scank.organizer.ui.guestlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.scank.organizer.R;
import com.scank.organizer.Utility.ApplicationClass;
import com.scank.organizer.Utility.Common;
import com.scank.organizer.Utility.Constants;
import com.scank.organizer.activity.ScanModeActivty;
import com.scank.organizer.interfaces.itemClickListener;
import com.scank.organizer.model.EventData;
import com.scank.organizer.model.GuestApiResponse;
import com.scank.organizer.model.GuestData;
import com.scank.organizer.model.LoginResponse;
import com.scank.organizer.model.TicketList;
import com.scank.organizer.network.APIClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestListFragment extends Fragment implements itemClickListener, SearchView.OnQueryTextListener {

    private GuestListViewModel guestListViewModel;
    private LinearLayout llScanTicket, llManualEntry;
    private RecyclerView recyclerView;
    private SeekBar seekbar;
    private TextView tvSalesTicket, tvTotalTickets;
    private SearchView searchView;

    private RelativeLayout rlMain;

    private EventData eventData;
    private List<GuestData> guestDataList = new ArrayList<>();
    private Common common;

    private SwipeRefreshLayout swipeRefreshLayout;

    private Realm realm;

    private GuestListAdapter adapter;

    public GuestListFragment(EventData eventData, Intent intent) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INTENT_KEY.EVENT_DATA, eventData);
        setArguments(bundle);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_guestlist, container, false);

        guestListViewModel = new ViewModelProvider(this).get(GuestListViewModel.class);
        common = Common.getInstance();

        Bundle bundle = getArguments();
        eventData = bundle.getParcelable(Constants.INTENT_KEY.EVENT_DATA);

        init(root);
        process();

        return root;
    }


    public void init(View root) {

        searchView = root.findViewById(R.id.searchView);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);

        rlMain = root.findViewById(R.id.rlMain);
        rlMain.requestFocus();

        llScanTicket = root.findViewById(R.id.llScanTicket);
        llManualEntry = root.findViewById(R.id.llManualEntry);

        recyclerView = root.findViewById(R.id.recyclerView);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);

        tvSalesTicket = root.findViewById(R.id.tvSalesTicket);
        tvTotalTickets = root.findViewById(R.id.tvTotalTickets);
        seekbar = root.findViewById(R.id.seekbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

       /* DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);*/
        recyclerView.setLayoutManager(linearLayoutManager);

        customizeSearchView();

    }

    private void customizeSearchView() {
        int searchTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchBox = ((EditText) searchView.findViewById(searchTextId));
        searchBox.setBackgroundColor(Color.parseColor("#F8F8F8"));
        searchBox.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
        int search_plateId = getResources().getIdentifier("android:id/search_plate", null, null);
        View mSearchPlate = ((View) searchView.findViewById(search_plateId));
        mSearchPlate.setBackgroundColor(Color.parseColor("#F8F8F8"));

        searchView.setSubmitButtonEnabled(false);
       /* int searchCloseImageId = getResources().getIdentifier(android.id.android:id/search_close_btn", null, null);
        ImageView searchClose = ((ImageView) searchView.findViewById(searchCloseImageId));// change color
        searchClose.setBackgroundColor(Color.WHITE);*/
    }

    public void process() {

        llScanTicket.setOnClickListener(view -> {

            searchView.clearFocus();
            searchView.setFocusable(false);
            Intent intent = new Intent(getActivity(), ScanModeActivty.class);
            intent.putExtra(Constants.INTENT_KEY.SCAN_TYPE, 1);
            startActivityForResult(intent, Constants.REQUEST_CODE.SCAN_CODE);
        });

        llManualEntry.setOnClickListener(view -> {
            searchView.clearFocus();
            searchView.setFocusable(false);
            Intent intent = new Intent(getActivity(), ScanModeActivty.class);
            intent.putExtra(Constants.INTENT_KEY.SCAN_TYPE, 2);
            startActivityForResult(intent, Constants.REQUEST_CODE.SCAN_CODE);
        });

        guestListViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if(common.isOnline(true)) {
                guestDataList.clear();
                getGuestListData();
            } else {
                    seekbar.setProgress((int)eventData.getCheckedInRatio());
                    tvSalesTicket.setText(new StringBuilder().append(eventData.getCheckedInGuest()).toString());
                    tvTotalTickets.setText(new StringBuilder().append("/ ").append(eventData.getTotalGuest()));
                    getTicketDataFromDb();
            }
            swipeRefreshLayout.setRefreshing(false);
        });

        if(common.isOnline(true)) {
            getGuestListData();
        } else {
            seekbar.setProgress((int)eventData.getCheckedInRatio());
            tvSalesTicket.setText(new StringBuilder().append(eventData.getCheckedInGuest()).toString());
            tvTotalTickets.setText(new StringBuilder().append("/ ").append(eventData.getTotalGuest()));
            getTicketDataFromDb();
        }
    }

    public void getGuestListData() {

        LoginResponse loginResponse = ApplicationClass.getInstance().getLoginResult();

        Call<GuestApiResponse> eventResponse = APIClient.getApiService().getGuestList(loginResponse.getToken(), eventData.getId());
        eventResponse.enqueue(new Callback<GuestApiResponse>() {
            @Override
            public void onResponse(Call<GuestApiResponse> call, Response<GuestApiResponse> response) {
                GuestApiResponse guestApiResponse = response.body();
                if (guestApiResponse.getStateCode() == 0) {

                    seekbar.setProgress(guestApiResponse.getCheckedInRatio());
                    tvSalesTicket.setText(String.valueOf(guestApiResponse.getCheckedInGuest()));
                    tvTotalTickets.setText(new StringBuilder().append("/ ").append(guestApiResponse.getTotalGuest()));

                    if (guestApiResponse.getList().size() > 0) {
                        guestDataList = guestApiResponse.getList();
                        setAdater();
                    }
                } else {
                    common.responseErrorToast(guestApiResponse.getStateCode(), getActivity(), guestApiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GuestApiResponse> call, Throwable t) {

            }
        });
    }

    public void setAdater() {
     //  if (adapter == null) {
            adapter = new GuestListAdapter(getContext(), guestDataList);
            adapter.setItemClickListener(this);
            recyclerView.setAdapter(adapter);
       /* } /*else {
            guestDataList.add(guestDataList.get(1));
            adapter.setGuestDataList(guestDataList);
            adapter.notifyDataSetChanged();
        }*/
    }

    @Override
    public void itemClick(Object model, int id) {
        GuestData guestData = null;
        if (model instanceof GuestData) {
            guestData = (GuestData) model;
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            adapter.getFilter().filter(null);
        } else {
            adapter.getFilter().filter(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == Constants.REQUEST_CODE.SCAN_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if(common.isOnline(true)) {
                    getGuestListData();
                } else {
                    seekbar.setProgress((int)eventData.getCheckedInRatio());
                    tvSalesTicket.setText(new StringBuilder().append(eventData.getCheckedInGuest()).toString());
                    tvTotalTickets.setText(new StringBuilder().append("/ ").append(eventData.getTotalGuest()));
                    getTicketDataFromDb();
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getTicketDataFromDb() {
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm -> {
                final RealmResults<TicketList> itemListData = realm.where(TicketList.class).equalTo("eventId", eventData.getId()).findAll();
                guestDataList.clear();

                int checkedInGuest = 0;

                for (TicketList ticketList : itemListData) {
                    GuestData guestData = new GuestData();
                    guestData.setId(ticketList.getId());
                    guestData.setName(ticketList.getName());
                    guestData.setTicket(ticketList.getTicket());
                    guestData.setLastUpdateDate(ticketList.getLastUpdateDate());
                    guestData.setCheckIn(ticketList.getCheckIn());
                    guestData.setOrderNumber(ticketList.getOrderNumber());

                    if(guestData.getCheckIn()) {
                        checkedInGuest++;
                    }
                    guestDataList.add(guestData);

                }

                if(guestDataList.size() > 0) {

                    if(checkedInGuest > 0 && checkedInGuest > eventData.getCheckedInGuest()) {
                        int per = (checkedInGuest * 100) / guestDataList.size();
                        seekbar.setProgress(per);
                        tvSalesTicket.setText(new StringBuilder().append(checkedInGuest).toString());
                    }
                    setAdater();
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }
}