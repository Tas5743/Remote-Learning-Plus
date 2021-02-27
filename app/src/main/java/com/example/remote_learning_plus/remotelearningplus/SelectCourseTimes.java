package com.example.remote_learning_plus.remotelearningplus;


import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;

import android.text.format.Time;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mifmif.common.regex.Generex;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

public class SelectCourseTimes extends AppCompatActivity{

//    String TextName;
    String TextID;
    String TextInviteCode;
    String TextSection;
//    String TextCourseDescription;
    Button add_course;
    TimePicker startTime;
    TimePicker endTime;
    String sTime;
    String eTime;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_meeting_schedule);
        Bundle bundle = getIntent().getExtras();
//        TextName = bundle.getString("courseName");
        TextID = bundle.getString("courseID");
//        TextInviteCode = bundle.getString("CourseInviteCode");
        TextSection = bundle.getString("courseSection");
//        TextCourseDescription = bundle.getString("courseDescription");

        add_course = findViewById(R.id.add_course);

        startTime = findViewById(R.id.timePicker1);
        startTime.setIs24HourView(true);
        endTime = findViewById(R.id.timePicker2);
        endTime.setIs24HourView(true);




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

        int sH, sM, eH, eM;
        if (Build.VERSION.SDK_INT >= 23 ){
            sH = startTime.getHour();
            sM = startTime.getMinute();
            eH = endTime.getHour();
            eM = endTime.getMinute();
        }
        else{
            sH = startTime.getCurrentHour();
            sM = startTime.getCurrentMinute();
            eH = endTime.getCurrentHour();
            eM = endTime.getCurrentMinute();
        }

        sTime = sH + ":" + sM;
        eTime = eH + ":" + eM;


        if (M.isChecked()){days.add("Monday");}
        if (T.isChecked()){days.add("Tuesday");}
        if (W.isChecked()){days.add("Wednesday");}
        if (TH.isChecked()){days.add("Thursday");}
        if (F.isChecked()){days.add("Friday");}


        if (days.isEmpty()){
            Toast.makeText(SelectCourseTimes.this, "Please select meeting day(s).", Toast.LENGTH_SHORT).show();;
        }

        else{

            Generex generex = new Generex("[A-Z]{3}\\d{3}");


            Map<String, Object> dataToSave = new HashMap<String, Object>();

            dataToSave.put("courseDays",days);
            dataToSave.put("startTime",sTime);
            dataToSave.put("endTime",eTime);
            dataToSave.put("InviteCode", generex.random());

            Toast.makeText(SelectCourseTimes.this, "Course Successfully crated!", Toast.LENGTH_SHORT).show();
            //TODO redirect to course page.
            //TODO Add section textbox


            DocumentReference colRef = FirebaseFirestore.getInstance().collection("courses/"+TextID+"/sections").document(TextSection);
            colRef.set(dataToSave);
        }

    }
}
