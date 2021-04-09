package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private  CollectionReference courseRef;
    private QuestionAdapter adapter;
    String courseID, courseSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__student);

        Intent intent = getIntent();
        courseID = intent.getStringExtra("courseID");
        courseSection = intent.getStringExtra("courseSection");
        Log.d("getStringExtra", "id:" + courseID + " section: " + courseSection);
        courseRef = db.collection("courses/" + courseID + "/section/" + courseSection + "/questions");

        setUpRecyclerView();

        adapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                QuestionModel question = documentSnapshot.toObject(QuestionModel.class);
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentQuestions.this);
                builder.setMessage(question.getQuestion())
                        .setTitle(question.getStudent())
                        .setPositiveButton(R.string.answered, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.document("courses/" + courseID + "/section/" + courseSection + "/questions/" + documentSnapshot.getId())
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("StudentQuestions", "Question Deletion Successful");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("StudentQuestions", "Question Deletion Failed", e);
                                                    }
                                                });
                                    }
                                })
                        .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // exit dialog
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Navigation Bar
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnHome:
                        Intent intent = new Intent(getApplicationContext(), Home_Teacher.class);
                        startActivity(intent);
                        break;
                    case R.id.btnAdd:
                        Intent intent2 = new Intent(getApplicationContext(), CreateCourse.class);
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
