package com.example.remote_learning_plus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CourseInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);
    }

    public void CourseSchedule(View view) {
        Intent intent = new Intent(this, CourseMeetingSchedule.class);
        startActivity(intent);
    }
}