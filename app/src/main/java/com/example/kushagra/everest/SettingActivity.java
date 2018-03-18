package com.example.kushagra.everest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    //
    private CircleImageView mDisplayImage;
    private TextView mName;
    private TextView mStatus;

    private Button mStatusButton;
    private Button mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //
        mDisplayImage = (CircleImageView) findViewById(R.id.setting_circle_image);
        mName = (TextView) findViewById(R.id.setting_display_name);
        mStatus = (TextView) findViewById(R.id.setting_status);
        //
        mStatusButton  = (Button) findViewById(R.id.setting_status_btn);



        //firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_Uid = mCurrentUser.getUid();//retrieve the uid of user from DB

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_Uid);//pointing to users -> then to uid
        //value event listener to get each uid data like name status ...


        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //when ever data is added, retrieve, changed
                //Toast.makeText(SettingActivity.this,dataSnapshot.toString(), Toast.LENGTH_LONG).show();


                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();


                mName.setText(name);
                mStatus.setText(status);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //
                String status_value = mStatus.getText().toString();


                Intent statusIntent = new Intent(SettingActivity.this, StatusActivity.class);

                //hint in the textfield for old status
                statusIntent.putExtra("status_value",status_value);

                startActivity(statusIntent);
            }
        });
    }
}
