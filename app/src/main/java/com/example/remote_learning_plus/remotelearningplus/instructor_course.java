package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class instructor_course extends AppCompatActivity implements View.OnClickListener {
    TextView courseName;
    String courseRef,courseID,courseSection;
    Button btnResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_course);

        btnResources = findViewById(R.id.btnResources);
        btnResources.setOnClickListener(this);

        Intent intent = getIntent();
        courseName = findViewById(R.id.tvCourse_Name);
        courseID = intent.getStringExtra("courseID");
        courseSection = intent.getStringExtra("courseSelection");
        courseRef = "courses/" + courseID + "/section/" + courseSection;
        courseName.setText(courseID);


        //Navigation Bar
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnHome:
                        Intent intent = new Intent(getApplicationContext(), Home_Teacher.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnResources:
                Intent intent = new Intent(this, AddResource.class);
                intent.putExtra("courseRef", courseRef);
                startActivity(intent);
        }
    }
}
