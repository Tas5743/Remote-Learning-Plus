package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class CourseInformationPageTeacher extends AppCompatActivity {

    String courseRef, courseDesc, courseID, courseSection;
    private DocumentReference courseDoc;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView txtDesc, txtCourse, txtSection, txtDays, txtTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information_page_teacher);

        txtDesc = findViewById(R.id.txtDesc);
        txtCourse = findViewById(R.id.txtCourse);
        txtSection = findViewById(R.id.txtSection);
        txtDays = findViewById(R.id.txtDays);
        txtTimes = findViewById(R.id.txtTimes);

        Intent intent = getIntent();
        courseRef = intent.getStringExtra("courseRef");

        courseDoc = db.document(courseRef);

        courseDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    txtCourse.setText(documentSnapshot.get("courseId").toString() + " - " + documentSnapshot.get("courseName").toString());
                    txtDesc.setText(documentSnapshot.get("courseDesc").toString());
                    txtSection.setText("Section: " + documentSnapshot.get("courseSection").toString());
                    txtDays.setText(documentSnapshot.get("courseDays").toString().trim());
                    txtTimes.setText("Start: " + documentSnapshot.get("startTime").toString() + "    End: " + documentSnapshot.get("endTime").toString());
                }else{
                    Toast.makeText(CourseInformationPageTeacher.this, "Document does not exist", Toast.LENGTH_SHORT).show();
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
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnHome:
                        Intent intent = new Intent(getApplicationContext(), Home_Teacher.class);
                        startActivity(intent);
                        break;
                    case R.id.btnAdd:
                        Intent intent2 = new Intent(getApplicationContext(), JoinCourse.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });



    }
}