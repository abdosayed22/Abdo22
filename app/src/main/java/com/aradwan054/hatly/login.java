package com.aradwan054.hatly;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class login extends AppCompatActivity {
    EditText mail, pass;
    Button SignUp;
    RadioGroup radioGroup;
    RadioButton custLogin, delvrLogin;
    TextView signup_txt;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //  hideSoftKeyboard(this);
        mail = findViewById(R.id.login_mail_edt);
        pass = findViewById(R.id.login_pass_edt);
        custLogin = findViewById(R.id.rdb_as_customer);
        delvrLogin = findViewById(R.id.rdb_as_delivery);
        SignUp = findViewById(R.id.login_sign_btn);
        signup_txt = findViewById(R.id.signup_txt);
        radioGroup = findViewById(R.id.radio_group);
        mAuth = FirebaseAuth.getInstance();

        custLogin.setChecked(false);
        delvrLogin.setChecked(false);

        delvrLogin.setEnabled(false);
        custLogin.setEnabled(false);

        //  mail.setFocusable(false);
        //pass.setFocusable(false);

       // InputMethodManager imm = (InputMethodManager) getSystemService(login.INPUT_METHOD_SERVICE);
       // Objects.requireNonNull(imm).hideSoftInputFromWindow(mail.getWindowToken(), 0);
        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pass.getText().toString().equals("")) {

                    delvrLogin.setEnabled(false);
                    custLogin.setEnabled(false);
                }
                // delvrLogin.setEnabled(false);
                // custLogin.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pass.getText().toString().equals("")) {

                    delvrLogin.setEnabled(false);
                    custLogin.setEnabled(false);

                } else {
                    delvrLogin.setEnabled(true);
                    custLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mail.getText().toString().equals("")) {

                    delvrLogin.setEnabled(false);
                    custLogin.setEnabled(false);
                } else {
                    if (pass.getText().toString().equals("")) {

                        delvrLogin.setEnabled(false);
                        custLogin.setEnabled(false);
                    } else {
                        delvrLogin.setEnabled(true);
                        custLogin.setEnabled(true);


                    }
                }

            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pass.getText().toString().equals("")) {

                    delvrLogin.setEnabled(false);
                    custLogin.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mail.getText().toString().equals("")) {

                    delvrLogin.setEnabled(false);
                    custLogin.setEnabled(false);

                } else {
                    delvrLogin.setEnabled(true);
                    custLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (pass.getText().toString().equals("")) {

                    delvrLogin.setEnabled(false);
                    custLogin.setEnabled(false);
                } else {
                    if (mail.getText().toString().equals("")) {

                        delvrLogin.setEnabled(false);
                        custLogin.setEnabled(false);
                    } else {
                        delvrLogin.setEnabled(true);
                        custLogin.setEnabled(true);


                    }
                }

            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check If Edit Text Empty Or Not

                if (mail.getText().toString().equals("")) {
                    mail.setError("Enter Your E-Mail !");
                    // radioGroup.setEnabled(false);
                }
                if (pass.getText().toString().equals("")) {
                    pass.setError("Enter Your Password !");
                    // radioGroup.setEnabled(false);
                }
                if ((!mail.getText().toString().equals("")) && (!pass.getText().toString().equals(""))) {
                    radioGroup.setEnabled(true);
                }
                // Check If One Of Radio Buttons Is Checked Or Not

                if ((!custLogin.isChecked()) && (!delvrLogin.isChecked())) {

                    Toast.makeText(login.this, "Set Your Identifier", Toast.LENGTH_LONG).show();

                } else {
                    // Check Which Radio Button Is Checked And Go Intent

                    if (custLogin.isChecked()) {

                        // Check If Edit Text Empty Or Not

                        //   if (mail.getText().toString().equals("")) mail.setError("Enter Your E-Mail !");
                        //  if (pass.getText().toString().equals("")) pass.setError("Enter Your Password !");

                        mAuth.signInWithEmailAndPassword(mail.getText().toString(), pass.getText().toString())
                                .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(login.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Navigation.class);
                                    intent.putExtra("email", mail.getText().toString());
                                    startActivity(intent);
                                    mail.setText("");
                                    pass.setText("");

                                } else
                                    Toast.makeText(login.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                    else if(delvrLogin.isChecked()){
                        mAuth.signInWithEmailAndPassword(mail.getText().toString(), pass.getText().toString())
                                .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(login.this, "Welcome Baby", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), recycler_delivery.class);
                                    intent.putExtra("name", mail.getText().toString());
                                    startActivity(intent);
                                    mail.setText("");
                                    pass.setText("");

                                } else

                                    Toast.makeText(login.this, "Authentication failed", Toast.LENGTH_SHORT).show();


                            }
                        });

                    }
                }
            }
        });
        signup_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);

            }
        });
    }


}