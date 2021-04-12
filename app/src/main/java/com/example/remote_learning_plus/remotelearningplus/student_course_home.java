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

public class student_course_home extends AppCompatActivity implements View.OnClickListener {
    TextView txtCourseName;
    String courseID, courseSection, courseRef;
    Button btnAskQuestion, btnQuiz, btnResources, btnResults, btnInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_home);

        btnAskQuestion = findViewById(R.id.btnAsk);
        btnQuiz = findViewById(R.id.btnQuizzes);
        btnResources = findViewById(R.id.btnResources);
        btnResults = findViewById(R.id.btnResults);
        btnInformation = findViewById(R.id.btnInformation);

        btnAskQuestion.setOnClickListener(this);
        btnQuiz.setOnClickListener(this);
        btnResources.setOnClickListener(this);
        btnResults.setOnClickListener(this);
        btnInformation.setOnClickListener(this);

        Intent intent = getIntent();
        txtCourseName = findViewById(R.id.tvCourse_Name);
        courseID = intent.getStringExtra("courseID");
        courseSection = intent.getStringExtra("courseSection");
        courseRef = intent.getStringExtra("courseRef");
        txtCourseName.setText(courseID);

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btnHome:
                        Intent intent = new Intent(getApplicationContext(), Home_Student.class);
                        startActivity(intent);
                    case R.id.btnAdd:
                        Intent intent2 = new Intent(getApplicationContext(), Student_Ask_Question.class);
                        startActivity(intent2);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAsk:
                Intent intent = new Intent(this, Student_Ask_Question.class);
                intent.putExtra("courseID", courseID);
                intent.putExtra("courseSection", courseSection);
                intent.putExtra("courseRef", courseRef);
                startActivity(intent);
                break;
            case R.id.btnQuizzes:
                break;
            case R.id.btnResources:
                break;
            case R.id.btnResults:
                break;
            case R.id.btnInformation:
                break;
        }
    }
}