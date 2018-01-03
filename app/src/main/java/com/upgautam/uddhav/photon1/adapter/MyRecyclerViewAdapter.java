package com.upgautam.uddhav.photon1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.upgautam.uddhav.photon1.R;
import com.upgautam.uddhav.photon1.model.IssData;

import java.util.Collections;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "MyRecyclerViewAdapter";
    public static List<IssData> mIssDataList = Collections.emptyList(); //returns immutable empty list
    IssData issData = new IssData();
    Context context;

    public MyRecyclerViewAdapter(List<IssData> issDataList, Context context) {
        this.context = context;
        this.mIssDataList = issDataList;
    }


    public static List<IssData> getmIssDataList() {
        return mIssDataList;
    }

    public static void setmIssDataList(List<IssData> mIssDataList) {
        MyRecyclerViewAdapter.mIssDataList = mIssDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerview, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, IssData data) {
        mIssDataList.add(position, data);
        notifyItemInserted(position);
    }


    public void update(int position, IssData data) {
        mIssDataList.remove(position);
        notifyItemRemoved(position);

        insert(position, data);
    }

    public IssData getItemFromPredefinedPosition(int position) {
        return mIssDataList.get(position);
    }

    public void remove(IssData data) {
        int position = mIssDataList.indexOf(data);
        mIssDataList.remove(position);
        notifyItemRemoved(position);
    }

    public void remove() {
        mIssDataList.remove(0);
        notifyItemRemoved(0);
    }

    @Override
    public int getItemCount() {
        return mIssDataList.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder { //can be as separate class
        public static EditText durationEditText;
        public static EditText timeEditText;

        private IssData issData = new IssData();


        public MyViewHolder(View v) {
            super(v);
            durationEditText = v.findViewById(R.id.duration_edittext);
            timeEditText = v.findViewById(R.id.time_edittext);

            durationEditText.setFocusable(false);
            timeEditText.setClickable(false);

            durationEditText.setFocusable(false);
            timeEditText.setClickable(false);


        }


        public void updateDurationAndTimeBoth() {
            int lastValue = mIssDataList.size() - 1;
            durationEditText.setText("   " + mIssDataList.get(lastValue).getmDuration() + "");
            timeEditText.setText("   " + mIssDataList.get(lastValue).getmTime() + "");

        }
    }

}