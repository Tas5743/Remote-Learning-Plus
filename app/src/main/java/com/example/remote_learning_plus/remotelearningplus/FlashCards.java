package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.HashMap;

public class FlashCards extends AppCompatActivity {

    TextView courseCode;
    TextView courseTitle;
    TextView courseSection;
    TextView Title;
    TextView card;
    Button prev;
    Button next;
    Button exit;
    Button flipcard;
    String quizPath;
    String courseSectionStr;
    String quizTitleStr;
    String question;
    String correct;
    int questionnum;
    DocumentReference QuizQuestion;
    HashMap<String, String> choices;
    boolean flipped;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        courseCode = findViewById(R.id.courseCodeflash);
        courseTitle = findViewById(R.id.flash_course_title);
        courseSection = findViewById(R.id.flash_section);
        Title = findViewById(R.id.flashTitle);
        card = findViewById(R.id.flashtext);

        prev = findViewById(R.id.btn_prev_flash);
        next = findViewById(R.id.btn_next_flash);
        exit = findViewById(R.id.btn_exit_flash);
        flipcard = findViewById(R.id.Flip_card);

        //Bundle query = getIntent().getExtras();
        //String student = query.getString("student");
        //String quizPath = query.getString("quizPath");
        //int questionnum = query.getInt("num");
        //int totalquestion = query.getInt("total");
        quizPath = "courses/cmpsc475/quizzes/quiz1/questions/question1";
        questionnum = 1;
        int totalquestion = 1;

//       String courseCodeStr = query.getString("courseCode");
//       String courseTitleStr = query.getString("courseTitle");
//       String courseSectionStr = query.getString("section");
//       String quizTitleStrquery = query.getString("quizTitle");

        String courseCodeStr = "CMPSC475";
        String courseTitleStr = "App Development";
        courseSectionStr = "section1";
        quizTitleStr = "quiz1";

        courseCode.setText(courseCodeStr);
        courseTitle.setText(courseTitleStr);
        courseSection.setText(courseSectionStr);
        Title.setText(quizTitleStr);
        flipped = false;


        if (questionnum == 1){
            prev.setVisibility(View.INVISIBLE);
        }
        if (questionnum == totalquestion){
            next.setVisibility(View.INVISIBLE);
        }

        //QuizQuestion = FirebaseFirestore.getInstance().document(quiz+"/"+questionnum);
        QuizQuestion = FirebaseFirestore.getInstance().document(quizPath);

        QuizQuestion.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String TAG = "quiz";
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        question = document.getData().get("question").toString();
                        choices = (HashMap<String, String>) document.get("choices");
                        correct = choices.get("correct");
                        card.setText(question);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent previous  = new Intent(FlashCards.this,FlashCards.class);
                previous.putExtra("quiz",quizPath);
                //previous.putExtra("quiz",quiz + "/" + questionnum);
                previous.putExtra("num", questionnum-1);
                previous.putExtra("total",totalquestion);
                previous.putExtra("courseCode",courseCodeStr);
                previous.putExtra("courseTitle", courseTitleStr);
                previous.putExtra("section", courseSectionStr);
                previous.putExtra("quizTitle",quizTitleStr);

                startActivity(previous);

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Next  = new Intent(FlashCards.this, FlashCards.class);
                Next.putExtra("quiz",quizPath);
                //Next.putExtra("quiz",quiz + "/" + questionnum);
                Next.putExtra("num", questionnum+1);
                Next.putExtra("total",totalquestion);
                Next.putExtra("courseCode",courseCodeStr);
                Next.putExtra("courseTitle", courseTitleStr);
                Next.putExtra("section", courseSectionStr);
                Next.putExtra("quizTitle",quizTitleStr);

                startActivity(Next);

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Submit = new Intent(FlashCards.this, Student_HomePage.class);
                //startActivity(Submit);
            }
        });

        flipcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flipped){
                    card.setText(question);
                    flipped = false;
                }
                else {card.setText(correct);
                    flipped = true;}

            }
        });



    }
}

