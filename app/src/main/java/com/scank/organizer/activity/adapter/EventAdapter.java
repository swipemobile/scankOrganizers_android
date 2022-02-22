package com.scank.organizer.activity.adapter;

import android.content.Context;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.scank.organizer.R;
import com.scank.organizer.Utility.Common;
import com.scank.organizer.interfaces.itemClickListener;
import com.scank.organizer.model.EventData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private itemClickListener itemClickListener;
    List<EventData> eventDataArrayList = new ArrayList<>();

    int selectedPos = -1;
    Context context;

    private Common common;

    public EventAdapter(Context context, List<EventData> eventList) {
        eventDataArrayList = eventList;
        this.context = context;
        common = Common.getInstance();
    }

    public void setItemClickListener(itemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setEventDataArrayList(List<EventData> eventDataArrayList) {
        this.eventDataArrayList = eventDataArrayList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the custom layout
        View listItem = layoutInflater.inflate(R.layout.item_event_list, parent, false);
        return new ViewHolder(listItem);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        EventData eventData = eventDataArrayList.get(position);

        if(eventData != null) {
            if(!common.isStringEmpty(eventData.getSales()) && !common.isStringEmpty(eventData.getTicketCount())) {
                int per = (Integer.parseInt(eventData.getSales()) * 100) / Integer.parseInt(eventData.getTicketCount());
                holder.tvSoldPer.setText("" + per + "%");
                holder.seekbar.setProgress(per);
            }

            Glide.with(context)
                    .load(eventData.getCoverPicture()) // image url
                    .override(200, 200) // resizing
                    .into(holder.ivEventImage);

            holder.tvTitle.setText(eventData.getTitle());
            holder.tvDate.setText(eventData.getStartDateStr());

            int totalDays = getCountOfDays(eventData.getStartDate());
            holder.tvRemainingDays.setText(String.valueOf(totalDays));

            holder.tvCompletedEvents.setText(String.valueOf(eventData.getSales()));
            holder.tvTotalEvents.setText(new StringBuilder().append("/ ").append(eventData.getTicketCount()));

            if (selectedPos != -1 && selectedPos == position) {
                holder.llImageBg.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                holder.llBottomButtons.setVisibility(View.VISIBLE);
                holder.ivBracket.setRotation(0);
            } else {
                holder.llImageBg.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                holder.llBottomButtons.setVisibility(View.GONE);
                holder.ivBracket.setRotation(180);
            }
        }

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return eventDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AppCompatImageView ivEventImage, ivBracket;
        public TextView tvTitle, tvDate, tvRemainingDays, tvTotalEvents, tvCompletedEvents, tvSoldPer;
        public AppCompatSeekBar seekbar;
        public LinearLayout llBracket, llBottomButtons, llImageBg;
        private AppCompatButton btnReports, btnGuestList, btnManage;
        private RelativeLayout rlMainEvents;

        public ViewHolder(View itemView) {
            super(itemView);

            ivEventImage = itemView.findViewById(R.id.ivEventImage);
            ivBracket = itemView.findViewById(R.id.ivBracket);

            rlMainEvents = itemView.findViewById(R.id.rlMainEvents);

            tvSoldPer = itemView.findViewById(R.id.tvSoldPer);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCompletedEvents = itemView.findViewById(R.id.tvCompletedEvents);
            tvTotalEvents = itemView.findViewById(R.id.tvTotalEvents);
            tvRemainingDays = itemView.findViewById(R.id.tvRemainingDays);
            llBracket = itemView.findViewById(R.id.llBracket);
            llBottomButtons = itemView.findViewById(R.id.llBottomButtons);
            llImageBg = itemView.findViewById(R.id.llImageBg);

            btnReports = itemView.findViewById(R.id.btnReports);
            btnGuestList = itemView.findViewById(R.id.btnGuestList);
            btnManage = itemView.findViewById(R.id.btnManage);

            seekbar = itemView.findViewById(R.id.seekbar);

            btnReports.setOnClickListener(this);
            btnGuestList.setOnClickListener(this);
            btnManage.setOnClickListener(this);
            rlMainEvents.setOnClickListener(this);
            llBracket.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            int position = getLayoutPosition();

            if (id == R.id.btnReports) {

                if (itemClickListener != null) {
                    itemClickListener.itemClick(eventDataArrayList.get(getLayoutPosition()), 1);
                }
            } else if (id == R.id.btnGuestList) {

                if (itemClickListener != null) {
                    itemClickListener.itemClick(eventDataArrayList.get(getLayoutPosition()), 2);
                }

            } else if (id == R.id.btnManage) {

                if (itemClickListener != null) {
                    itemClickListener.itemClick(eventDataArrayList.get(getLayoutPosition()), 3);
                }

            } else if(id == R.id.rlMainEvents || id == R.id.llBracket) {
                if (selectedPos == -1) {
                    selectedPos = position;
                } else if (selectedPos == position) {
                    selectedPos = -1;
                } else {
                    selectedPos = position;
                }
                notifyDataSetChanged();
            }
        }
    }

    public int getCountOfDays(String startEventDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        Date todayConvertedDate = null, startConvertedDate = null, todayWithZeroTime = null;
        try {

            Date date = Calendar.getInstance().getTime();
            String todayDateString = dateFormat.format(date);

            startConvertedDate = dateFormat.parse(startEventDate);
            todayConvertedDate = dateFormat.parse(todayDateString);


        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        //milliseconds
        long different = startConvertedDate.getTime() - todayConvertedDate.getTime();

        System.out.println("startDate : " + todayConvertedDate);
        System.out.println("endDate : " + startConvertedDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        return (int) elapsedDays;

    }
}