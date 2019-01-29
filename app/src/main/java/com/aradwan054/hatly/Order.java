package com.aradwan054.hatly;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Order extends AppCompatActivity {
EditText desc,price;
Button order,setLoction;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        desc=findViewById(R.id.desc_request);
        price=findViewById(R.id.price_request);
        order=findViewById(R.id.order_btn);
        setLoction=findViewById(R.id.location_btn);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Requests");
    }

    public void order(View view) {
        try {
            if (desc.getText().toString().equals("")) desc.setError("Please Enter your request");
            if (price.getText().toString().equals("")) price.setError("Please Enter price you want");

            Map<String, String> map = new HashMap<String, String>();
            map.put("request-description", desc.getText().toString());
            map.put("price", price.getText().toString());
            myRef.push().setValue(map);
            Toast.makeText(Order.this, "Your order is published", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Recycler.class);
            intent.putExtra("request-disc", desc.getText().toString());
            intent.putExtra("price", price.getText().toString());
            startActivity(intent);
            desc.setText("");
            price.setText("");
            // ...

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
