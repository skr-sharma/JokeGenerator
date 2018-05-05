package com.icndb.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Model.JokesBean;
import com.icndb.joke.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<JokesBean> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txJokesDesc;
        LinearLayout layoutMain;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txJokesDesc = (TextView) itemView.findViewById(R.id.txJokesDesc);
            this.layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutMain);
        }
    }

    public CustomAdapter(ArrayList<JokesBean> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        holder.txJokesDesc.setText(Html.fromHtml(dataSet.get(listPosition).getJokes()));
        //holder.layoutMain.setBackgroundColor(Color.parseColor("#" + dataSet.get(listPosition).getRatingColor()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
