package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;

public class Ask_Question extends AppCompatActivity {

    Bundle studentandinstructor = getIntent().getExtras();
    //String student = studentandinstructor.getString("student");
    //String instructor_course = studentandinstructor.getString("course_path");
    String student = "student";
    String instructor_course = "courses/SW123/sections/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        Button button = findViewById(R.id.btnSubmit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText question = findViewById(R.id.etQuestion);
                CollectionReference log = FirebaseFirestore.getInstance().collection(instructor_course+"/questions");
                HashMap<String,Object> croissant = new HashMap<>();
                croissant.put("student", student);
                croissant.put("question", question.getText().toString());
                croissant.put("isAnswered", false);
                log.add(croissant);
                Toast.makeText(Ask_Question.this,"Question posted.", Toast.LENGTH_SHORT).show();
                Intent homepage = new Intent(Ask_Question.this, student_course_home.class);
                startActivity(homepage);
            }
        });

    }


}