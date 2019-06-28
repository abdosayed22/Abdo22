package com.aradwan054.hatly;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
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
    Button button;
    ImageView cust_image ;

    String latitude ;
    String longitude ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_delivery);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Requests");
        requestDeliveryList = new ArrayList<>();
        button = findViewById(R.id.test);
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
                    String id = dataSnapshot1.child("idemail").getValue().toString();
                    latitude=dataSnapshot1.child("Latitude").getValue().toString();
                    longitude=dataSnapshot1.child("Longitude").getValue().toString();

                    request_delivery Request = new request_delivery();
                    Request.setDescription(description);
                    Request.setPrice(price);
                    Request.setId(id);
                    Request.setLatitude(latitude);
                    Request.setLongitude(longitude);

                    requestDeliveryList.add(Request);
                }
                requestArrayAdapter = new RecyclerViewAdapterDelivery(requestDeliveryList, recycler_delivery.this);
                recyclerView.setAdapter(requestArrayAdapter);
             //   runAnim(recyclerView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
