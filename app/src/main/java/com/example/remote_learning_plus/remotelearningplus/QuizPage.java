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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class QuizPage extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Get course/quizTitle from previous activity
    //Intent intent = getIntent();

    //String course = intent.getStringExtra("course");
    //String oldTitle = intent.getStringExtra("quizTitle");
    //Boolean isNewQuiz = intent.getBooleanExtra("isNewQuiz", true);

    // Test data
    String course = "cmpsc475";
    String oldTitle="Hashmaps";
    Boolean isNewQuiz = true;

    CollectionReference quizzesRef = db.collection("/courses/" + course + "/quizzes");
    DocumentReference quizRef;
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

            quizzesRef.whereEqualTo("quizTitle", oldTitle).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        private static final String TAG = "Tag";

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    quizRef = document.getReference();
                                    quiz = document.toObject(Quiz.class);
                                    etQuizTitle.setText(quiz.getTitle());
                                }

                            } else {
                                Log.d(TAG, "error", task.getException());
                            }
                        }
                    });

            tvQuizTitle.setText(R.string.edit_quiz_title);
            //etQuizTitle.setText(oldTitle);
            qButton.setText(R.string.edit_questions);
        }


        qButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quiz.setTitle(etQuizTitle.getText().toString());

                if (!isNewQuiz) {
                    quizRef = db.document("/courses/cmpsc475/quizzes/quiz1");
                    quizRef.update("title", etQuizTitle.getText());

                    openEditQuestionsActivity(etQuizTitle.getText().toString());

                } else {
                    db.document(quizzesRef.getPath() + "/" + quiz.getTitle()).set(quiz);
                    openCreateQuestionsActivity(quiz.getTitle());
                }

            }
        });
    }


    private void openEditQuestionsActivity(String quizTitle) {
        Intent intent = new Intent(this, EditQuestions.class);
        intent.putExtra("quizTitle", quizTitle);
        startActivity(intent);
    }

    private void openCreateQuestionsActivity(String quizTitle) {
        Intent intent = new Intent(this, CreateQuestion.class);
        intent.putExtra("quizTitle", quizTitle);
        startActivity(intent);
    }

}