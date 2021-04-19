package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class student_course_home extends AppCompatActivity implements View.OnClickListener {
    TextView txtCourseName;
    String courseID, courseSection, courseRef, uniqueCourseID;
    Button btnQuiz, btnResources, btnResults, btnInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_home);

        //btnAskQuestion = findViewById(R.id.btnAsk);
        btnQuiz = findViewById(R.id.btnQuizzes);
        btnResources = findViewById(R.id.btnResources);
        btnResults = findViewById(R.id.btnResults);
        btnInformation = findViewById(R.id.btnInformation);

        //btnAskQuestion.setOnClickListener(this);
        btnQuiz.setOnClickListener(this);
        btnResources.setOnClickListener(this);
        btnResults.setOnClickListener(this);
        btnInformation.setOnClickListener(this);

        Intent intent = getIntent();
        txtCourseName = findViewById(R.id.tvCourse_Name);
        courseID = intent.getStringExtra("courseID");
        courseSection = intent.getStringExtra("courseSection");
        courseRef = intent.getStringExtra("courseRef");
        Log.d("STUDENT_COURSE_HOME", courseRef);
        txtCourseName.setText(courseID);

        DocumentReference courseDoc = FirebaseFirestore.getInstance().document(courseRef);
        courseDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    uniqueCourseID = documentSnapshot.get("uniqueCourseID").toString();
                }else{
                    Toast.makeText(student_course_home.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btnHome:
                        Intent intent = new Intent(getApplicationContext(), Home_Student.class);
                        startActivity(intent);
                        break;
                    case R.id.btnAdd:
                        Intent intent2 = new Intent(getApplicationContext(), Student_Ask_Question.class);
                        intent2.putExtra("courseID", courseID);
                        intent2.putExtra("courseSection", courseSection);
                        intent2.putExtra("courseRef", courseRef);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnQuizzes:
                Intent studentQuiz = new Intent(this, StudentQuizzes.class);
                studentQuiz.putExtra("courseRef", courseRef);
                studentQuiz.putExtra("uniqueCourseID", uniqueCourseID);
                startActivity(studentQuiz);
                break;
            case R.id.btnResources:
                break;
            case R.id.btnResults:
                break;
            case R.id.btnInformation:
                Intent courseInfo = new Intent(this, CourseInformationPage.class);
                courseInfo.putExtra("courseRef", courseRef);
                startActivity(courseInfo);
                break;
        }
    }
}