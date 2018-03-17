package com.example.kushagra.everest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class SetupActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar setupToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        setupToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(setupToolbar);
        getSupportActionBar().setTitle("Account Setting");


    }
}
