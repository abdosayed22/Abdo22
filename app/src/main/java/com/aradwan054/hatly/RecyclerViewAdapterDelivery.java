package com.aradwan054.hatly;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerViewAdapterDelivery extends RecyclerView.Adapter<RecyclerViewAdapterDelivery.MyViewHolder> {
    private ArrayList<request_delivery> requestDeliveryList;
    Context context;
    Dialog mydialog;
    String snav_img ;

    public RecyclerViewAdapterDelivery(ArrayList<request_delivery> requestDeliveryList, Context context) {
        this.requestDeliveryList = requestDeliveryList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_delivery, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        mydialog = new Dialog(context);
        mydialog.setContentView(R.layout.order_dialog);

        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.order_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TextView dialog_description_txt =  mydialog.findViewById(R.id.dialogue_desc_txt);
                    TextView dialog_price_txt =  mydialog.findViewById(R.id.dialogue_price_txt);
                    TextView dialog_fare_txt =  mydialog.findViewById(R.id.dialogue_fare_txt);
                    dialog_description_txt.setText(requestDeliveryList.get(viewHolder.getAdapterPosition()).getDescription());
                    dialog_price_txt.setText(requestDeliveryList.get(viewHolder.getAdapterPosition()).getPrice());
                    dialog_fare_txt.setText(requestDeliveryList.get(viewHolder.getAdapterPosition()).getPrice());


                    //
                    Button dialog_location_btn =  mydialog.findViewById(R.id.dialogue_location_btn);
                    Button dialog_request_btn =  mydialog.findViewById(R.id.dialogue_request_btn);
                    dialog_location_btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            Toast.makeText(context,"We Will Make A Call At A Moment ...",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog_request_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context,"We Will Send A Message At A Moment ...",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(context,"Test Click"+String.valueOf(viewHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                    mydialog.show();
                }catch (Exception e){
                    Toast.makeText(context,e.getMessage()+ "     E error",Toast.LENGTH_LONG).show();

                }
            }


        });

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapterDelivery.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final request_delivery request = requestDeliveryList.get(position);
        holder.description.setText(request.getDescription());
        holder.customer_rate.setText(request.getPrice() );
        //Glide.with(context).load(request.getImage()).into(holder.customer_img);
        final String x = request.getLatitude();
        final String y = request.getLongitude();
      final  String id = request.getId();

        FirebaseDatabase database;
        FirebaseAuth mAuth;
        DatabaseReference myRef;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        myRef = database.getReference("Accounts");
        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                snav_img = dataSnapshot.child("imgURI").getValue(String.class);
                String suserName = dataSnapshot.child("UserName").getValue(String.class);//al5mis

                Glide.with(context).load(snav_img).into(holder.customer_img);
                holder.customer_name.setText(suserName);//al5mis





            }


            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        holder.customer_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.order_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,x + " && " + y,Toast.LENGTH_LONG).show();

             /*   Intent intent = new Intent(context ,  MapsActivity.class) ;
               intent.putExtra("latitude" , x );
                intent.putExtra("longitude" , y);
               context.startActivity(intent); */

                TextView dialog_description_txt =  mydialog.findViewById(R.id.dialogue_desc_txt);
                TextView dialog_price_txt =  mydialog.findViewById(R.id.dialogue_price_txt);
                TextView dialog_fare_txt =  mydialog.findViewById(R.id.dialogue_fare_txt);
                dialog_description_txt.setText(requestDeliveryList.get(position).getDescription());
                dialog_price_txt.setText(requestDeliveryList.get(position).getPrice());
                dialog_fare_txt.setText(requestDeliveryList.get(position).getPrice());

                Button dialog_location_btn =  mydialog.findViewById(R.id.dialogue_location_btn);
                Button dialog_request_btn =  mydialog.findViewById(R.id.dialogue_request_btn);
                dialog_location_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(context ,  MapsActivity.class) ;
                        intent.putExtra("latitude" , x );
                        intent.putExtra("longitude" , y);
                        context.startActivity(intent);

                        //   Toast.makeText(context,"We Will Make A Call At A Moment ...",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog_request_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Notification has been sent",Toast.LENGTH_SHORT).show();
                    }
                });
                //   Toast.makeText(context,"Test Click"+String.valueOf(requestDeliveryList.get(position)),Toast.LENGTH_SHORT).show();
                mydialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (requestDeliveryList == null) return 0;
        return requestDeliveryList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //   TextView description_edt, price_edt;
        //   Button getlocation;

        Button order_info; // Karim
        TextView description,customer_name,customer_rate; // Karim
        ImageView customer_img; // Karim

        public MyViewHolder(View itemView) {
            super(itemView);

            //  description_edt = (TextView) itemView.findViewById(R.id.desTxtDelivery);
            //  price_edt = (TextView) itemView.findViewById(R.id.priceTxtDelivery);
            // getlocation = (Button) itemView.findViewById(R.id.locationBtnDelivery);

            description = itemView.findViewById(R.id.desc_txt); // Karim
            customer_name = itemView.findViewById(R.id.cust_name); // Karim
            customer_rate = itemView.findViewById(R.id.cust_rate); // Karim
            order_info = itemView.findViewById(R.id.btn_info); // Karim
            customer_img = itemView.findViewById(R.id.cust_img); // Karim

        }
    }
}