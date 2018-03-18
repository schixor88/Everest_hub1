package com.example.kushagra.everest;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class ChatActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar chatToolbar;

    private ViewPager mViewpager;
    private SectionPagerAdapter mSectionPagerAdapter;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(chatToolbar);
        getSupportActionBar().setTitle("EEMC Chat");

        //tabs

        mViewpager = (ViewPager) findViewById(R.id.chat_tabPager);

        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        mViewpager.setAdapter(mSectionPagerAdapter);

        mTabLayout = findViewById(R.id.chat_tabs);

        //setting tablayout with viewpager
        mTabLayout.setupWithViewPager(mViewpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

         super.onOptionsItemSelected(item);

         //i've done this using select case in mainactivity, here i am trying with if condition

        if (item.getItemId()==R.id.action_logout_btn){

            FirebaseAuth.getInstance().signOut();
            sendToLogin();
        }
        if (item.getItemId()==R.id.action_settings_btn){

            sendToSetting();
        }


         return true;

    }

    private void sendToSetting() {

        Intent settingIntent = new Intent(ChatActivity.this, SettingActivity.class);
        startActivity(settingIntent);
    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(ChatActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
