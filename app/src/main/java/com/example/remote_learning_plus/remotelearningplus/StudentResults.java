package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
import android.graphics.Color;
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

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.HashMap;


public class StudentResults extends AppCompatActivity {

    TextView courseCode;
    TextView courseTitle;
    TextView courseSection;
    TextView quizTitle;
    TextView question;
    TextView response1;
    TextView response2;
    TextView response3;
    TextView response4;
    TextView response5;
    Button prev;
    Button next;
    Button submit;
    String student;
    String quizPath;
    String courseSectionStr;
    String quizTitleStr;
    String correct;
    String path;
    int questionnum;
    int totalquestion;
    Long points;
    DocumentReference QuizQuestion;
    DocumentReference studentcopy;
    HashMap<String, String> choices;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        courseCode = findViewById(R.id.courseCode);
        courseTitle = findViewById(R.id.quiz_course_title);
        courseSection = findViewById(R.id.quiz_section);
        quizTitle = findViewById(R.id.quizTitle);
        question = findViewById(R.id.quiz_question);
        response1 = findViewById(R.id.option1);
        response2 = findViewById(R.id.option2);
        response3 = findViewById(R.id.option3);
        response4 = findViewById(R.id.option4);
        response5 = findViewById(R.id.option5);
        prev = findViewById(R.id.btn_prev);
        next = findViewById(R.id.btn_next);
        submit = findViewById(R.id.btn_exit_results);


        Bundle query = getIntent().getExtras();
        student = query.getString("student");
        quizPath = query.getString("quiz");
        totalquestion = query.getInt("total");
        String courseCodeStr = query.getString("courseCode");
        String courseTitleStr = query.getString("courseTitle");
        courseSectionStr = query.getString("section");
        quizTitleStr = query.getString("quizTitle");
        questionnum = query.getInt("num");

        courseCode.setText(courseCodeStr);
        courseTitle.setText(courseTitleStr);
        courseSection.setText(courseSectionStr);
        quizTitle.setText(quizTitleStr);


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

                        question.setText(questionnum + ".\n" + document.getData().get("question").toString());
                        points =  (Long) document.getData().get("points");
                        choices = (HashMap<String, String>) document.get("choices");
                        correct = choices.get("correct");

                        if (choices.containsKey("option1")){
                            response1.setText(choices.get("option1"));
                            response1.setVisibility(View.VISIBLE);
                            if(choices.get("option1").equals(choices.get("correct"))){
                                response1.setBackgroundColor(Color.GREEN);
                                response1.setTextColor(Color.BLACK);
                            }}

                        if (choices.containsKey("option2")){
                            response2.setText(choices.get("option2"));
                            response2.setVisibility(View.VISIBLE);
                            if(choices.get("option2").equals(choices.get("correct"))){
                                response2.setBackgroundColor(Color.GREEN);
                                response2.setTextColor(Color.BLACK);
                            }}

                        if (choices.containsKey("option3")){
                            response3.setText(choices.get("option3"));
                            response3.setVisibility(View.VISIBLE);
                            if(choices.get("option3").equals(choices.get("correct"))){
                                response3.setBackgroundColor(Color.GREEN);
                                response3.setTextColor(Color.BLACK);
                            }}

                        if (choices.containsKey("option4")){
                            response4.setText(choices.get("option4"));
                            response4.setVisibility(View.VISIBLE);
                            if(choices.get("option4").equals(choices.get("correct"))){
                                response4.setBackgroundColor(Color.GREEN);
                                response4.setTextColor(Color.BLACK);
                            }}

                        if (choices.containsKey("option5")){
                            response5.setText(choices.get("option5"));
                            response5.setVisibility(View.VISIBLE);
                            if(choices.get("option4").equals(choices.get("correct"))){
                                response5.setBackgroundColor(Color.GREEN);
                                response5.setTextColor(Color.BLACK);
                            }}


//                        for (int i = 0; i < responses .getChildCount(); i++) {
//                            if (i < c.size()){
//                            ((RadioButton) responses.getChildAt(i)).setText(c.get(i));
//                            responses.getChildAt(i).setVisibility(View.VISIBLE);}
//                        }

                        path = QuizQuestion.getParent().getParent().getParent().getParent().getPath()+ "/sections/" + courseSectionStr + "/quizresults/" + quizTitleStr+ "/" + student;
                        studentcopy = FirebaseFirestore.getInstance().document(path  + "/" +questionnum);
                        studentcopy.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                String TAG = "quiz";
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                        if (document.getData().containsKey("response")){
                                            String response = document.getData().get("response").toString();

                                            if(choices.containsKey("option1") && choices.get("option1").equals(response) && !response.equals(choices.get("correct"))){
                                                response1.setTextColor(Color.BLACK);
                                                response1.setBackgroundColor(Color.RED);
                                            }
                                            if(choices.containsKey("option2") && choices.get("option2").equals(response)&& !response.equals(choices.get("correct"))){
                                                response2.setTextColor(Color.BLACK);
                                                response2.setBackgroundColor(Color.RED);
                                            }
                                            if(choices.containsKey("option3") && choices.get("option3").equals(response)&& !response.equals(choices.get("correct"))){
                                                response3.setTextColor(Color.BLACK);
                                                response3.setBackgroundColor(Color.RED);
                                            }
                                            if(choices.containsKey("option4") && choices.get("option4").equals(response)&& !response.equals(choices.get("correct"))){
                                                response4.setTextColor(Color.BLACK);
                                                response4.setBackgroundColor(Color.RED);
                                            }
                                            if(choices.containsKey("option5") && choices.get("option5").equals(response)&& !response.equals(choices.get("correct"))){
                                                response5.setTextColor(Color.BLACK);
                                                response5.setBackgroundColor(Color.RED);
                                            }
                                        }
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
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



        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent previous  = new Intent(StudentResults.this, StudentResults.class);
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
                Intent Next  = new Intent(StudentResults.this, StudentResults.class);
                Next.putExtra("student", student);
                Next.putExtra("quiz",quizPath);
                Next.putExtra("num", questionnum+1);
                Next.putExtra("total",totalquestion);
                Next.putExtra("courseCode",courseCodeStr);
                Next.putExtra("courseTitle", courseTitleStr);
                Next.putExtra("section", courseSectionStr);
                Next.putExtra("quizTitle",quizTitleStr);

                startActivity(Next);

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submit = new Intent(StudentResults.this, quizHome.class);
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



    }
}
