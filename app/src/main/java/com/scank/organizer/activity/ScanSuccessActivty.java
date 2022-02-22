package com.scank.organizer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.scank.organizer.R;
import com.scank.organizer.Utility.Constants;
import com.scank.organizer.model.TicketDetails;

public class ScanSuccessActivty extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton btnOk;
    TextView tvName, tvTicketNumber;
    private TicketDetails ticketDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_success_activity);

        init();
        process();
        setListener();
    }

    public void init() {

        tvName = findViewById(R.id.tvName);
        tvTicketNumber = findViewById(R.id.tvTicketNumber);
        btnOk = findViewById(R.id.btnOk);

    }

    public void process() {
        Intent intent = getIntent();
        ticketDetails = intent.getParcelableExtra(Constants.INTENT_KEY.TICKET_DATA);

        tvName.setText(ticketDetails.getName());
        tvTicketNumber.setText("" +ticketDetails.getId());

    }

    public void setListener() {
        btnOk.setOnClickListener(this);
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


    @Override
    public void onClick(View view) {
        int id  = view.getId();
        if(id == R.id.btnOk) {
            setResult(RESULT_OK);
            onBackPressed();
        }
    }


}
