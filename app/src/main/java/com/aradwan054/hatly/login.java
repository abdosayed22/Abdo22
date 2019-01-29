package com.aradwan054.hatly;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    EditText mail, pass;
    Button custLogin, delvrLogin, SignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mail = findViewById(R.id.login_mail_edt);
        pass = findViewById(R.id.login_pass_edt);
        custLogin = findViewById(R.id.cust_login_btn);
        delvrLogin = findViewById(R.id.delvr_login_btn);
        SignUp = findViewById(R.id.sign_up_btn);
        mAuth = FirebaseAuth.getInstance();
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });
        custLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mail.getText().toString().equals("")) mail.setError("Please Enter mail");
                if (pass.getText().toString().equals("")) pass.setError("Please Enter your Password");



                String myMail=mail.getText().toString();
                String myPass= pass.getText().toString();


                mAuth.signInWithEmailAndPassword(myMail,myPass)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(login.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Order.class);
                                    intent.putExtra("name", mail.getText().toString());
                                    startActivity(intent);
                                    mail.setText("");
                                    pass.setText("");
                                } else

                                    Toast.makeText(login.this, "Authentication failed", Toast.LENGTH_SHORT).show();


                            }
                        });








            }
        });
    }
}