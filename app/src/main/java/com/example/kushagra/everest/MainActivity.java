package com.example.kushagra.everest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mainToolbar;

    private FirebaseAuth mAuth;

    private Button chat_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        chat_btn = (Button) findViewById(R.id.chat_btn);

        mainToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("EEMC Hub");


        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChat = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intentChat);

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        //when app starts it checks if user is logged in or not
        //if not prompt to login page
        //if it is, go in the app

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){

            sendToLogin();


        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_logout_btn:

                logOut();
                return true;

            

            default:
                return false;

        }

    }

    private void logOut() {

        mAuth.signOut();
        sendToLogin();

    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish(); //to make sure that when back is pressed the intent wont come back to this page
    }
}
