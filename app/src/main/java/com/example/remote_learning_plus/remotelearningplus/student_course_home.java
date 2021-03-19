package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class student_course_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_home);

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