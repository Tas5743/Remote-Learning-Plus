package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Student_Ask_Question extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    CheckBox cBoxHideName;
    Boolean hideName = false;
    EditText edtTxtQuestion;
    Button button;
    String courseID, courseSection, studentUID, studentName, questionText;
    DocumentReference docRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_ask_question);

        studentUID = mAuth.getCurrentUser().getUid();
        docRef = db.collection("users").document(studentUID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    studentName = documentSnapshot.getString("username");
                    Log.d("getStudentName", studentName);
                }else{
                    Toast.makeText(Student_Ask_Question.this, "Document does not exist.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        cBoxHideName = findViewById(R.id.cBoxHideName);

        Intent intent = getIntent();
        courseID = intent.getStringExtra("courseID");
        courseSection= intent.getStringExtra("courseSection");

        edtTxtQuestion = findViewById(R.id.etQuestion);

        button = findViewById(R.id.btnSubmit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference log = db.collection("courses/" + courseID + "/section/" + courseSection + "/questions").document(studentName);
                HashMap<String,Object> croissant = new HashMap<>();
                questionText = edtTxtQuestion.getText().toString();
                if(!hideName){
                    croissant.put("student", studentName);
                }else{
                    croissant.put("student", "anonymous");
                }
                croissant.put("question", questionText);
                log.set(croissant);
                Toast.makeText(Student_Ask_Question.this,"Question posted.", Toast.LENGTH_SHORT).show();
                Intent homepage = new Intent(Student_Ask_Question.this, Home_Student.class);
                startActivity(homepage);
            }
        });

    }

    public void onCheckboxClicked(View view){
        boolean checked = cBoxHideName.isChecked();
        hideName = checked;
        Log.d("isChecked", hideName.toString());
    }
}
