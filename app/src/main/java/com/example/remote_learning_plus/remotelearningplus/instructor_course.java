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

public class instructor_course extends AppCompatActivity implements View.OnClickListener{
    TextView courseName;
    String courseID, courseSection, courseRef, uniqueCourseID;
    Button btnQuiz, btnResource, btnClassList, btnInfo, btnStats, btnQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_course);

        Intent intent = getIntent();
        courseName = findViewById(R.id.tvCourse_Name);
        courseID = intent.getStringExtra("courseID");
        courseSection = intent.getStringExtra("courseSection");
        courseRef = intent.getStringExtra("courseRef");
        Log.d("INSTRUCTOR_COURSE", courseRef + "");
        courseName.setText(courseID);

        btnQuiz = findViewById(R.id.btnQuizzes);
        btnResource = findViewById(R.id.btnResources);
        btnClassList = findViewById(R.id.btnClass_List);
        btnInfo = findViewById(R.id.btnInformation);
        btnStats = findViewById(R.id.btnStatistics);
        btnQuestions = findViewById(R.id.btnQuestions);

        btnQuiz.setOnClickListener(this);
        btnResource.setOnClickListener(this);
        btnClassList.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnStats.setOnClickListener(this);
        btnQuestions.setOnClickListener(this);

        DocumentReference courseDoc = FirebaseFirestore.getInstance().document(courseRef);
        courseDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    uniqueCourseID = documentSnapshot.get("uniqueCourseID").toString();
                    Log.d("INSTRUCTOR_COURSE", "uniqueCourseID: " + uniqueCourseID);
                }else{
                    Toast.makeText(instructor_course.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        //Navigation Bar
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnHome:
                        Intent intent2 = new Intent(getApplicationContext(), Home_Teacher.class);
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
                Intent intentQuizzes = new Intent(instructor_course.this, QuizHome_.class);
                intentQuizzes.putExtra("courseID", courseID);
                intentQuizzes.putExtra("courseRef", courseRef);
                intentQuizzes.putExtra("uniqueCourseID", uniqueCourseID);
                startActivity(intentQuizzes);
                break;
            case R.id.btnResources:
                break;
            case R.id.btnClass_List:
                break;
            case R.id.btnInformation:
                break;
            case R.id.btnStatistics:
                break;
            case R.id.btnQuestions:
                Intent intent = new Intent(instructor_course.this, StudentQuestions.class);
                intent.putExtra("courseID", courseID);
                intent.putExtra("courseSection", courseSection);
                intent.putExtra("courseRef", courseRef);
                startActivity(intent);
                break;
        }
    }
}
