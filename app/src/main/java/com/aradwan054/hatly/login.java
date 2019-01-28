package com.aradwan054.hatly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {
EditText user,pass;
Button custLogin,delvrLogin,SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=findViewById(R.id.login_user_edt);
        pass=findViewById(R.id.login_pass_edt);
        custLogin=findViewById(R.id.cust_login_btn);
        delvrLogin=findViewById(R.id.delvr_login_btn);
        SignUp=findViewById(R.id.sign_up_btn);
        custLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,Order.class);
                startActivity(intent);
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,signup.class);
                startActivity(intent);
            }
        });
    }

}
