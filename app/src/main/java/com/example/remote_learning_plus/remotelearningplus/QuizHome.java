package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuizHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home2);

        Button quizResults = findViewById(R.id.btnQuizResults);
        Button flashcards = findViewById(R.id.btnFlashcards);
        Button score = findViewById(R.id.btnScore);
        Button takeQuiz = findViewById(R.id.btnTakeQuiz);
        TextView quizName = findViewById(R.id.tvQuiz_Name);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String student;
        String quizPath;
        int totalquestion;
        String courseCodeStr;
        String courseTitleStr;
        String courseSectionStr;
        String quizTitleStr;
        String uniqueCourseID;


        Bundle bundle = getIntent().getExtras();
        courseCodeStr = bundle.getString("courseCode");
        courseTitleStr = bundle.getString("courseTitle");
        courseSectionStr = bundle.getString("section");
        quizTitleStr = bundle.getString("quizTitle");
        student = bundle.getString("student");
        quizPath = bundle.getString("quiz");
        totalquestion = bundle.getInt("total");
        uniqueCourseID = bundle.getString("uniqueCourseID");
        Log.d("QUIZHOME", totalquestion + "");



        quizName.setText(quizTitleStr);

        DocumentReference QuizQuestion = FirebaseFirestore.getInstance().document(quizPath);
        String path = QuizQuestion.getParent().getParent().getPath() + "/section/" + courseSectionStr + "/quizresults/" + quizTitleStr + "/" + student+"/grade";
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
                Intent quizResources = new Intent(QuizHome.this, StudentQuiz.class);
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
                Intent resultsResources = new Intent(QuizHome.this, StudentResults.class);
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
                Intent flashcardResources = new Intent(QuizHome.this, FlashCards.class);
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
                Intent scoreResources = new Intent(QuizHome.this, quizScore.class);
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



        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnHome:
                        Intent intent = new Intent(getApplicationContext(), Home_Student.class);
                        startActivity(intent);
                        break;
                    case R.id.btnAdd:
                        Intent studentQuiz = new Intent(QuizHome.this, StudentQuizzes.class);
                        studentQuiz.putExtra("uniqueCourseID", QuizQuestion.getParent().getParent().getId());
                        studentQuiz.putExtra("courseSection", courseSectionStr);
                        studentQuiz.putExtra("courseCode", courseCodeStr);
                        studentQuiz.putExtra("student", mAuth.getUid());
                        studentQuiz.putExtra("courseTitle", courseTitleStr);
                        startActivity(studentQuiz);
                        break;
                }
                return true;
            }
        });

    }


}