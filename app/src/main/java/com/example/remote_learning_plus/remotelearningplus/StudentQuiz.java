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


public class StudentQuiz extends AppCompatActivity {

    TextView courseCode;
    TextView courseTitle;
    TextView courseSection;
    TextView quizTitle;
    TextView question;
    RadioGroup responses;
    RadioButton response1;
    RadioButton response2;
    RadioButton response3;
    RadioButton response4;
    //  RadioButton response5;
    Button prev;
    Button next;
    Button submit;
    String student;
    String quizPath;
    String courseSectionStr;
    String quizTitleStr;
    String correct;
    String path;
    Integer questionnum;
    Integer totalquestion;
    Long points;
    DocumentReference QuizQuestion;
    DocumentReference studentcopy;
    HashMap<String, String> choices;

    private void save() {
        RadioButton selected = (RadioButton) findViewById(responses.getCheckedRadioButtonId());
        HashMap<String, Object> answer = new HashMap<>();
        Long grade = 0L;

        if (!(selected == null)) {
            answer.put("response", selected.getText().toString());
            if (selected.getText().toString().equals(correct)) {
                grade = points;
            }
        }
        answer.put("grade", grade);
        studentcopy.set(answer);


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_quiz);

        courseCode = findViewById(R.id.courseCode);
        courseTitle = findViewById(R.id.quiz_course_title);
        courseSection = findViewById(R.id.quiz_section);
        quizTitle = findViewById(R.id.quizTitle);
        question = findViewById(R.id.quiz_question);
        responses = findViewById(R.id.quiz_responses);
        response1 = findViewById(R.id.response1);
        response2 = findViewById(R.id.response2);
        response3 = findViewById(R.id.response3);
        response4 = findViewById(R.id.response4);
//        response5 = findViewById(R.id.response5);
        prev = findViewById(R.id.btn_prev);
        next = findViewById(R.id.btn_next);
        submit = findViewById(R.id.btn_submit_quiz);

        Intent intent = getIntent();
        Bundle query = getIntent().getExtras();
        student = query.getString("student");
        quizPath = query.getString("quiz");
        totalquestion = intent.getIntExtra("total", 1);
        Log.d("STUDENT_QUIZ", "totalquestion: " + totalquestion);
        String courseCodeStr = query.getString("courseCode");
        String courseTitleStr = query.getString("courseTitle");
        courseSectionStr = query.getString("section");
        quizTitleStr = query.getString("quizTitle");
        questionnum = query.getInt("num");
        courseCode.setText(courseCodeStr);
        courseTitle.setText(courseTitleStr);
        courseSection.setText(courseSectionStr);
        quizTitle.setText(quizTitleStr);


        if (questionnum == 1) {
            prev.setVisibility(View.INVISIBLE);
        }
        if (questionnum.equals(totalquestion)) {
            next.setVisibility(View.INVISIBLE);
        }

        QuizQuestion = FirebaseFirestore.getInstance().document(quizPath + "/questions/" + questionnum);
        QuizQuestion.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String TAG = "quiz";
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        question.setText(questionnum + ".\n" + document.getData().get("question").toString());
                        points = (Long) document.getData().get("point");
                        choices = (HashMap<String, String>) document.get("choices");
                        correct = choices.get("correct");

                        if (choices.containsKey("option1")) {
                            response1.setText(choices.get("option1"));
                            response1.setVisibility(View.VISIBLE);
                        }

                        if (choices.containsKey("option2")) {
                            response2.setText(choices.get("option2"));
                            response2.setVisibility(View.VISIBLE);
                        }

                        if (choices.containsKey("option3")) {
                            response3.setText(choices.get("option3"));
                            response3.setVisibility(View.VISIBLE);
                        }

                        if (choices.containsKey("option4")) {
                            response4.setText(choices.get("option4"));
                            response4.setVisibility(View.VISIBLE);
                        }

//                        if (choices.containsKey("option5")){
//                            response5.setText(choices.get("option5"));
//                            response5.setVisibility(View.VISIBLE);}


//                        for (int i = 0; i < responses .getChildCount(); i++) {
//                            if (i < c.size()){
//                            ((RadioButton) responses.getChildAt(i)).setText(c.get(i));
//                            responses.getChildAt(i).setVisibility(View.VISIBLE);}
//                        }

                        path = QuizQuestion.getParent().getParent().getParent().getParent().getPath() + "/section/" + courseSectionStr + "/quizresults/" + quizTitleStr + "/" + student;
                        Log.d("STUDENTQUIZ", courseSectionStr);
                        studentcopy = FirebaseFirestore.getInstance().document(path + "/" + questionnum);
                        studentcopy.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                String TAG = "quiz";
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                        if (document.getData().containsKey("response")) {
                                            String response = document.getData().get("response").toString();

                                            if (choices.containsKey("option1") && choices.get("option1").equals(response)) {
                                                response1.toggle();
                                            }
                                            if (choices.containsKey("option2") && choices.get("option2").equals(response)) {
                                                response2.toggle();
                                            }
                                            if (choices.containsKey("option3") && choices.get("option3").equals(response)) {
                                                response3.toggle();
                                            }
                                            if (choices.containsKey("option4") && choices.get("option4").equals(response)) {
                                                response4.toggle();
                                            }
//                                            if(choices.containsKey("option5") && choices.get("option5").equals(response)){
//                                                response5.toggle();
//                                            }
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
                save();
                Intent previous = new Intent(StudentQuiz.this, StudentQuiz.class);
                previous.putExtra("student", student);
                previous.putExtra("quiz", quizPath);
                previous.putExtra("num", questionnum - 1);
                Log.d("STUDENT_QUIZ", (questionnum - 1) + "");
                previous.putExtra("total", totalquestion);
                previous.putExtra("courseCode", courseCodeStr);
                previous.putExtra("courseTitle", courseTitleStr);
                previous.putExtra("section", courseSectionStr);
                previous.putExtra("quizTitle", quizTitleStr);
                startActivity(previous);

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Intent next = new Intent(StudentQuiz.this, StudentQuiz.class);
                next.putExtra("student", student);
                next.putExtra("quiz", quizPath);
                next.putExtra("num", questionnum + 1);
                Log.d("STUDENT_QUIZ", (questionnum + 1) + "");
                next.putExtra("total", totalquestion);
                next.putExtra("courseCode", courseCodeStr);
                next.putExtra("courseTitle", courseTitleStr);
                next.putExtra("section", courseSectionStr);
                next.putExtra("quizTitle", quizTitleStr);
                startActivity(next);

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                CollectionReference quizgrade = FirebaseFirestore.getInstance().collection(path);
                quizgrade.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                String TAG = "GRADE";
                                if (task.isSuccessful()) {
                                    Long totalgrade = 0L;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        if (document.contains("grade")) {
                                            totalgrade +=  (Long) document.getData().get("grade");
                                        }
                                    }


                                    float finalTotalGrade = totalgrade;
                                    QuizQuestion.getParent().getParent().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                                    //Gather maxgrade
                                                    QuizQuestion.getParent().get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            Long maxGrade = 0L;
                                                            String TAG = "GRADE";
                                                            if (task.isSuccessful()) {


                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                                                    if (document.contains("point")) {
                                                                        maxGrade += (Long) document.getData().get("point");
                                                                    }
                                                                }

                                                                HashMap<String, Object> grading = new HashMap<>();
                                                                grading.put("fraction", finalTotalGrade + "/" + maxGrade);

                                                                NumberFormat defaultFormat = NumberFormat.getPercentInstance();
                                                                defaultFormat.setMinimumFractionDigits(0);
                                                                float percentageGrade = (float) finalTotalGrade / maxGrade;
                                                                grading.put("percentage", defaultFormat.format(percentageGrade));
                                                                grading.put("taken", true);
                                                                FirebaseFirestore.getInstance().document(path + "/grade").set(grading);

                                                                Intent submit = new Intent(StudentQuiz.this, quizScore.class);
                                                                submit.putExtra("student", student);
                                                                submit.putExtra("quiz", quizPath);
                                                                submit.putExtra("total", totalquestion);
                                                                submit.putExtra("courseCode", courseCodeStr);
                                                                submit.putExtra("courseTitle", courseTitleStr);
                                                                submit.putExtra("section", courseSectionStr);
                                                                submit.putExtra("quizTitle", quizTitleStr);
                                                                startActivity(submit);
                                                            } else {
                                                                Log.d(TAG, "get failed with ", task.getException());
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Log.d(TAG, "get failed with ", task.getException());
                                                }
                                            }
                                        }
                                    });

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });


    }
}