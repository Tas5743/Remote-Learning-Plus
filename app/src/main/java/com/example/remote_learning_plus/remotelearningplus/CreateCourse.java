package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class CreateCourse extends AppCompatActivity {
    DocumentReference colRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Expand reference to include instructor
                EditText  edtTextName = findViewById(R.id.etcourse_name);
                EditText  edtTextID = findViewById(R.id.etcourse_id);
//                EditText  edtTextInviteCode= findViewById(R.id.etinvite_code);

                EditText  edtTextCourseDescription = findViewById(R.id.etcourse_description);


                String TextName = edtTextName.getText().toString().trim();
                String TextID =   edtTextID.getText().toString().trim();
//                String TextInviteCode = edtTextInviteCode.getText().toString().trim();

                String TextCourseDescription = edtTextCourseDescription.getText().toString().trim();

                if(TextName.isEmpty()){
                    Toast.makeText(CreateCourse.this,"Please fill out Course Name", Toast.LENGTH_SHORT).show();
                }
                else if(TextID.isEmpty()){
                    Toast.makeText(CreateCourse.this,"Please fill out Course ID", Toast.LENGTH_SHORT).show();
                }
//                else if(TextInviteCode.isEmpty()){
//                    Toast.makeText(CreateCourse.this,"Please fill out Course Invite Code", Toast.LENGTH_SHORT).show();
//                }

                else if(TextCourseDescription.isEmpty()){
                    Toast.makeText(CreateCourse.this,"Please fill out Course Description", Toast.LENGTH_SHORT).show();
                }

                else{
                    Map<String, Object> dataToSave = new HashMap<String, Object>();
                    dataToSave.put("courseTitle",TextName);
                    //dataToSave.put("courseID",TextID);
                    //dataToSave.put("courseCode",TextInviteCode);
 //                   dataToSave.put("courseSection",TextSection);
                    dataToSave.put("courseDescription",TextCourseDescription);




                    //TODO redirect to course page.
                    colRef = FirebaseFirestore.getInstance().collection("courses").document(TextID);
                    colRef.set(dataToSave);
                    Toast.makeText(CreateCourse.this, "Course Successfully created!", Toast.LENGTH_SHORT).show();
                    Intent selectMeetTimes = new Intent(CreateCourse.this,SelectCourseTimes.class);


//                    selectMeetTimes.putExtra("courseName", TextName);
                    selectMeetTimes.putExtra("courseID", TextID);
//                    selectMeetTimes.putExtra("courseInviteCode", TextInviteCode);
//                    selectMeetTimes.putExtra("courseSection",TextSection);
//                    selectMeetTimes.putExtra("courseDescription",TextCourseDescription);
                    startActivity(selectMeetTimes);
                }
            }
        });
    }




}