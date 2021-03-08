package com.example.remote_learning_plus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Student_HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__home_page);
    }

    public void JoinCourse(View view) {
        Intent intent = new Intent(this, Join_Course.class);
        startActivity(intent);
    }
}