package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mifmif.common.regex.Generex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseInformation extends AppCompatActivity {
    DocumentReference colRef;
    Button btn;
    EditText edtTxtName, edtTxtId, edtTxtDesc;
    String name, id, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);
        
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

        // Initialize EditText Fields
        edtTxtName = findViewById(R.id.etCourse_name);
        edtTxtId = findViewById(R.id.etCourse_id);
        edtTxtDesc = findViewById(R.id.etCourse_description);


        // Initialize Button
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store EditText Input
                name = edtTxtName.getText().toString();
                id = edtTxtId.getText().toString();
                desc = edtTxtDesc.getText().toString();

                if(name.isEmpty()){
                    edtTxtName.setError("Please enter a course name.");
                    edtTxtName.requestFocus();
                }
                else if(id.isEmpty()){
                    edtTxtId.setError("Please enter a course ID.");
                    edtTxtId.requestFocus();
                }
                else if(desc.isEmpty()){
                    edtTxtDesc.setError("Please enter a course description.");
                    edtTxtDesc.requestFocus();
                }
                else {
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
                                        String uniqueCourseID = "o";
                                        while (!unique) {
                                            Generex generex = new Generex("[A-Za-z0-9]{9}");
                                            uniqueCourseID = generex.random();
                                            if (
                                                    !sections.contains(uniqueCourseID)) {
                                                unique = true;
                                            }
                                        }
                                        //colRef = FirebaseFirestore.getInstance().collection("courses").document(uniqueClassID);

                                        //Map<String, Object> dataToSave = new HashMap<String, Object>();
                                        //dataToSave.put("courseTitle", edtTxtName);
                                        //dataToSave.put("courseID", edtTxtId);
                                        //dataToSave.put("courseDescription", edtTxtDesc);
                                        //dataToSave.put("courseIndex", uniqueClassID);

                                        //colRef.set(dataToSave);
                                        Toast.makeText(CourseInformation.this, "Course Successfully created!", Toast.LENGTH_SHORT).show();
                                        Intent selectMeetTimes = new Intent(CourseInformation.this, SelectCourseTimes.class);
                                        selectMeetTimes.putExtra("courseName", name);
                                        selectMeetTimes.putExtra("courseId", id);
                                        selectMeetTimes.putExtra("courseDesc", desc);
                                        selectMeetTimes.putExtra("uniqueCourseID", uniqueCourseID);
                                        startActivity(selectMeetTimes);
                                    }
                                }
                            });
                }
            }
        });
    }


}
