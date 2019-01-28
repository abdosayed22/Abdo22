package com.aradwan054.hatly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class signup extends AppCompatActivity {
EditText mail,pass,username,mobile,idnum,address;
Button sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mail=findViewById(R.id.cust_mail_edit);
        pass=findViewById(R.id.cust_pass_edit);
        username=findViewById(R.id.cust_user_edit);
        mobile=findViewById(R.id.cust_phone_edit);
        idnum=findViewById(R.id.cust_idNum_edit);
        address=findViewById(R.id.cust_address_edit);
        sign=findViewById(R.id.cust_btn_sign);
    }
}
