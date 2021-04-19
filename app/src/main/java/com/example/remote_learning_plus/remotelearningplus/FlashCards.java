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
//    TextView card;
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
        setContentView(R.layout.activity_flash_cards);

        courseCode = findViewById(R.id.courseCodeflash);
        courseTitle = findViewById(R.id.flash_course_title);
        courseSection = findViewById(R.id.flash_section);
        Title = findViewById(R.id.flashTitle);
        //card = findViewById(R.id.flashtext);

        prev = findViewById(R.id.btn_prev_flash);
        next = findViewById(R.id.btn_next_flash);
        exit = findViewById(R.id.btn_exit_flash);
        flipcard = findViewById(R.id.Flip_card);

        Bundle query = getIntent().getExtras();
        String student = query.getString("student");
        quizPath = query.getString("quiz");
        int totalquestion = query.getInt("total");
        questionnum = query.getInt("num");
        String courseCodeStr = query.getString("courseCode");
        String courseTitleStr = query.getString("courseTitle");
        courseSectionStr = query.getString("section");
        quizTitleStr = query.getString("quizTitle");




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

        QuizQuestion = FirebaseFirestore.getInstance().document(quizPath+"/questions/"+questionnum);


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
                        flipcard.setText(question);

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
                previous.putExtra("student", student);
                previous.putExtra("quiz",quizPath);
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
                Intent next  = new Intent(FlashCards.this, FlashCards.class);
                next.putExtra("student", student);
                next.putExtra("quiz",quizPath);
                next.putExtra("num", questionnum+1);
                next.putExtra("total",totalquestion);
                next.putExtra("courseCode",courseCodeStr);
                next.putExtra("courseTitle", courseTitleStr);
                next.putExtra("section", courseSectionStr);
                next.putExtra("quizTitle",quizTitleStr);

                startActivity(next);

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent submit = new Intent(FlashCards.this, QuizHome.class);
                submit.putExtra("student", student);
                submit.putExtra("quiz", quizPath);
                submit.putExtra("total", totalquestion);
                submit.putExtra("courseCode", courseCodeStr);
                submit.putExtra("courseTitle", courseTitleStr);
                submit.putExtra("section", courseSectionStr);
                submit.putExtra("quizTitle", quizTitleStr);
                startActivity(submit);
            }
        });

        flipcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flipped){
                    flipcard.setText(question);
                    flipped = false;
                }
                else {flipcard.setText(correct);
                    flipped = true;}

            }
        });



    }
}