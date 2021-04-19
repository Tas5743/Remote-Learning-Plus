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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class StudentQuizzes extends AppCompatActivity {
    private static String TAG = "Tag";

    String courseRef, uniqueCourseID, student, courseCodeStr, courseTitleStr;


    //String student = getIntent().getStringExtra("student");
    //String courseCodeStr = getIntent().getStringExtra("courseCodeStr");
    //String courseTitleStr = getIntent().getStringExtra("courseTitleStr");
    //String courseSectionStr = getIntent().getStringExtra("courseSectionStr");
    //String quizzesPath ="/courses/" + "0915U1AvP" + "/quizzes/";

    String courseSectionStr;
    int quizItems;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private CollectionReference quizRef = db.collection(quizzesPath);
    private CollectionReference quizRef;
    private QuizAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_quizzes);


        //***************** Passing courseRef ****************
        Intent intent = getIntent();
        courseRef = intent.getStringExtra("courseRef");
        uniqueCourseID = intent.getStringExtra("uniqueCourseID");
        courseSectionStr = intent.getStringExtra("courseSection");
        student = intent.getStringExtra("student");
        courseCodeStr = intent.getStringExtra("courseCode");
        courseTitleStr = intent.getStringExtra("courseTitle");
        Log.d("STUDENT_QUIZZES", "courses/" + uniqueCourseID + "/quizzes/");
        quizRef = db.collection("courses/" + uniqueCourseID + "/quizzes/");

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
                        Log.d(StudentQuizzes.TAG, Integer.toString(position));
                        openQuizPageActivity(position+1, quizTitle);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        String quizTitle = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.quizTitle)).getText().toString();
                        System.out.println(position);

                        openQuizPageActivity(position+1, quizTitle);
                    }
                })
        );

    }

    private void openQuizPageActivity(int quizNum, String quizTitleStr) {
        Intent submit = new Intent(StudentQuizzes.this, QuizHome.class);

        String quizPath = "courses/" + uniqueCourseID + "/quizzes/quiz" + quizNum;
        db.document(quizPath).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Quiz quiz = documentSnapshot.toObject(Quiz.class);
                quizItems = Integer.parseInt(documentSnapshot.get("items").toString());
                submit.putExtra("total", quizItems);
                Log.d(TAG, "quiz items = " + quizItems);
                submit.putExtra("student", student);
                submit.putExtra("quiz", quizPath);
                submit.putExtra("courseCode", courseCodeStr);
                submit.putExtra("courseTitle", courseTitleStr);
                submit.putExtra("section", courseSectionStr);
                submit.putExtra("quizTitle", quizTitleStr);
                submit.putExtra("uniqueCourseID", uniqueCourseID);
                startActivity(submit);

            }
        });



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

    private class TAG {
    }
}