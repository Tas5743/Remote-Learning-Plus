package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class StudentQuizzes extends AppCompatActivity {

    /*Intent intent = getIntent();
    String student = getIntent().getStringExtra("student");
    String courseCodeStr = getIntent().getStringExtra("courseCodeStr");
    String courseTitleStr = getIntent().getStringExtra("courseTitleStr");
    String courseSectionStr = getIntent().getStringExtra("courseSectionStr");*/

    String student = "student";
    String courseCodeStr = "cmpsc475";
    String courseTitleStr = "Hashmaps";
    String courseSectionStr = "1";
    int quizItems;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference quizRef = db.collection("/courses/cmpsc475/quizzes/");
    private QuizAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_quizzes);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        Query query = quizRef;
        FirestoreRecyclerOptions<Quiz> options = new FirestoreRecyclerOptions.Builder<Quiz>()
                .setQuery(query, Quiz.class)
                .build();
        adapter = new QuizAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.quizRecycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String quizTitle = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.quizTitle)).getText().toString();
                        openQuizPageActivity(position, quizTitle);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        String quizTitle = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.quizTitle)).getText().toString();
                        openQuizPageActivity(position, quizTitle);
                    }
                })
        );

    }

    private void openQuizPageActivity(int quizNum, String quizTitleStr) {
        Intent submit = new Intent(StudentQuizzes.this, quizHome.class);

        String quizPath = "courses/" + courseCodeStr + "quizzes/quiz/" + quizNum;
        db.document(quizPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            private static final String TAG = "TAG";

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        quizItems = (int) documentSnapshot.get("items");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Failed with", task.getException());
                }
            }});

        submit.putExtra("student", student);
        submit.putExtra("quiz", quizPath);
        submit.putExtra("total", quizItems);
        submit.putExtra("courseCode", courseCodeStr);
        submit.putExtra("courseTitle", courseTitleStr);
        submit.putExtra("section", courseSectionStr);
        submit.putExtra("quizTitle", quizTitleStr);
        startActivity(submit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}