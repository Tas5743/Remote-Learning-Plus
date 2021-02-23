package com.example.remote_learning_plus.remotelearningplus;


import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectCourseTimes extends AppCompatActivity {

    String TextName;
    String TextID;
    String TextInviteCode;
    String TextSection;
    String TextCourseDescription;
    Button add_course;

    private CollectionReference colRef = FirebaseFirestore.getInstance().collection("courses");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_meeting_schedule);

        Bundle bundle = getIntent().getExtras();
        TextName = bundle.getString("courseName");
        TextID = bundle.getString("courseID");
        TextInviteCode = bundle.getString("CourseInviteCode");
        TextSection = bundle.getString("courseSection");
        TextCourseDescription = bundle.getString("courseDescription");

        add_course = findViewById(R.id.add_course);

        add_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savecourse2(v);
            }
        });

    }


    public void savecourse2(View v){
        ArrayList<String> days = new ArrayList<>();
        CheckBox M = findViewById(R.id.chkboxMonday);
        CheckBox T = findViewById(R.id.chkboxTuesday);
        CheckBox W = findViewById(R.id.chkboxWednesday);
        CheckBox TH = findViewById(R.id.chkboxThursday);
        CheckBox F = findViewById(R.id.chkboxFriday);
        Spinner startTime = findViewById(R.id.spinnerStartTime);
        Spinner endTime = findViewById(R.id.spinnerEndTime);



        if (M.isChecked()){days.add("Monday");}
        if (T.isChecked()){days.add("Tuesday");}
        if (W.isChecked()){days.add("Wednesday");}
        if (TH.isChecked()){days.add("Thursday");}
        if (F.isChecked()){days.add("Friday");}

        //TODO Extract Spinner information, put that into the course data.




        if (days.isEmpty()){
            Toast.makeText(SelectCourseTimes.this, "Please select meeting day(s).", Toast.LENGTH_SHORT).show();;
        }


        else{
            Map<String, Object> dataToSave = new HashMap<String, Object>();
            dataToSave.put("courseName",TextName);
            dataToSave.put("courseId",TextID);
            dataToSave.put("courseInviteCode",TextInviteCode);
            dataToSave.put("courseSection",TextSection);
            dataToSave.put("courseDescription",TextCourseDescription);
            dataToSave.put("courseDays",days);
            //dataToSave.put("startTime",startTime);
            //dataToSave.put("endTime",endTime);
            colRef.add(dataToSave);
            Toast.makeText(SelectCourseTimes.this, "Course Successfully crated!", Toast.LENGTH_SHORT).show();
            //TODO redirect to course page.
        }

    }
}
