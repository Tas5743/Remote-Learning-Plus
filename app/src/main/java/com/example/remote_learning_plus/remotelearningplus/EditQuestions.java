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

public class EditQuestions extends AppCompatActivity {

    private static final String TAG = "Tag";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Intent data
    Intent intent = getIntent();
    String course = intent.getStringExtra("course");
    int quizNum = intent.getIntExtra("quizNum", 1);

    String quizPath ="/courses/" + course + "/quizzes/quiz" + quizNum;
    CollectionReference questionsRef = db.collection(quizPath + "/questions");

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

    int itemNum = 1;
    int lastNum;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_questions);

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


        // Bottom Navigation
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btnHome:
                        Intent intent = new Intent(getApplicationContext(), Home_Teacher.class);
                        startActivity(intent);
                        break;
                    case R.id.btnAdd:
                        Intent intent2 = new Intent(getApplicationContext(), CourseInformation.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });


        Button qButton = findViewById(R.id.nextQuestionButton);
        Button finishButton = findViewById(R.id.saveButton);

        fetchQuestion();

        // get last item number
        questionsRef.orderBy("itemNum", Query.Direction.DESCENDING).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Question q = document.toObject(Question.class);
                                lastNum = q.getItemNum();
                            }
                        } else {
                            Log.d(TAG, "error", task.getException());
                        }
                    }
                });



        qButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQuestion();
                itemNum++;
                if (itemNum > lastNum) { openNewCreateQuestionActivity(); }
                else fetchQuestion();
            }

        });


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQuestion();
                openQuizHomeActivity();
            }
        });

    }


    private void fetchQuestion(){

        questionsRef.whereEqualTo("itemNum", itemNum)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Question q = document.toObject(Question.class);

                                tvQNum.setText(Integer.toString(q.getItemNum()));
                                etQuestion.setText(q.getQuestion());

                                HashMap<String, String> choices = q.getChoices();
                                etChoice1.setText(choices.get("option1"));
                                etChoice2.setText(choices.get("option2"));
                                etChoice3.setText(choices.get("option3"));
                                etChoice4.setText(choices.get("option4"));

                                int key = q.getKey();
                                switch (key) {
                                    case 1 : radioGroup.check(radioButton1.getId());
                                    case 2 : radioGroup.check(radioButton2.getId());
                                    case 3 : radioGroup.check(radioButton3.getId());
                                    case 4 : radioGroup.check(radioButton4.getId());
                                }

                                etTimeLimit.setText(Integer.toString(q.getTimeLimit()));
                                etPoints.setText(Integer.toString(q.getPoint()));

                            }
                        } else {
                            Log.d(TAG, "error", task.getException());
                        }
                    }
                });

    }



    private void createQuestion(){
        if (etQuestion.getText().toString().isEmpty()) {
            Toast.makeText(EditQuestions.this, "Please fill out question", Toast.LENGTH_SHORT).show();
        } else if (etChoice1.getText().toString().isEmpty() || etChoice2.getText().toString().isEmpty() ||
                etChoice3.getText().toString().isEmpty() || etChoice4.getText().toString().isEmpty()) {
            Toast.makeText(EditQuestions.this, "Please fill out all the choices", Toast.LENGTH_SHORT).show();

        } else if (etPoints.getText().toString().isEmpty()) {
            Toast.makeText(EditQuestions.this, "Please fill out points", Toast.LENGTH_SHORT).show();
        } else if (etTimeLimit.getText().toString().isEmpty()) {
            Toast.makeText(EditQuestions.this, "Please fill out time limit", Toast.LENGTH_SHORT).show();
        } else if (radioGroup.getCheckedRadioButtonId()==-1) {
            Toast.makeText(EditQuestions.this, "Please select the correct answer", Toast.LENGTH_SHORT).show();
        } else {
            writeData();
        }
    }

    private void writeData(){

        Question question = new Question();

        question.setItemNum(Integer.parseInt(tvQNum.getText().toString()));

        question.setQuestion(etQuestion.getText().toString());

        HashMap<String,String> choices = new HashMap<>();
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

        db.document(questionsRef.getPath() + "/" + question.getItemNum()).set(question);
    }


    private void openNewCreateQuestionActivity() {
        Intent intent = new Intent(this, CreateQuestion.class);
        intent.putExtra("course", course);
        intent.putExtra("quizNum", quizNum);
        startActivity(intent);
    }


    private void openQuizHomeActivity() {
        Intent intent = new Intent(this, QuizHome.class);
        intent.putExtra("course", course);
        startActivity(intent);
    }


}