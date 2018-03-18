package com.example.kushagra.everest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    //added username later
    private EditText reg_username_field;
    //
    private EditText reg_email_field;
    private EditText reg_pass_field;
    private EditText reg_confirm_pass_field;
    private Button reg_btn;
    private Button reg_login_btn;
    private ProgressBar reg_progress;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        reg_username_field = (EditText) findViewById(R.id.reg_username);
        reg_email_field = (EditText) findViewById(R.id.reg_email);
        reg_pass_field = (EditText) findViewById(R.id.reg_pass);
        reg_confirm_pass_field = (EditText) findViewById(R.id.reg_confirm_pass);
        reg_btn = (Button) findViewById(R.id.reg_btn);
        reg_login_btn =(Button) findViewById(R.id.reg_login_btn);
        reg_progress = (ProgressBar) findViewById(R.id.reg_progress);


        reg_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = reg_username_field.getText().toString();
                String email = reg_email_field.getText().toString();
                String pass = reg_pass_field.getText().toString();
                String confirm_pass = reg_confirm_pass_field.getText().toString();


                register_user(username,email,pass,confirm_pass);//blog app has done here but chat app has made method


            }
        });



    }

    private void register_user(final String username, String email, String pass, String confirm_pass)
    {
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass)){
            if (pass.equals(confirm_pass))
            {
                reg_progress.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            //sending data to Firebase

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            //can add here if statement to check if current user is null
                            String uid = current_user.getUid();
                            //storing data, 3 fields

                            //store to a child of root database called User -> User -> uid
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            //adding complex data, using hash map

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name",username);
                            userMap.put("status","Hi there, I'm using EEMC Hub");
                            userMap.put("image","default");
                            userMap.put("thumb_image","default");

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        //After creating account send to account setting to make user set up his name

                                        Intent setupIntent = new Intent(RegisterActivity.this, SettingActivity.class);
                                        startActivity(setupIntent);
                                        finish();

                                    }
                                }
                            });//add to DB using userMap

                            //


                        }
                        else{
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(RegisterActivity.this,"Error" + errorMessage,Toast.LENGTH_LONG).show();
                        }

                        reg_progress.setVisibility(View.INVISIBLE);
                    }
                });

            }
            else
            {
                Toast.makeText(RegisterActivity.this,"Confirm password dosen't match",Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            sendToMain();

        }
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
