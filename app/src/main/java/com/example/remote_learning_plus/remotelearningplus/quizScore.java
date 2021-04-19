package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;

public class quizScore  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);

        TextView courseCodeView = findViewById(R.id.courseCode);
        TextView courseTitleView = findViewById(R.id.quiz_course_title);
        TextView courseSectionView = findViewById(R.id.quiz_section);
        TextView quizTitleView = findViewById(R.id.quizTitle);
        TextView fractionView = findViewById(R.id.fraction);
        TextView percentageView = findViewById(R.id.percentage);
        Button quizHome = findViewById(R.id.btn_exit);

        String courseCode;
        String courseTitle;
        String courseSection;
        String quizTitle;
        String student;
        String quizPath;
        int totalquestion;

        Bundle bundle = getIntent().getExtras();
        courseCode = bundle.getString("courseCode");
        courseTitle = bundle.getString("courseTitle");
        courseSection = bundle.getString("section");
        quizTitle = bundle.getString("quizTitle");
        student = bundle.getString("student");
        quizPath = bundle.getString("quiz");
        totalquestion = bundle.getInt("total");



        // /courses/cmpsc475/sections/section1/quizresults/quiz1/student
        // "courses/cmpsc475/quizzes/quiz1";
        courseCodeView.setText(courseCode);
        courseTitleView.setText(courseTitle);
        courseSectionView.setText(courseSection);
        quizTitleView.setText(quizTitle);


        DocumentReference QuizQuestion = FirebaseFirestore.getInstance().document(quizPath);
        String path = QuizQuestion.getParent().getParent().getPath() + "/sections/" + courseSection + "/quizresults/" + quizTitle + "/" + student+"/grade";
        DocumentReference studentGrade = FirebaseFirestore.getInstance().document(path);
        //Collect grade from student's quiz.
        studentGrade.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String TAG = "studentgrade";
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        fractionView.setText(document.getData().get("fraction").toString());
                        percentageView.setText(document.getData().get("percentage").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        quizHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submit = new Intent(quizScore.this, QuizHome.class);;
                submit.putExtra("student", student);
                submit.putExtra("quiz", quizPath);
                submit.putExtra("total", totalquestion);
                submit.putExtra("courseCode", courseCode);
                submit.putExtra("courseTitle", courseTitle);
                submit.putExtra("section", courseSection);
                submit.putExtra("quizTitle", quizTitle);
                startActivity(submit);

            }
        });
    }

}
