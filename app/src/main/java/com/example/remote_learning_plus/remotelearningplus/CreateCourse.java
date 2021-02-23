package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;



public class CreateCourse extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText  edtTextName = findViewById(R.id.etcourse_name);
                EditText  edtTextID = findViewById(R.id.etcourse_id);
                EditText  edtTextInviteCode= findViewById(R.id.etinvite_code);
                EditText  edtTextSection = findViewById(R.id.etsection);
                EditText  edtTextCourseDescription = findViewById(R.id.etcourse_description);


                String TextName = edtTextName.getText().toString().trim();
                String TextID =   edtTextID.getText().toString().trim();
                String TextInviteCode = edtTextInviteCode.getText().toString().trim();
                String TextSection = edtTextSection.getText().toString().trim();
                String TextCourseDescription = edtTextCourseDescription.getText().toString().trim();

                //TODO Check if Data is valid


                Intent selectMeetTimes = new Intent(CreateCourse.this,SelectCourseTimes.class);
                selectMeetTimes.putExtra("courseName", TextName);
                selectMeetTimes.putExtra("courseID", TextID);
                selectMeetTimes.putExtra("courseInviteCode", TextInviteCode);
                selectMeetTimes.putExtra("courseSection",TextSection);
                selectMeetTimes.putExtra("courseDescription",TextCourseDescription);
                startActivity(selectMeetTimes);
            }
        });
    }




}