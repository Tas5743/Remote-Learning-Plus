package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class CourseInformationPage extends AppCompatActivity {

    String courseRef, courseDesc, courseID, courseSection;
    private DocumentReference courseDoc;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView txtDesc, txtCourse, txtSection, txtDays, txtTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information_page);

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
                    Toast.makeText(CourseInformationPage.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }
}