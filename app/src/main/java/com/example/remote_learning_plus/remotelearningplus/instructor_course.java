package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class instructor_course extends AppCompatActivity implements View.OnClickListener{
    TextView courseName;
    String courseID, courseSection;
    Button btnQuiz, btnResource, btnClassList, btnInfo, btnStats, btnQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_course);

        Intent intent = getIntent();
        courseName = findViewById(R.id.tvCourse_Name);
        courseID = intent.getStringExtra("courseID");
        courseSection = intent.getStringExtra("courseSection");
        courseName.setText(courseID);

        btnQuiz = findViewById(R.id.btnQuizzes);
        btnResource = findViewById(R.id.btnResources);
        btnClassList = findViewById(R.id.btnClass_List);
        btnInfo = findViewById(R.id.btnInformation);
        btnStats = findViewById(R.id.btnStatistics);
        btnQuestions = findViewById(R.id.btnQuestions);

        btnQuiz.setOnClickListener(this);
        btnResource.setOnClickListener(this);
        btnClassList.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnStats.setOnClickListener(this);
        btnQuestions.setOnClickListener(this);


        //Navigation Bar
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnHome:
                        Intent intent2 = new Intent(getApplicationContext(), Home_Teacher.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnQuizzes:
                break;
            case R.id.btnResources:
                break;
            case R.id.btnClass_List:
                break;
            case R.id.btnInformation:
                break;
            case R.id.btnStatistics:
                break;
            case R.id.btnQuestions:
                Intent intent = new Intent(instructor_course.this, StudentQuestions.class);
                intent.putExtra("courseID", courseID);
                intent.putExtra("courseSection", courseSection);
                Log.d("putExtra", courseID + " " + courseSection);
                startActivity(intent);
                break;
        }
    }
}
