package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class StudentQuestions extends AppCompatActivity {
    String course;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference courseRef = db.collection("courses").document(course).collection("Questions");
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__student);

        setUpRecyclerView();

        /*adapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                QuestionModel course = documentSnapshot.toObject(QuestionModel.class);
                Intent intent = new Intent(StudentQuestions.this, student_course_home.class);
                intent.putExtra("courseID", course.getCourseID());
                intent.putExtra("courseSection", course.getCourseSection());
                startActivity(intent);
            }
        });*/

        //Navigation Bar
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnHome:
                        Intent intent = new Intent(getApplicationContext(), Home_Student.class);
                        startActivity(intent);
                        break;
                    case R.id.btnAdd:
                        Intent intent2 = new Intent(getApplicationContext(), JoinCourse.class);
                        startActivity(intent2);
                        break;
                }
            }
        });
    }

    private void setUpRecyclerView() {
        Query query = courseRef;

        FirestoreRecyclerOptions<QuestionModel> options = new FirestoreRecyclerOptions.Builder<QuestionModel>()
                .setQuery(query, QuestionModel.class)
                .build();
        adapter = new QuestionAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
