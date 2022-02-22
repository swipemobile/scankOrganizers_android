package com.scank.organizer.ui.guestlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.scank.organizer.R;
import com.scank.organizer.Utility.Common;
import com.scank.organizer.interfaces.itemClickListener;
import com.scank.organizer.model.GuestData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.ViewHolder> implements Filterable {

    private itemClickListener itemClickListener;
    List<GuestData> guestDataList = new ArrayList<>();
    List<GuestData> searchDataList = new ArrayList<>();

    Context context;
    Common common;

    public GuestListAdapter(Context context, List<GuestData> guestDataList) {
        this.guestDataList = guestDataList;
        searchDataList = guestDataList;
        this.context = context;
        common = Common.getInstance();
    }

    public void setGuestDataList(List<GuestData> guestDataList) {
        this.guestDataList = guestDataList;
    }

    public void setItemClickListener(itemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.item_guest_list, parent, false));
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        GuestData eventData = searchDataList.get(position);

        holder.tvTitle.setText(eventData.getName());
        holder.tvTicketNumber.setText("" + eventData.getId());
        holder.tvOrder.setText(String.format("%d", eventData.getOrderNumber()));

        holder.tvTicketCount.setText("x 1 " + eventData.getTicket());

        if((position + 1) == guestDataList.size()) {
            holder.viewDivider.setVisibility(View.GONE);
        } else {
            holder.viewDivider.setVisibility(View.VISIBLE);
        }

        try {
            String convertedDate =   formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss", "hh:mm a", eventData.getLastUpdateDate());
            holder.tvOrderTime.setText(convertedDate);

        } catch (android.net.ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        if (eventData.getCheckIn()) {
            holder.btnCheckedIn.setVisibility(View.VISIBLE);
            holder.tvOrderTime.setVisibility(View.VISIBLE);
        } else {
            holder.btnCheckedIn.setVisibility(View.GONE);
            holder.tvOrderTime.setVisibility(View.GONE);
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return searchDataList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults result = new FilterResults();
                final ArrayList<GuestData> results = new ArrayList<GuestData>();
                if (searchDataList == null || searchDataList.size() == 0)
                    searchDataList = guestDataList;
                if (constraint != null) {
                    if (searchDataList != null && searchDataList.size() > 0) {
                        for (final GuestData g : searchDataList) {
                            if (g.getName().toLowerCase().contains(constraint.toString()) ||
                                    g.getOrderNumber().toString().contains(constraint.toString()) ||
                                    g.getId().toString().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    result.values = results;
                }
                return result;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {

                if(constraint != null) {
                    searchDataList = (ArrayList<GuestData>) results.values;
                } else {
                    searchDataList = guestDataList;
                }
                notifyDataSetChanged();
            }
        };

    }


    public static String formatDateFromDateString(String inputDateFormat, String outputDateFormat,
                                                  String inputDate) throws ParseException {
        String mOutputDateString;

        Date date = new SimpleDateFormat(inputDateFormat).parse(inputDate);
        mOutputDateString = new SimpleDateFormat(outputDateFormat).format(date);

        return mOutputDateString;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle, tvTicketCount, tvTicketNumber, tvOrder, tvOrderTime;
        private AppCompatButton btnCheckedIn;
        private View viewDivider;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTicketCount = itemView.findViewById(R.id.tvTicketCount);
            tvTicketNumber = itemView.findViewById(R.id.tvTicketNumber);
            tvOrder = itemView.findViewById(R.id.tvOrder);
            tvOrderTime = itemView.findViewById(R.id.tvOrderTime);

            viewDivider = itemView.findViewById(R.id.viewDivider);
            btnCheckedIn = itemView.findViewById(R.id.btnCheckedIn);
            // btnCheckedIn.setOnClickListener(this);
         }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.btnCheckedIn) {

                if (itemClickListener != null) {
                    itemClickListener.itemClick(guestDataList.get(getLayoutPosition()), 1);
                }
            }
        }
    }

}