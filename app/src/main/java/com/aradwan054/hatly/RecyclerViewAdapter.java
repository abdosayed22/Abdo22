package com.aradwan054.hatly;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<request> requestList;
    private Activity activity;

    public RecyclerViewAdapter(ArrayList<request> movieList, Context context) {
        this.requestList = movieList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        final request request = requestList.get(i);
        holder.description_edt.setText(request.getDescription());
        holder.price_edt.setText(request.getPrice() + "");
    }

    @Override
    public int getItemCount() {
        if (requestList == null) return 0;
        return requestList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView description_edt, price_edt;
        public MyViewHolder(View itemView) {
            super(itemView);
            description_edt = (TextView) itemView.findViewById(R.id.des_txt);
            price_edt = (TextView) itemView.findViewById(R.id.price_txt);

        }
    }
}
