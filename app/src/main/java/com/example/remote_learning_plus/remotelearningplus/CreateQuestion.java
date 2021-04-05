package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CreateQuestion extends AppCompatActivity {

    private static final String TAG = "Tag";
    // TO DO: Get course and quiz
    String course="CMPSC111", quiz="/courses/cmpsc475/quizzes/quiz1";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final DocumentReference quizRef = FirebaseFirestore.getInstance().document(quiz);
    private final CollectionReference questionsRef = FirebaseFirestore.getInstance().collection(quizRef.getPath() + "/questions");

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        Button qButton = findViewById(R.id.newQuestionButton);
        Button finishButton = findViewById(R.id.finishButton);

        // Set question number
        TextView tvQNum = findViewById(R.id.tvQNum);

        // Counting the documents in collection
        db.collection(String.valueOf(questionsRef))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Question> qs = new ArrayList<>();

                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                               qs.add(document.toObject(Question.class));
                                Log.d(TAG, document.getId() + " => " + document.getData());

                            }
                            tvQNum.setText(Integer.toString(qs.size()+1));

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());

                        }
                    }
                });

/*
        // getting it from a field in quizzes
        quizRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Quiz quizSnap = documentSnapshot.toObject(Quiz.class);
                assert quizSnap != null;
                tvQNum.setText(Integer.toString(quizSnap.getItems()));

            }
        });*/


        // Create question

        EditText etQuestion = findViewById(R.id.etQuestion);
        EditText etChoice1 = findViewById(R.id.etChoice1);
        EditText etChoice2 = findViewById(R.id.etChoice2);
        EditText etChoice3 = findViewById(R.id.etChoice3);
        EditText etChoice4 = findViewById(R.id.etChoice4);
        EditText etPoints = findViewById(R.id.etPoints);
        EditText etTimeLimit = findViewById(R.id.etTime);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButton1 = findViewById(R.id.radioButton1);
        RadioButton radioButton2 = findViewById(R.id.radioButton2);
        RadioButton radioButton3 = findViewById(R.id.radioButton3);
        RadioButton radioButton4 = findViewById(R.id.radioButton4);

        qButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etQuestion.getText().toString().isEmpty()) {
                    Toast.makeText(CreateQuestion.this, "Please fill out question", Toast.LENGTH_SHORT).show();
                } else if (etChoice1.getText().toString().isEmpty() || etChoice2.getText().toString().isEmpty() ||
                        etChoice3.getText().toString().isEmpty() || etChoice4.getText().toString().isEmpty()) {
                    Toast.makeText(CreateQuestion.this, "Please fill out all the choices", Toast.LENGTH_SHORT).show();

                } else if (etPoints.getText().toString().isEmpty()) {
                    Toast.makeText(CreateQuestion.this, "Please fill out points", Toast.LENGTH_SHORT).show();
                } else if (etTimeLimit.getText().toString().isEmpty()) {
                    Toast.makeText(CreateQuestion.this, "Please fill out time limit", Toast.LENGTH_SHORT).show();
                } else if (radioGroup.getCheckedRadioButtonId()==-1) {
                        Toast.makeText(CreateQuestion.this, "Please select the correct answer", Toast.LENGTH_SHORT).show();
                } else {

                    Question question = new Question();


                    question.setQuestion(etQuestion.getText().toString());

                    HashMap<String,String> choices = new HashMap<>();
                    choices.put("Choice 1", etChoice1.getText().toString());
                    choices.put("Choice 2", etChoice2.getText().toString());
                    choices.put("Choice 3", etChoice3.getText().toString());
                    choices.put("Choice 4", etChoice4.getText().toString());
                    question.setChoices(choices);

                    if (radioGroup.getCheckedRadioButtonId() == radioButton1.getId()) {
                        question.setKey(1);
                    } else if (radioGroup.getCheckedRadioButtonId() == radioButton2.getId()) {
                        question.setKey(2);
                    } else if (radioGroup.getCheckedRadioButtonId() == radioButton3.getId()) {
                        question.setKey(3);
                    } else if (radioGroup.getCheckedRadioButtonId() == radioButton4.getId()) {
                        question.setKey(4);
                    }

                    question.setPoint(Integer.parseInt(etPoints.getText().toString()));
                    question.setTimeLimit(Integer.parseInt(etTimeLimit.getText().toString()));
                    questionsRef.add(question);

                    int items = Integer.parseInt(tvQNum.getText().toString()) + 1;
                    quizRef.update("items", items);
                    openNewCreateQuestionActivity();

                }
            }
        });


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO DO QUIZ HOMEPAGE
                // Display success message or newly created quiz along with the older ones
            }
        });


    }

    private void openNewCreateQuestionActivity() {
        Intent intent = new Intent(this, CreateQuestion.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}