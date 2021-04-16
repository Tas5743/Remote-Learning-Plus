package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.HashMap;
import java.util.Objects;

public class CreateQuestion extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int itemNum = 1;
    private static final String TAG = "Tag";

    // Data from intent
    Intent intent;
    String course;
    int quizNum;
    String quizPath;
    DocumentReference quizRef;
    CollectionReference questionsRef;

    // Test data
    /*int quizNum = 1;
    String course = "cmpsc475";
    String quizPath = "/courses/cmpsc475/quizzes/quiz1";*/

    private TextView tvQNum;
    private EditText etQuestion;
    private EditText etChoice1;
    private EditText etChoice2;
    private EditText etChoice3;
    private EditText etChoice4;
    private EditText etPoints;
    private EditText etTimeLimit;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        tvQNum = findViewById(R.id.tvQNum);
        etQuestion = findViewById(R.id.etQuestion);
        etChoice1 = findViewById(R.id.etChoice1);
        etChoice2 = findViewById(R.id.etChoice2);
        etChoice3 = findViewById(R.id.etChoice3);
        etChoice4 = findViewById(R.id.etChoice4);
        etPoints = findViewById(R.id.etPoints);
        etTimeLimit = findViewById(R.id.etTime);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);

        Button qButton = findViewById(R.id.nextQuestionButton);
        Button finishButton = findViewById(R.id.saveButton);

        intent = getIntent();
        course = intent.getStringExtra("course");
        quizNum = intent.getIntExtra("quizNum", 1);
        quizPath ="/courses/" + course + "/quizzes/quiz" + quizNum;
        quizRef = FirebaseFirestore.getInstance().document(quizPath);
        questionsRef = FirebaseFirestore.getInstance().collection(quizRef.getPath() + "/questions");

        // Bottom Navigation
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnHome:
                        Intent intent = new Intent(getApplicationContext(), Home_Teacher.class);
                        startActivity(intent);
                        break;
                    case R.id.btnAdd:
                        openNewCreateQuestionActivity();
                        break;
                }
                return true;
            }
        });


        // retrieving the last question from the question collection
        questionsRef.orderBy("itemNum", Query.Direction.DESCENDING).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Question q = document.toObject(Question.class);
                                itemNum = q.getItemNum() +1;
                                tvQNum.setText(Integer.toString(itemNum));
                            }
                        } else {
                            Log.d(TAG, "error", task.getException());
                            tvQNum.setText(Integer.toString(itemNum));
                        }

                        db.document(quizPath).update("items", Integer.parseInt(tvQNum.getText().toString()));
                        //db.document("/courses/cmpsc475/quizzes/quiz1").update("items", Integer.parseInt(tvQNum.getText().toString()));
                    }
                });


        qButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQuestion();
                openNewCreateQuestionActivity();
            }

        });


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQuestion();

            }

        });

    }


    public void createQuestion() {
        if (etQuestion.getText().toString().isEmpty()) {
            etQuestion.setError("Please fill out question");
            etQuestion.requestFocus();
        } else if (etChoice1.getText().toString().isEmpty()) {
            etChoice1.setError("Please enter choice 1.");
            etChoice1.requestFocus();
        } else if (etChoice2.getText().toString().isEmpty()) {
            etChoice2.setError("Please enter choice 2.");
            etChoice2.requestFocus();
        } else if (etChoice3.getText().toString().isEmpty()) {
            etChoice3.setError("Please enter choice 3.");
            etChoice3.requestFocus();
        } else if (etChoice4.getText().toString().isEmpty()) {
            etChoice4.setError("Please enter choice 4.");
            etChoice4.requestFocus();
        }else if (etPoints.getText().toString().isEmpty()) {
            etPoints.setError("Please fill out points.");
            etPoints.requestFocus();
        } else if (etTimeLimit.getText().toString().isEmpty()) {
            etTimeLimit.setError("Please fill out time limit.");
            etTimeLimit.requestFocus();
        } else if (radioGroup.getCheckedRadioButtonId() == -1) {
            etChoice4.setError("Please select a correct choice.");
        } else {
            writeData();
            openQuizHomeActivity();
        }
    }
    public void writeData() {

        Question question = new Question();

        question.setItemNum(Integer.parseInt(tvQNum.getText().toString()));

        question.setQuestion(etQuestion.getText().toString());

        HashMap<String, String> choices = new HashMap<>();
        choices.put("option1", etChoice1.getText().toString());
        choices.put("option2", etChoice2.getText().toString());
        choices.put("option3", etChoice3.getText().toString());
        choices.put("option4", etChoice4.getText().toString());
        question.setChoices(choices);

        if (radioGroup.getCheckedRadioButtonId() == radioButton1.getId()) {
            question.setKey(1);
            choices.put("correct", etChoice1.getText().toString());
        } else if (radioGroup.getCheckedRadioButtonId() == radioButton2.getId()) {
            question.setKey(2);
            choices.put("correct", etChoice2.getText().toString());
        } else if (radioGroup.getCheckedRadioButtonId() == radioButton3.getId()) {
            question.setKey(3);
            choices.put("correct", etChoice3.getText().toString());
        } else if (radioGroup.getCheckedRadioButtonId() == radioButton4.getId()) {
            question.setKey(4);
            choices.put("correct", etChoice4.getText().toString());
        }

        question.setPoint(Integer.parseInt(etPoints.getText().toString()));
        question.setTimeLimit(Integer.parseInt(etTimeLimit.getText().toString()));

        db.document(questionsRef.getPath() + "/" + (itemNum)).set(question);

    }

    private void openNewCreateQuestionActivity() {
        Intent intent = new Intent(this, CreateQuestion.class);
        intent.putExtra("quizNum", quizNum);
        intent.putExtra("course", course);
        startActivity(intent);
    }


    private void openQuizHomeActivity() {
        Intent intent = new Intent(this, QuizHome_.class);
        intent.putExtra("course", course);
        startActivity(intent);
    }


}