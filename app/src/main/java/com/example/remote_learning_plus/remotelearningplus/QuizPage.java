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
    //String oldTitle = intent.getStringExtra("quizTitle");
    //Boolean isNewQuiz = intent.getBooleanExtra("isNewQuiz", true);

    // Test data
    String course = "cmpsc475";
    String oldTitle="quiz1";
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

            quizzesRef.document(oldTitle).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    quiz = documentSnapshot.toObject(Quiz.class);
                }
            });

            tvQuizTitle.setText(R.string.edit_quiz_title);
            etQuizTitle.setText(oldTitle);
            qButton.setText(R.string.edit_questions);
        }


        qButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quiz.setTitle(etQuizTitle.getText().toString());
                db.document(quizzesRef.getPath() + "/" + quiz.getTitle()).set(quiz);


                if (!isNewQuiz) {

                    // move questions collection to this new quiz document
                    // delete doc and sub collection

                    openEditQuestionsActivity(etQuizTitle.getText().toString());

                } else {
                    openCreateQuestionsActivity(quiz.getTitle());
                }
            }
        });
    }



    public void moveFirestoreCollection(CollectionReference fromPath, final CollectionReference toPath) {
        fromPath.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        toPath.set(document.getData())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                        fromPath.delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error deleting document", e);
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
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
        Intent intent = new Intent(this, EditQuestions.class);
        intent.putExtra("quizTitle", quizTitle);
        startActivity(intent);
    }

}