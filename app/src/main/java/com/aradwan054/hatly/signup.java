package com.aradwan054.hatly;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity  {
    EditText mail, pass, username, mobile, nationalNum, address;
    Button sign;
    TextView login_txt;
    FirebaseDatabase database;
    DatabaseReference myRef;
    RadioButton male, female;
    private FirebaseAuth mAuth;
    Boolean DataFalse = false;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // initialize Views

        mail = findViewById(R.id.sign_email_edt);
        pass = findViewById(R.id.sign_pass_edt);
        username = findViewById(R.id.sign_user_edt);
        mobile = findViewById(R.id.sign_phone_edit);
        nationalNum = findViewById(R.id.sign_idcard_edt);
        address = findViewById(R.id.sign_address_edt);
        sign = findViewById(R.id.SignUp_btn);
        login_txt = findViewById(R.id.signin_txt);
        male = findViewById(R.id.rdb_male);
        female = findViewById(R.id.rdb_female);

        final ProgressDialog progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Accounts");

        ClearData(this);
        male.setEnabled(false);
        female.setEnabled(false);

        CheckData(this);

        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this,login.class);
                startActivity(intent);
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (mail.getText().toString().equals("")) mail.setError("Please Enter Your mail");
                    if (pass.getText().toString().equals("")) pass.setError("Please Enter your Password");
                    if (username.getText().toString().equals("")) username.setError("Please Enter your UserName");
                    if (mobile.getText().toString().equals("")) mobile.setError("Please Enter your mobile number");
                    if (nationalNum.getText().toString().equals("")) nationalNum.setError("Please Enter your national number ");
                    if (address.getText().toString().equals("")) address.setError("Please Enter your address");
                    if (mail.getText().toString().equals("") || pass.getText().toString().equals("") || username.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("") ) {
                        Toast.makeText(signup.this, "Please Fill Your Empty Data !", Toast.LENGTH_LONG).show();

                    }
                    //  male.setEnabled(false);
                    // female.setEnabled(false);


                    CheckData(signup.this);

                    if (DataFalse == true){
                        progressDialog.setProgressStyle(R.drawable.card_back);
                        progressDialog.setTitle("incomplete Data");
                        progressDialog.setMessage("Please Insert Correct Data !!");
                        progressDialog.show();

                    }else {
                        mAuth.createUserWithEmailAndPassword(mail.getText().toString(), pass.getText().toString())
                                .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
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
                                            if(male.isChecked()){
                                                map.put("Gender","Male");

                                            }else
                                                map.put("Gender","Female");

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
                    }




                } catch (Exception e) {
                    //  Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void ClearData(Context context){
        mail.setText("");
        pass.setText("");
        mobile.setText("");
        username.setText("");
        address.setText("");
        nationalNum.setText("");
        male.setChecked(false);
        female.setChecked(false);
    }

    public void CheckData(Context context){
        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( pass.getText().toString().equals("") || username.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( pass.getText().toString().equals("") || username.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    male.setEnabled(true);
                    female.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(mail.getText().toString().equals("")){
                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    if (!mail.getText().toString().contains(".com") && !mail.getText().toString().contains("@")){
                        mail.setError("Please Put a real account");
                        DataFalse = true;
                    }else {
                        DataFalse = false;
                    }
                }
                if ( pass.getText().toString().equals("") || username.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    male.setEnabled(true);
                    female.setEnabled(true);
                }
            }

        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( mail.getText().toString().equals("") || pass.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( mail.getText().toString().equals("") || pass.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    male.setEnabled(true);
                    female.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(username.getText().toString().equals("")){
                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    if ( mail.getText().toString().equals("") || pass.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("") ) {
                        male.setEnabled(false);
                        female.setEnabled(false);
                    }else {
                        male.setEnabled(true);
                        female.setEnabled(true);
                    }
                }
            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( username.getText().toString().equals("") || mail.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( username.getText().toString().equals("") || mail.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    male.setEnabled(true);
                    female.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(pass.getText().toString().equals("")){
                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    if (pass.getText().toString().length() < 6){
                        pass.setError("Password should be 6 characters at least");
                        DataFalse = true;
                    }else {
                        if (username.getText().toString().equals("") || mail.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("")) {
                            DataFalse = true;
                            male.setEnabled(false);
                            female.setEnabled(false);
                        } else {
                            DataFalse = false ;
                            male.setEnabled(true);
                            female.setEnabled(true);
                        }

                    }

                }
            }
        });

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( mail.getText().toString().equals("") || username.getText().toString().equals("") || pass.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( mail.getText().toString().equals("") || username.getText().toString().equals("") || pass.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    male.setEnabled(true);
                    female.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(mobile.getText().toString().equals("")){
                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    if (!mobile.getText().toString().startsWith("01")) {
                        mobile.setError("Please Put a real Phone Number");
                        DataFalse = true;
                    } else {

                        if (mail.getText().toString().equals("") || username.getText().toString().equals("") || pass.getText().toString().equals("") || nationalNum.getText().toString().equals("") || address.getText().toString().equals("")) {
                            DataFalse = true;
                            male.setEnabled(false);
                            female.setEnabled(false);
                        } else {
                            DataFalse = false;
                            male.setEnabled(true);
                            female.setEnabled(true);
                        }
                    }
                }
            }
        });

        nationalNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( mail.getText().toString().equals("") || username.getText().toString().equals("") || pass.getText().toString().equals("") || mobile.getText().toString().equals("") || address.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( mail.getText().toString().equals("") || username.getText().toString().equals("") || pass.getText().toString().equals("") || mobile.getText().toString().equals("") || address.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                } else {
                    male.setEnabled(true);
                    female.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(nationalNum.getText().toString().equals("")){
                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    if (nationalNum.getText().toString().length() < 14) {
                        nationalNum.setError("ID Card should be 14 characters at least");
                        DataFalse = true;
                    }
                    if ( mail.getText().toString().equals("") || username.getText().toString().equals("") || pass.getText().toString().equals("") || mobile.getText().toString().equals("") || address.getText().toString().equals("") ) {
                        DataFalse = true;
                        male.setEnabled(false);
                        female.setEnabled(false);
                    }else {
                        DataFalse = false;
                        male.setEnabled(true);
                        female.setEnabled(true);
                    }
                }
            }
        });

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( mail.getText().toString().equals("") || username.getText().toString().equals("") || pass.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( mail.getText().toString().equals("") || username.getText().toString().equals("") || pass.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") ) {

                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    male.setEnabled(true);
                    female.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(address.getText().toString().equals("")){
                    male.setEnabled(false);
                    female.setEnabled(false);
                }else {
                    if ( mail.getText().toString().equals("") || username.getText().toString().equals("") || pass.getText().toString().equals("") || mobile.getText().toString().equals("") || nationalNum.getText().toString().equals("") ) {
                        male.setEnabled(false);
                        female.setEnabled(false);
                    }else {
                        male.setEnabled(true);
                        female.setEnabled(true);
                    }
                }
            }
        });
    }
}
