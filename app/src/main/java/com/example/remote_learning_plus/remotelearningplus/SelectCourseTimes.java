package com.example.remote_learning_plus.remotelearningplus;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SelectCourseTimes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_meeting_schedule);

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




        if (days.isEmpty()){
            Toast.makeText(SelectCourseTimes.this, "Please select meeting day(s).", Toast.LENGTH_SHORT).show();;
        }

    }
}
