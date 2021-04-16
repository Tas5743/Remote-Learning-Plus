package com.example.remote_learning_plus.remotelearningplus;


import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;

import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.mifmif.common.regex.Generex;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SelectCourseTimes extends AppCompatActivity{

    String TextName;
    String TextID;
    String TextInviteCode;
    String uniqueCourseID;
    String TextCourseDescription;
    Button add_course;
    TimePicker startTime;
    TimePicker endTime;
    String sTime;
    String eTime;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_meeting_schedule);
        
        //Navigation Bar
         BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnHome:
                        Intent intent = new Intent(getApplicationContext(), Home_Teacher.class);
                        startActivity(intent);
                        break;
                    case R.id.btnAdd:
                        Intent intent2 = new Intent(getApplicationContext(), CourseInformation.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });
        //Bundle bundle = getIntent().getExtras();
//        TextName = bundle.getString("courseName");
        //TextID = bundle.getString("courseID");
//        TextInviteCode = bundle.getString("CourseInviteCode");
        //TextSection = bundle.getString("courseSection");
//        TextCourseDescription = bundle.getString("courseDescription");

        Intent intent = getIntent();
        TextName = intent.getStringExtra("courseName");
        TextID = intent.getStringExtra("courseId");
        uniqueCourseID = intent.getStringExtra("uniqueCourseID");
        TextCourseDescription = intent.getStringExtra("courseDesc");


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

        //ArrayList<String> days = new ArrayList<>();
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
        if(sM < 10){
            sTime = sH + ":" + "0"+sM;
        }
        else {
            sTime = sH + ":" + sM;
        }
        if(eM < 10){
            eTime = eH + ":" + "0"+eM;}
        else { eTime = eH + ":" + eM;}


        EditText edtTextSection = findViewById(R.id.etsection);
        String TextSection = edtTextSection.getText().toString().trim();

        /*
        if (M.isChecked()){days.add("Monday");}
        if (T.isChecked()){days.add("Tuesday");}
        if (W.isChecked()){days.add("Wednesday");}
        if (TH.isChecked()){days.add("Thursday");}
        if (F.isChecked()){days.add("Friday");}*/

        String days = "";
        if (M.isChecked()){days += "M ";}
        if (T.isChecked()){days += "T ";}
        if (W.isChecked()){days += "W ";}
        if (TH.isChecked()){days += "Th ";}
        if (F.isChecked()){days += "F ";}


        if(TextSection.isEmpty()){
                edtTextSection.setError("Enter a course section.");
                edtTextSection.requestFocus();
              }
        else if (days.isEmpty()){
            Toast.makeText(SelectCourseTimes.this, "Please select meeting day(s).", Toast.LENGTH_SHORT).show();;
        }

        else{

            Generex generex = new Generex("[A-Z]{3}\\d{3}");

            String inviteCode = generex.random();

            DocumentReference colSections = FirebaseFirestore.getInstance().collection("masterSectionList").document(inviteCode);
//            while(colSections){
//                inviteCode = generex.random();
//                colSections = FirebaseFirestore.getInstance().collection("masterSectionList").document(inviteCode);
//                check =  colSections.get().getResult();
//            }
            //TODO Generate new invite code if current one already exists.





            Map<String, Object> dataToSave = new HashMap<String, Object>();
            dataToSave.put("courseName", TextName);
            dataToSave.put("courseSection", TextSection);
            dataToSave.put("courseId", TextID);
            //dataToSave.put("courseDays",days);
            dataToSave.put("courseDays",days);
            dataToSave.put("startTime",sTime);
            dataToSave.put("endTime",eTime);
            dataToSave.put("InviteCode", inviteCode);
            dataToSave.put("courseDesc", TextCourseDescription);
            dataToSave.put("uniqueCourseID", uniqueCourseID);

            // Reference new course to teachers course list
            String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
            DocumentReference docRefInstructor = db.collection("users").document(userId).collection("courses").document(TextID+TextSection);
            Map<String, Object> courseList = new HashMap<>();
            courseList.put("courseRef","courses/"+uniqueCourseID+"/section/"+TextSection);
            courseList.put("courseSection", TextSection);
            courseList.put("courseID", TextID);
            docRefInstructor.set(courseList);



            DocumentReference colRef = FirebaseFirestore.getInstance().collection("courses/"+uniqueCourseID+"/section").document(TextSection);
            colRef.set(dataToSave);
            Map<String, Object> masterlist = new HashMap<String, Object>();
            masterlist.put("sectionReference","courses/"+uniqueCourseID+"/section/"+TextSection);
            colSections.set(masterlist);

            Toast.makeText(SelectCourseTimes.this, "Course Successfully created!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SelectCourseTimes.this, Home_Teacher.class);
            startActivity(intent);

        }

    }
}
