package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
import android.os.Bundle;

import com.example.remote_learning_plus.remotelearningplus.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class CreateCourse extends AppCompatActivity {

    private CollectionReference colRef = FirebaseFirestore.getInstance().collection("courses");

    public void savecourse1(View v){

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

        Map<String, Object> dataToSave = new HashMap <String, Object>();
        dataToSave.put("courseName",TextName);
        dataToSave.put("courseId",TextID);
        dataToSave.put("courseInviteCode",TextInviteCode);
        dataToSave.put("courseSection",TextSection);
        dataToSave.put("courseDescription",TextCourseDescription);

        Intent selectMeetTimes = new Intent(CreateCourse.this,SelectCourseTimes.class);
        startActivity(selectMeetTimes);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}