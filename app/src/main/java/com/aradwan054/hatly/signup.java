package com.aradwan054.hatly;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class signup extends AppCompatActivity {

    EditText mail, pass, username, mobile, nationalNum, address;
    ImageView image_customer;
    Button sign;
    TextView login_txt;
    FirebaseDatabase database;
    DatabaseReference myRef;
    RadioButton male, female;
    boolean gabalsora ;
    private FirebaseAuth mAuth;
    Boolean DataFalse = false;
    private static final int GALLERY_INTENT = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private StorageReference mStorageRef;
    private Uri imgUri;
    String imgURL ;
    boolean c , seterror ;
    private UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
      gabalsora= false ;

        // initialize Views
        image_customer = findViewById(R.id.profile_img);
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
        c=false ;
        seterror=false ;

        mStorageRef = FirebaseStorage.getInstance().getReference();//GDID

        final ProgressDialog progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Accounts");

     //   ClearData(this);



        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });
        sign.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {

                try {
                    CheckData(this);





                 if (gabalsora == false)
                 {   progressDialog.setProgressStyle(R.drawable.card_back);
                     progressDialog.setTitle("incomplete Data");
                     progressDialog.setMessage("Please Insert Correct Data !!");
                     progressDialog.show();
                     return;}


                    if (seterror == true)
                    {
                        return;
                    }



                    final Map<String, String> map = new HashMap<String, String>();
                    if (male.isChecked())
                    {
                        map.put("Gender", "male");
                        c=true ;

                    }
                    else if (female.isChecked())
                    {
                        map.put("Gender", "female");
                        c=true ;

                    }


                        if(c==false) {
                            Toast.makeText(signup.this, "Gender ya 7lw", Toast.LENGTH_SHORT).show();
                            progressDialog.setProgressStyle(R.drawable.card_back);
                            progressDialog.setTitle("incomplete Data");
                            progressDialog.setMessage("Please Insert Correct Data !!");
                            progressDialog.show();
                            return;
                        }



                    mAuth.createUserWithEmailAndPassword(mail.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    } else if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();


                                        FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString())
                                                .putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Toast.makeText(signup.this, "uploaded", Toast.LENGTH_SHORT).show();


                                                map.put("e-mail", mail.getText().toString());
                                                map.put("Password", pass.getText().toString());
                                                map.put("Phone", mobile.getText().toString());
                                                map.put("UserName", username.getText().toString());
                                                map.put("national-number", nationalNum.getText().toString());
                                                map.put("address", address.getText().toString());

                                                map.put("imgURI", taskSnapshot.getDownloadUrl().toString());
                                                myRef.child(mAuth.getCurrentUser().getUid()).setValue(map);
                                                mail.setText("");
                                                pass.setText("");
                                                mobile.setText("");
                                                username.setText("");
                                                address.setText("");
                                                nationalNum.setText("");


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(signup.this, "exception is " + e.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        // Sign in success, update UI with the signed-in user's information

                                        Toast.makeText(signup.this, "Account Created", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), login.class);
                                        startActivity(intent);

                                    }

                                }
                            });

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            imgUri = data.getData();

            Toast.makeText(this, imgUri.toString(), Toast.LENGTH_LONG).show();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                image_customer.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        gabalsora = true ;
    }


    public void uploadProfile(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "select"), GALLERY_INTENT);


    }


    public void ClearData(Context context) {
        mail.setText("");
        pass.setText("");
        mobile.setText("");
        username.setText("");
        address.setText("");
        nationalNum.setText("");
        male.setChecked(false);
        female.setChecked(false);
    }
    public void CheckData(View.OnClickListener context) {
       String cmail = mail.getText().toString();
        String cpass = pass.getText().toString();
        String cphone = mobile.getText().toString();
        String cnational = nationalNum.getText().toString();
        seterror = false ;


        if (cmail.equals("") || !cmail.contains(".com") || !cmail.contains("@gmail")   ) {

            mail.setError("Please Put a real  @gmail account");
            seterror = true ;

        }

        if (username.getText().toString().equals("")  ) {
            seterror = true ;
            username.setError("Please Put a real name");

        }
        if (address.getText().toString().equals("")  ) {

            address.setError("Please Put a real address");
            seterror = true ;

        }
        if (cphone.equals("") || !cphone.contains("01")  ) {

            mobile.setError("Please Put a real phone");
            seterror = true ;

        }
        if (cnational.equals("") || cnational.length()<14  ) {

            nationalNum.setError("Please Put a real national num ");
            seterror = true ;

        }
        if (cpass.equals("") || cpass.length()<6 ) {

            pass.setError("Please Put a real password (more than 6 char ");
            seterror = true ;

        }
    }





    }

