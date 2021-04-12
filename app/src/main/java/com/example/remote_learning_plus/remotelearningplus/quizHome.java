package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
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

public class quizHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);

        Button quizResults = findViewById(R.id.btnQuizResults);
        Button flashcards = findViewById(R.id.btnFlashcards);
        Button score = findViewById(R.id.btnScore);
        Button takeQuiz = findViewById(R.id.btnTakeQuiz);
        TextView quizName = findViewById(R.id.tvQuiz_Name);

        String student;
        String quizPath;
        int totalquestion;
        String courseCodeStr;
        String courseTitleStr;
        String courseSectionStr;
        String quizTitleStr;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            courseCodeStr = bundle.getString("courseCode");
            courseTitleStr = bundle.getString("courseTitle");
            courseSectionStr = bundle.getString("section");
            quizTitleStr = bundle.getString("quizTitle");
            student = bundle.getString("student");
            quizPath = bundle.getString("quiz");
            totalquestion = bundle.getInt("total");
        }
        else {
            student = "student";
            quizPath = "/courses/cmpsc475/quizzes/quiz1";
            totalquestion = 7;
            courseCodeStr = "CMPSC 475";
            courseTitleStr = "Computer Science Course";
            courseSectionStr = "section1";
            quizTitleStr = "quiz1";
        }

        quizName.setText(quizTitleStr);

        DocumentReference QuizQuestion = FirebaseFirestore.getInstance().document(quizPath);
        String path = QuizQuestion.getParent().getParent().getPath() + "/sections/" + courseSectionStr + "/quizresults/" + quizTitleStr + "/" + student+"/grade";
        DocumentReference studentGrade = FirebaseFirestore.getInstance().document(path);
        //Collect grade from student's quiz.
        studentGrade.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String TAG = "studentgrade";
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        quizResults.setVisibility(View.VISIBLE);
                        score.setVisibility(View.VISIBLE);
                        flashcards.setVisibility(View.VISIBLE);
                    } else {
                        takeQuiz.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        takeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quizResources = new Intent(quizHome.this, StudentQuiz.class);
                quizResources.putExtra("student", student);
                quizResources.putExtra("quiz", quizPath);
                quizResources.putExtra("num", 1);
                quizResources.putExtra("total", totalquestion);
                quizResources.putExtra("courseCode", courseCodeStr);
                quizResources.putExtra("courseTitle", courseTitleStr);
                quizResources.putExtra("section", courseSectionStr);
                quizResources.putExtra("quizTitle", quizTitleStr);
                startActivity(quizResources);
            }
        });


        quizResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultsResources = new Intent(quizHome.this, StudentResults.class);
                resultsResources.putExtra("student", student);
                resultsResources.putExtra("quiz", quizPath);
                resultsResources.putExtra("num", 1);
                resultsResources.putExtra("total", totalquestion);
                resultsResources.putExtra("courseCode", courseCodeStr);
                resultsResources.putExtra("courseTitle", courseTitleStr);
                resultsResources.putExtra("section", courseSectionStr);
                resultsResources.putExtra("quizTitle", quizTitleStr);
                startActivity(resultsResources);
            }
        });

        flashcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent flashcardResources = new Intent(quizHome.this, FlashCards.class);
                flashcardResources.putExtra("student", student);
                flashcardResources.putExtra("quiz", quizPath);
                flashcardResources.putExtra("num", 1);
                flashcardResources.putExtra("total", totalquestion);
                flashcardResources.putExtra("courseCode", courseCodeStr);
                flashcardResources.putExtra("courseTitle", courseTitleStr);
                flashcardResources.putExtra("section", courseSectionStr);
                flashcardResources.putExtra("quizTitle", quizTitleStr);
                startActivity(flashcardResources);
            }
        });


        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreResources = new Intent(quizHome.this, quizScore.class);
                scoreResources.putExtra("student", student);
                scoreResources.putExtra("quiz", quizPath);
                scoreResources.putExtra("total", totalquestion);
                scoreResources.putExtra("courseCode", courseCodeStr);
                scoreResources.putExtra("courseTitle", courseTitleStr);
                scoreResources.putExtra("section", courseSectionStr);
                scoreResources.putExtra("quizTitle", quizTitleStr);
                startActivity(scoreResources);
            }
        });

    }


}