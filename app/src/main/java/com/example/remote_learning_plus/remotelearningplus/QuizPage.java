package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class QuizPage extends AppCompatActivity {

    private static final String TAG = "TAG";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Get course/quizTitle from previous activity
    //Intent intent = getIntent();

    //String course = intent.getStringExtra("course");
    //Boolean isNewQuiz = intent.getBooleanExtra("isNewQuiz", true);
    //int quizNum = intent.getIntExtra("quizNum", 1);
    //String oldTitle = intent.getStringExtra("oldTitle");

    // Test data
    String oldTitle = "Hashmaps";
    String course = "cmpsc475";
    int quizNum = 1;
    Boolean isNewQuiz = false;

    CollectionReference quizzesRef = db.collection("/courses/" + course + "/quizzes");
    Quiz quiz = new Quiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        Button qButton = findViewById(R.id.qButton);
        TextView tvQuizTitle = findViewById(R.id.tvQuizTitle);
        EditText etQuizTitle = findViewById(R.id.etQuizTitle);

        // change values for the activity if quiz is being edited
        if (!isNewQuiz) {

            tvQuizTitle.setText(R.string.edit_quiz_title);
            etQuizTitle.setText(oldTitle);
            qButton.setText(R.string.edit_questions);
        }


        qButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isNewQuiz) {

                    db.document(quizzesRef.getPath() + "/quiz" + quizNum)
                            .update("title", etQuizTitle.getText().toString());
                    openEditQuestionsActivity();

                } else {

                    quiz.setTitle(etQuizTitle.getText().toString());
                    db.document(quizzesRef.getPath() + "/quiz" + quizNum).set(quiz);
                    openCreateQuestionsActivity();
                }
            }
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
        intent.putExtra("quizNum", quizNum+1);
        startActivity(intent);
    }

}