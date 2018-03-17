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

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailText;
    private EditText loginPassText;
    private Button loginBtn;
    private Button loginRegbtn;

    private FirebaseAuth mAuth;

    private ProgressBar loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginEmailText = (EditText) findViewById(R.id.reg_email);
        loginPassText = (EditText) findViewById(R.id.reg_confirm_pass);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginRegbtn = (Button) findViewById(R.id.login_reg_btn);
        loginProgress = (ProgressBar) findViewById(R.id.login_progress);


        //Intent added after making registerActivity
        //to go to register from login page

        loginRegbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regIntent);


            }
        });
        //Intent finish


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String loginEmail = loginEmailText.getText().toString();
                final String loginPassword = loginPassText.getText().toString();


                //Check if the email and the password is empty or not

                if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPassword)){

                        //if its not empty show the loginprogress bar
                        loginProgress.setVisibility(View.VISIBLE);

                    //checking for login process
                    mAuth.signInWithEmailAndPassword(loginEmail,loginPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {



                            if(task.isSuccessful()){

                                sendToMain();

                            }
                            else{
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error" + errorMessage, Toast.LENGTH_LONG).show();
                            }

                            //if there are errors progress bar must not be shown
                            loginProgress.setVisibility(View.INVISIBLE);

                        }
                    });
                }



            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //using to find if a user is logged in or not
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){

            sendToMain();
        }
    }

    //we made sendToMain to goto mainactivity as a shortcut function which we can call anytime
    private void sendToMain() {

        Intent mainintent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainintent);
        finish();
    }


}
