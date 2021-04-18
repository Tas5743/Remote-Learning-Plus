package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class QuizPage extends AppCompatActivity {

    // Quiz home -> Create/Edit quiz page

    private static final String TAG = "TAG";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Intent intent;
    String course;
    Boolean isNewQuiz;
    int quizNum;
    String oldTitle;
    CollectionReference quizzesRef;
    Button qButton;
    TextView tvQuizTitle;
    EditText etQuizTitle;
    /*Test data
    String oldTitle = "Hashmaps";
    String course = "cmpsc475";
    int quizNum = 1;
    Boolean isNewQuiz = false;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        intent = getIntent();
        course = intent.getStringExtra("course");
        isNewQuiz = intent.getBooleanExtra("isNewQuiz", true);
        quizNum = intent.getIntExtra("quizNum", 1) +1;
        quizzesRef = db.collection("/courses/" + course + "/quizzes");
        oldTitle = intent.getStringExtra("oldTitle");

        qButton = findViewById(R.id.qButton);
        tvQuizTitle = findViewById(R.id.tvQuizTitle);
        etQuizTitle = findViewById(R.id.etQuizTitle);

        if (!isNewQuiz) {
            tvQuizTitle.setText(R.string.edit_quiz_title);
            etQuizTitle.setText(oldTitle);
            qButton.setText(R.string.edit_questions);
        }

         qButton.setOnClickListener(v -> {

             if (isNewQuiz) {
                 Quiz quiz = new Quiz();
                 quizNum--;
                 quiz.setTitle(etQuizTitle.getText().toString());
                 db.document("/courses/" + course + "/quizzes/quiz" + quizNum).set(quiz);
                 openCreateQuestionsActivity();

             } else {
                 db.document(quizzesRef.getPath() + "/quiz" + quizNum)
                         .update("title", etQuizTitle.getText().toString());
                 openEditQuestionsActivity();

             }
         });

        // Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.btnHome:
                    Intent intent = new Intent(getApplicationContext(), Home_Teacher.class);
                    startActivity(intent);
                    break;
                case R.id.btnAccount:
                    Intent intent2 = new Intent(getApplicationContext(), CourseInformationPage.class);
                    startActivity(intent2);
                    break;
            }
            return true;
        });

    }

    private void openEditQuestionsActivity() {
        Intent intent = new Intent(this, EditQuestions.class);
        String quizPath = quizzesRef.getPath() + "/quiz" + quizNum;
        intent.putExtra("quizPath", quizPath);
        intent.putExtra("course", course);
        intent.putExtra("quizNum", quizNum);
        startActivity(intent);
    }

    private void openCreateQuestionsActivity() {
        Intent intent = new Intent(this, CreateQuestion.class);
        intent.putExtra("course", course);
        intent.putExtra("quizNum", quizNum);
        startActivity(intent);

    }

}