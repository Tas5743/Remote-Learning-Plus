package com.example.remote_learning_plus.remotelearningplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class QuizHome_ extends AppCompatActivity {

    // TO DO: Get course
    //String course = getIntent().getStringExtra("course");
    //String course="CMPSC475";
    String course, courseRef, uniqueCourseID;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private CollectionReference quizRef = db.collection("/courses/cmpsc475/quizzes/");
    private CollectionReference quizRef;

    private QuizAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);

        Intent intent = getIntent();
        course = intent.getStringExtra("courseID");
        courseRef = intent.getStringExtra("courseRef");
        uniqueCourseID = intent.getStringExtra("uniqueCourseID");
        quizRef = db.collection("/courses/" + uniqueCourseID + "/quizzes/");
        Log.d("QUIZ_HOME_", "quizRef: " + "/courses/" + uniqueCourseID + "/quizzes/");

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        Query query = quizRef;
        FirestoreRecyclerOptions<Quiz> options = new FirestoreRecyclerOptions.Builder<Quiz>()
                .setQuery(query, Quiz.class)
                .build();
        adapter = new QuizAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.quizRecycler);
        recyclerView.setHasFixedSize(true);
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

    private void openQuizPageActivity(int quizNum, String quizTitle) {
        Intent intent = new Intent(this, QuizPage.class);
        intent.putExtra("quizNum", quizNum);
        intent.putExtra("isNewQuiz", false);
        intent.putExtra("oldTitle", quizTitle);
        intent.putExtra("course", course);
        startActivity(intent);
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