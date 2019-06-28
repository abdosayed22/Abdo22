package com.aradwan054.hatly;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Navigation extends AppCompatActivity implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    EditText desc, price;
    boolean b = false;
    ImageView nav_img;
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
    String idemail;
    LinearLayout linearLayout ;

    LocationManager locationManager;
    String provider;
    private DrawerLayout mdrawer;
    private ActionBarDrawerToggle mtoggle;
    Context context ;
    String   snav_img , sname;//al5mis

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = Navigation.this;

         linearLayout = findViewById(R.id.order_linear);
        setContentView(R.layout.activity_navigation);
        desc = findViewById(R.id.desc_request);
        price = findViewById(R.id.price_request);
        order = findViewById(R.id.order_btn);
        pd = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Requests");
        // email = getIntent().getStringExtra("email");
        mdrawer = findViewById(R.id.act_main);
        mtoggle = new ActionBarDrawerToggle(this, mdrawer, R.string.Open, R.string.Close);
        mdrawer.addDrawerListener(mtoggle);
        mtoggle.syncState();
        NavigationView NV = findViewById(R.id.navigation);
      //  Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        SetupDrawerContent(NV);

        checkLocationPermission();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        View hView =  NV.inflateHeaderView(R.layout.header);
        final ImageView imgvw = (ImageView)hView.findViewById(R.id.nav_img);
        final TextView textname = (TextView) hView.findViewById(R.id.textname);//al5mis


        FirebaseDatabase database;
        FirebaseAuth mAuth;
        DatabaseReference myRef;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef = database.getReference("Accounts");



        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                snav_img = dataSnapshot.child("imgURI").getValue(String.class);
                sname = dataSnapshot.child("UserName").getValue(String.class);//al5mis



                Glide.with(context).load(snav_img).into(imgvw);
                textname.setText(sname);





            }


            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });



    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onStart() {

        super.onStart();



    }

    public void order(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        // if(Latitude.length() == 0 && Longitude.length() == 0)
        // {
        // pd.setMessage("Waiting");
        //  pd.show();

        //}
        //  else {
        //   pd.dismiss();

        try {

            if (desc.getText().toString().equals("") || price.getText().toString().equals("") ) {
                desc.setError("Please Enter your request");
                price.setError("Please Enter price you want");
            }

             else {

                if (b == false) //t7t altagroba al5mis
                {   progressDialog.setProgressStyle(R.drawable.card_back);
                    progressDialog.setTitle("incomplete Data");
                    progressDialog.setMessage("wait to get your location please turn the permassion location on and turn on the gps !!");
                    progressDialog.show();
                    return;}

                idemail = FirebaseAuth.getInstance().getCurrentUser().getUid();


                Map<String, String> map = new HashMap<String, String>();
                map.put("request-description", desc.getText().toString());
                map.put("price", price.getText().toString());
                map.put("Latitude", Latitude);
                map.put("Longitude", Longitude);
                map.put("idemail", idemail);

                myRef.push().setValue(map);
                Toast.makeText(Navigation.this, "Your order is published", Toast.LENGTH_SHORT).show();
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
        mLocationRequest.setInterval(1); // Update location every second


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


        Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
        pd.setMessage("Waiting!! for Getting your Location");
        pd.show();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {


        if (b == false) {

            Latitude = String.valueOf(location.getLatitude());
            Longitude = String.valueOf(location.getLongitude());
            b = true;
            pd.dismiss();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("LOAD")
                        .setMessage("SSSSSSS")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Navigation.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) Navigation.this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }



    public void SelectItemDrawer(MenuItem menuItem) {
        Fragment MyFrag = null;
        Class fragmentclass;
        switch (menuItem.getItemId()) {
            case R.id.profile:
                fragmentclass = Profile_Frag.class;
                UnVisible();
                break;

                case R.id.About:
                fragmentclass = About_Frag.class;
                UnVisible();
                break;

            case R.id.Setting:
                fragmentclass = Setting_Frag.class;
                UnVisible();
                break;

            default:
                fragmentclass = Navigation.class;
        }
        try {
            MyFrag = (Fragment) fragmentclass.newInstance();
        } catch (Exception e) {
            e.getStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent, MyFrag).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mdrawer.closeDrawers();

    }

    private void SetupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                SelectItemDrawer(item);
                return true;
            }
        });
    }
    public void UnVisible(){
      //  linearLayout.setVisibility(View.GONE);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Navigation.this , signup.class);
        startActivity(intent);

    }
}
