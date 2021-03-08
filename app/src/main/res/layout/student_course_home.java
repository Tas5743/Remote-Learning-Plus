package com.example.remote_learning_plus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class sudent_course_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudent_course_home);
    }

    public void AskQuestion(View view) {
        Intent intent = new Intent(this, ask_question.class);
        startActivity(intent);
    }
}