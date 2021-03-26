package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class student_course_home extends AppCompatActivity {
    TextView txtCourseName;
    String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_home);

        Intent intent = getIntent();
        txtCourseName = findViewById(R.id.tvCourse_Name);
        courseID = intent.getStringExtra("courseID");
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
                        Intent intent2 = new Intent(getApplicationContext(), ask_question.class);
                        startActivity(intent2);
                }
            }
        });
    }
}