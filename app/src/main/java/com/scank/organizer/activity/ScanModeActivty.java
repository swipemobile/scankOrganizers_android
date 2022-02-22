package com.scank.organizer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.scank.organizer.R;
import com.scank.organizer.Utility.ApplicationClass;
import com.scank.organizer.Utility.Common;
import com.scank.organizer.Utility.Constants;
import com.scank.organizer.model.CheckInResponse;
import com.scank.organizer.model.EventGuestDetail;
import com.scank.organizer.model.EventTicketResponse;
import com.scank.organizer.model.LoginResponse;
import com.scank.organizer.model.SubmitTicketResponse;
import com.scank.organizer.model.TicketDetails;
import com.scank.organizer.model.TicketList;
import com.scank.organizer.model.TicketRequest;
import com.scank.organizer.network.APIClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanModeActivty extends AppCompatActivity implements View.OnClickListener {

    TextView tvExitScan, tvScanType;
    RelativeLayout rlExitScan, rlScanTicket;
    ImageView ivScanTypeImage;
    FrameLayout frameScan;

    private int scanType = 1;
    private Common common;
    private CodeScanner mCodeScanner;

    private ProgressBar progressBar;

    private boolean isScanPerform = false;
    private boolean isScanViewLoad = false;

    private Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        init();
        process();
        setListener();
    }

    public void init() {

        common = Common.getInstance();

        progressBar = findViewById(R.id.progressBar);
        frameScan = findViewById(R.id.frameScan);
        rlExitScan = findViewById(R.id.rlExitScan);
        rlScanTicket = findViewById(R.id.rlScanTicket);
        ivScanTypeImage = findViewById(R.id.ivScanTypeImage);

        tvExitScan = findViewById(R.id.tvExitScan);
        tvScanType = findViewById(R.id.tvScanType);

        if(common.isOnline(false)) {
            getOfflineCheckInList();
        }
    }

    public void process() {
        Intent intent = getIntent();
        scanType = intent.getIntExtra(Constants.INTENT_KEY.SCAN_TYPE, 1);
        changeScannerView();

    }

    public void setListener() {
        rlExitScan.setOnClickListener(this);
        rlScanTicket.setOnClickListener(this);
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                Constants.REQUEST_PERMISSION.CAMERA);
    }

    public void changeScannerView() {

        if (scanType == 1) {
            frameScan.setVisibility(View.VISIBLE);
            tvExitScan.setText(getResources().getString(R.string.exit_scan));
            ivScanTypeImage.setImageResource(R.drawable.ic_grid);
            tvScanType.setText(getResources().getString(R.string.manual_entry));

            if (!isScanViewLoad) {
                loadScannerView();
            }

            if (checkCameraPermission()) {
                if (!mCodeScanner.isPreviewActive()) {
                    mCodeScanner.startPreview();
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermission();
                }
            }

        } else {
            frameScan.setVisibility(View.GONE);
            tvExitScan.setText(getResources().getString(R.string.exit_manual));
            ivScanTypeImage.setImageResource(R.drawable.ic_scan);
            tvScanType.setText(getResources().getString(R.string.scan_ticket));

            showChangeLangDialog();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                setResult(RESULT_OK);
                onBackPressed();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadScannerView() {

        isScanViewLoad = true;
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (progressBar.getVisibility() == View.GONE) {
                            if(common.isOnline(false)) {
                                callCheckInApi(result.getText());
                            } else {
                                getTicketDataFromDb(result.getText(), true);
                            }
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rlExitScan) {
            setResult(RESULT_OK);
            onBackPressed();
        } else if (id == R.id.rlScanTicket) {

            if (scanType == 1) {
                scanType = 2;
            } else {
                scanType = 1;
            }
            changeScannerView();

        }
    }

    public void showChangeLangDialog() {

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_enter_ticket, null);
        final EditText edtTicketNum = dialogView.findViewById(R.id.edtTicketNum);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false);
        builder.setTitle("Manually Enter Ticket No");
        builder.setPositiveButton("Enter", null);
        builder.setNegativeButton("Cancel", (dialog, whichButton) -> {
            //dialog.dismiss();
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        okButton.setOnClickListener(v -> {
            // Do your stuff here
            String ticketNum = edtTicketNum.getText().toString().trim();
            if (ticketNum.isEmpty()) {
                common.showToast("Enter ticket number");
            } else if (ticketNum.length() < 4 || ticketNum.length() > 6) {
                common.showToast("Enter valid ticket number");
            } else {

                if (progressBar.getVisibility() == View.GONE) {
                    if(common.isOnline(false)) {
                        callCheckInApi(ticketNum);
                    } else {
                        getTicketDataFromDb(ticketNum, true);
                    }
                }
                alertDialog.dismiss();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkCameraPermission()) {
            if (mCodeScanner != null) {
                mCodeScanner.startPreview();
            }
        }
    }

    @Override
    protected void onPause() {
        if (checkCameraPermission()) {
            if (mCodeScanner != null) {
                mCodeScanner.releaseResources();
            }
        }
        super.onPause();
    }

    public void callCheckInApi(String ticketId) {
        LoginResponse loginResponse = ApplicationClass.getInstance().getLoginResult();
        progressBar.setVisibility(View.VISIBLE);

        Call<CheckInResponse> eventResponse = APIClient.getApiService().checkIn(loginResponse.getToken(), ticketId);
        eventResponse.enqueue(new Callback<CheckInResponse>() {
            @Override
            public void onResponse(Call<CheckInResponse> call, Response<CheckInResponse> response) {
                progressBar.setVisibility(View.GONE);
                CheckInResponse staticModel = response.body();
                if (staticModel.getStateCode() == 0) {
                    if (staticModel.getTicketDetails() != null) {
                        // common.responseErrorToast(staticModel.getStateCode(), ScanModeActivty.this, staticModel.getMessage());
                        isScanPerform = true;
                        getTicketDataFromDb(ticketId, false);

                        Intent intent = new Intent(ScanModeActivty.this, ScanSuccessActivty.class);
                        intent.putExtra(Constants.INTENT_KEY.TICKET_DATA, staticModel.getTicketDetails());
                        startActivityForResult(intent, 1001);
                    } else {
                        common.responseErrorToast(staticModel.getStateCode(), ScanModeActivty.this, staticModel.getMessage());
                    }
                } else {
                    common.responseErrorToast(staticModel.getStateCode(), ScanModeActivty.this, staticModel.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CheckInResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_PERMISSION.CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // main logic
                } else {
                    common.showToast("Permission Denied");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access camera permission for scan",
                                    (dialog, which) -> {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermission();
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                getOfflineCheckInList();
                if (scanType == 2) {
                    showChangeLangDialog();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ScanModeActivty.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void getTicketDataFromDb(String ticketNumber, boolean isOffline) {
        try {

            realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm -> {
                final TicketList ticketList = realm.where(TicketList.class).equalTo("id", Integer.parseInt(ticketNumber)).findFirst();

                if (ticketList != null) {
                    if (ticketList.getCheckIn()) {
                        common.showToast("Ticket already used.");
                    } else {
                        ticketList.setCheckIn(true);
                        ticketList.setOfflineCheckIn(true);

                        getcurrentDate(ticketList);

                        realm.insertOrUpdate(ticketList);

                        TicketDetails ticketDetails = new TicketDetails();
                        ticketDetails.setName(ticketList.getName());
                        ticketDetails.setId(ticketList.getId());

                        if(isOffline) {
                            isScanPerform = true;
                            Intent intent = new Intent(ScanModeActivty.this, ScanSuccessActivty.class);
                            intent.putExtra(Constants.INTENT_KEY.TICKET_DATA, ticketDetails);
                            startActivityForResult(intent, 1001);
                        }

                    }
                } else {
                    common.showToast("Ticket not available.");
                }

            });
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if (realm != null) {
                realm.close();
            }
        }

    }

    public void getcurrentDate(TicketList ticketList) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        Date todayConvertedDate = null, startConvertedDate = null, todayWithZeroTime = null;
        try {

            Date date = Calendar.getInstance().getTime();
            String todayDateString = dateFormat.format(date);

            ticketList.setLastUpdateDate(todayDateString);


        } catch (ParseException  e) {
            e.printStackTrace();
        }

    }

    /**
     * get offline scan ticket data list and convert to csv
     */
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
                    getEventTicketList();
                }

            });
        } catch (Exception e) {
            getEventTicketList();
            e.printStackTrace();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

    }

    /*
     *  update offline ticket to server
     */
    public void updateTicketCheckedInApi(TicketRequest jsonObject) {

        LoginResponse loginResponse = ApplicationClass.getInstance().getLoginResult();

        Call<SubmitTicketResponse> eventResponse = APIClient.getApiService().updateTicketCheckedIn(loginResponse.getToken(), jsonObject);
        eventResponse.enqueue(new Callback<SubmitTicketResponse>() {
            @Override
            public void onResponse(Call<SubmitTicketResponse> call, Response<SubmitTicketResponse> response) {

                SubmitTicketResponse staticModel = response.body();
                if (staticModel.getStateCode() == 0 && staticModel.getState()) {
                    //common.showToast(staticModel.getSuccessCounts() + " ticket status updated.");
                }

            }

            @Override
            public void onFailure(Call<SubmitTicketResponse> call, Throwable t) {
                // loginAction();
            }
        });

    }

    /**
     *  reload event ticket list
     */
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


}
