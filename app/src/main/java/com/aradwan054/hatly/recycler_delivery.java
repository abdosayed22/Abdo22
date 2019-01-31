package com.aradwan054.hatly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class recycler_delivery extends AppCompatActivity {
    ArrayList<request_delivery> requestDeliveryList;
    RecyclerViewAdapterDelivery requestArrayAdapter;
    private RecyclerView recyclerView;

    String latitude ;
    String longitude ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_delivery);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Requests");
        requestDeliveryList = new ArrayList<>();
        recyclerView=findViewById(R.id.recycler_delivery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String price = dataSnapshot1.child("price").getValue().toString();
                    String description = dataSnapshot1.child("request-description").getValue().toString();
                   //  latitude=dataSnapshot1.child("Latitude").getValue().toString();
                    // longitude=dataSnapshot1.child("Longitude").getValue().toString();
                    request_delivery Request = new request_delivery();
                    Request.setDescription(description);
                    Request.setPrice(price);
                    requestDeliveryList.add(Request);
                }
                requestArrayAdapter = new RecyclerViewAdapterDelivery(requestDeliveryList, recycler_delivery.this);
                recyclerView.setAdapter(requestArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
