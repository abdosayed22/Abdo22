package com.aradwan054.hatly;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Order extends AppCompatActivity implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    EditText desc, price;
    boolean b = false;
    String loc;
    String Latitude;
    String Longitude;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    Button order;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);
        desc = findViewById(R.id.desc_request);
        price = findViewById(R.id.price_request);
        order = findViewById(R.id.order_btn);
        pd = new ProgressDialog(this);

      mGoogleApiClient=new GoogleApiClient.Builder(this)
              .addApi(LocationServices.API)
              .addConnectionCallbacks(this)
              .addOnConnectionFailedListener(this).build();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Requests");
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onStart() {

        super.onStart();
        mGoogleApiClient.connect();


    }

    public void order(View view) {

       // if(Latitude.length() == 0 && Longitude.length() == 0)
       // {
           // pd.setMessage("Waiting");
          //  pd.show();

        //}
      //  else {
         //   pd.dismiss();

            try {

                if (desc.getText().toString().equals("")) desc.setError("Please Enter your request");
                if (price.getText().toString().equals("")) {
                    price.setError("Please Enter price you want");

                } else {


                    Map<String, String> map = new HashMap<String, String>();
                    map.put("request-description", desc.getText().toString());
                    map.put("price", price.getText().toString());



                    map.put("Latitude", Latitude);
                    map.put("Longitude", Longitude);

                    myRef.push().setValue(map);
                    Toast.makeText(Order.this, "Your order is published", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Recycler.class);
                    intent.putExtra("request-disc", desc.getText().toString());
                    intent.putExtra("price", price.getText().toString());
                    startActivity(intent);
                    desc.setText("");
                    price.setText("");
                }
                // ...
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
   // }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10); // Update location every second


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        Toast.makeText(this,"onConnected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if (b==false) {
            Latitude = String.valueOf(location.getLatitude());
            Longitude = String.valueOf(location.getLongitude());
            b=true ;
        }
    }


}
