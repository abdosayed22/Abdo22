package com.aradwan054.hatly;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterDelivery extends RecyclerView.Adapter<RecyclerViewAdapterDelivery.MyViewHolder>{
    private ArrayList<request_delivery> requestDeliveryList;
Context context;

    public RecyclerViewAdapterDelivery(ArrayList<request_delivery> requestDeliveryList, Context context) {
        this.requestDeliveryList = requestDeliveryList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterDelivery.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_delivery, parent, false);
        return new MyViewHolder(view);    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterDelivery.MyViewHolder holder, int i) {
        final request_delivery request = requestDeliveryList.get(i);
        holder.description_edt.setText(request.getDescription());
        holder.price_edt.setText(request.getPrice() + "");

    }

    @Override
    public int getItemCount() {
        if (requestDeliveryList == null) return 0;
        return requestDeliveryList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView description_edt, price_edt;
        public MyViewHolder(View itemView) {
            super(itemView);
            description_edt = (TextView) itemView.findViewById(R.id.desTxtDelivery);
            price_edt = (TextView) itemView.findViewById(R.id.priceTxtDelivery);

        }
    }
}
