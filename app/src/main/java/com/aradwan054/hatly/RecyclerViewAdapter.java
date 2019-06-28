package com.aradwan054.hatly;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<request> requestList;
    private Activity activity;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Requests");
    Dialog mydialog;

    public RecyclerViewAdapter(ArrayList<request> movieList, Context context) {
        this.requestList = movieList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mydialog = new Dialog(context);
        mydialog.setContentView(R.layout.edit_request_dialog);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        final MyViewHolder viewHolder=new MyViewHolder(view);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final request request = requestList.get(position);
        holder.description_edt.setText(request.getDescription());
        holder.price_edt.setText(request.getPrice() + "");


        final String id = request.getId();


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 ref.child(id).removeValue();
                 requestList.remove(position);
                 notifyDataSetChanged();

            }
        });





        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText update_desc=mydialog.findViewById(R.id.editDescription);
                final EditText update_price=mydialog.findViewById(R.id.editPrice);
                final Button update=mydialog.findViewById(R.id.doneEdit);


               update_desc.setText(requestList.get(position).getDescription());
               update_price.setText(requestList.get(position).getPrice());

               update.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       ref.child(id).child("request-description").setValue(update_desc.getText().toString());
                       ref.child(id).child("price").setValue(update_price.getText().toString());
                       requestList.get(position).setPrice(update_price.getText().toString());
                       requestList.get(position).setDescription(update_desc.getText().toString());
                       notifyDataSetChanged();



                   }
               });
                mydialog.show();

            }
        });




    }

    @Override
    public int getItemCount() {
        if (requestList == null) return 0;
        return requestList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button delete;
        Button edit;
        TextView description_edt, price_edt;

        public MyViewHolder(View itemView) {
            super(itemView);
            description_edt = (TextView) itemView.findViewById(R.id.des_txt);
            price_edt = (TextView) itemView.findViewById(R.id.price_txt);
            delete = itemView.findViewById(R.id.delBtn);
            edit = itemView.findViewById(R.id.edtbtn);

        }
    }
}
