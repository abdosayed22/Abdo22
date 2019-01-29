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

public class signup extends AppCompatActivity {
    EditText mail, pass, username, mobile, nationalNum, address;
    Button sign;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mail = findViewById(R.id.cust_mail_edit);
        pass = findViewById(R.id.cust_pass_edit);
        username = findViewById(R.id.cust_user_edit);
        mobile = findViewById(R.id.cust_phone_edit);
        nationalNum = findViewById(R.id.cust_idNum_edit);
        address = findViewById(R.id.cust_address_edit);
        sign = findViewById(R.id.cust_btn_sign);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Accounts");


    }

    public void Sign_up(View view) {
        try {


            if (mail.getText().toString().equals("")) mail.setError("Please Enter Your mail");
            if (pass.getText().toString().equals("")) pass.setError("Please Enter your Password");
            if (username.getText().toString().equals("")) username.setError("Please Enter your UserName");
            if (mobile.getText().toString().equals("")) mobile.setError("Please Enter your mobile number");
            if (nationalNum.getText().toString().equals("")) nationalNum.setError("Please Enter your national number ");
            if (address.getText().toString().equals("")) address.setError("Please Enter your address");

            mAuth.createUserWithEmailAndPassword(mail.getText().toString(), pass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful())
                                Toast.makeText(signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                     else {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("e-mail", mail.getText().toString());
                                map.put("Password", pass.getText().toString());
                                map.put("Phone", mobile.getText().toString());
                                map.put("UserName", username.getText().toString());
                                map.put("national-number", nationalNum.getText().toString());
                                map.put("address", address.getText().toString());
                                myRef.push().setValue(map);

                                // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(signup.this, "Account Created", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Order.class);
                                intent.putExtra("e-mail", mail.getText().toString());
                                intent.putExtra("Password", pass.getText().toString());
                                intent.putExtra("Phone", mobile.getText().toString());
                                intent.putExtra("UserName", username.getText().toString());
                                intent.putExtra("address", address.getText().toString());
                                intent.putExtra("national-number", nationalNum.getText().toString());

                                startActivity(intent);
                                mail.setText("");
                                pass.setText("");
                                mobile.setText("");
                                username.setText("");
                                address.setText("");
                                nationalNum.setText("");}
                    // ...
                }
            });


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
