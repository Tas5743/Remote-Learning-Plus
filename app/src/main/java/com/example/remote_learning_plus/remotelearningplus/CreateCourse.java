package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mifmif.common.regex.Generex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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





                    //TODO redirect to course page.

                    FirebaseFirestore.getInstance().collection("courses").get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        List<String> sections = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            sections.add(document.getId());
                                        }

                                        boolean unique = false;
                                        String inviteCode = "o";
                                        while (!unique){
                                            Generex generex = new Generex("[A-Za-z0-9]{9}");
                                            inviteCode = generex.random();
                                            if(
                                                    !sections.contains(inviteCode)){
                                                unique = true;
                                            }}
                                        colRef = FirebaseFirestore.getInstance().collection("courses").document(inviteCode);

                                        Map<String, Object> dataToSave = new HashMap<String, Object>();
                                        dataToSave.put("courseTitle",TextName);
                                        dataToSave.put("courseID",TextID);
                                        dataToSave.put("courseDescription",TextCourseDescription);
                                        dataToSave.put("courseIndex", inviteCode);

                                        colRef.set(dataToSave);
                                        Toast.makeText(CreateCourse.this, "Course Successfully created!", Toast.LENGTH_SHORT).show();
                                        Intent selectMeetTimes = new Intent(CreateCourse.this,SelectCourseTimes.class);


//                    selectMeetTimes.putExtra("courseName", TextName);
                                        selectMeetTimes.putExtra("courseID", inviteCode);
//                    selectMeetTimes.putExtra("courseInviteCode", TextInviteCode);
//                    selectMeetTimes.putExtra("courseSection",TextSection);
//                    selectMeetTimes.putExtra("courseDescription",TextCourseDescription);
                                        startActivity(selectMeetTimes);


                                    }
                                }
                            });


                }
            }
        });
    }




}