package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class CreateQuiz extends AppCompatActivity {

    private static final String TAG = "Tag";
    // TO DO: Get course and quiz
    String course="CMPSC475";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final DocumentReference courseRef = FirebaseFirestore.getInstance().document("/courses/" + course);
    CollectionReference quizzesRef = FirebaseFirestore.getInstance().collection(courseRef.toString() + "/quizzes");
    QuizRecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);


        ArrayList<Quiz> quizzes = new ArrayList<>();

        quizzesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        quizzes.add(document.toObject(Quiz.class));
                        Log.d(TAG, document.getId() + " => " + document.getData());

                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());

                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuizRecycleViewAdapter(this, quizzes);
        adapter.setClickListener((QuizRecycleViewAdapter.ItemClickListener) this);
        recyclerView.setAdapter(adapter);


    }
}