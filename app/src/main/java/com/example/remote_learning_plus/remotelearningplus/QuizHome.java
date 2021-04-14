package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class QuizHome extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private QuizAdapter adapter;


    /* TODO: Data from previous activity needed:
        Course Page -> Quiz Home
        - "course" - as in course id as written in Firestore
        - intent.putExtra("course", course); // copy this
    */


    // Intent data
    Intent intent;
    String course;
    CollectionReference quizRef;

    // Data for testing
    // private CollectionReference quizRef = db.collection("/courses/cmpsc475/quizzes/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);
        setUpRecyclerView();

        intent = getIntent();
        course=intent.getStringExtra("course");
        quizRef = db.collection("/courses/" + course + "/quizzes");


        // Bottom navigation

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnHome:
                        Intent intent = new Intent(getApplicationContext(), Home_Teacher.class);
                        startActivity(intent);
                        break;
                    case R.id.btnAdd:
                        openNewQuizPageActivity(count());
                        break;
                }
                return true;
            }
        });

    }

    private void setUpRecyclerView() {

        FirestoreRecyclerOptions<Quiz> options = new FirestoreRecyclerOptions.Builder<Quiz>()
                .setQuery(quizRef, Quiz.class)
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
                        String quizTitle = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position))
                                .itemView.findViewById(R.id.quizTitle)).getText().toString();
                        openQuizPageActivity(position, quizTitle);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        String quizTitle = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position))
                                .itemView.findViewById(R.id.quizTitle)).getText().toString();
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

    private void openNewQuizPageActivity(int quizNum) {
        Intent intent = new Intent(this, QuizPage.class);
        intent.putExtra("quizNum", quizNum);
        intent.putExtra("isNewQuiz", true);
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


    // TODO: if creating new quiz, count the number of quizzes already existing
    //  Pass this along with intent

    public int count () {
        final int[] numQuizzes = {0};
        db.collection(quizRef.getPath())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    private static final String TAG = "Tag";

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                numQuizzes[0]++;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return numQuizzes[0];
    }
}