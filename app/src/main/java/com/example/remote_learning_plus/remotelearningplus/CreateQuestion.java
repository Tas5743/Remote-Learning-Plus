package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


import org.w3c.dom.Text;

import java.util.HashMap;

public class CreateQuestion extends AppCompatActivity {

    Button qButton = findViewById(R.id.newQuestionButton);
    Button finishButton = findViewById(R.id.finishButton);

    // TO DO: Get course and quiz
    String course, quiz;

    private final DocumentReference quizRef = FirebaseFirestore.getInstance().document("courses" + course + "quizzes" + quiz);
    private final CollectionReference questionsRef = FirebaseFirestore.getInstance().collection(quizRef.getPath() + "questions");

    public CreateQuestion(FirebaseFirestore db) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);


        // Set question number
        TextView tvQNum = findViewById(R.id.tvQNum);

        quizRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Quiz quizSnap = documentSnapshot.toObject(Quiz.class);
                assert quizSnap != null;
                tvQNum.setText(quizSnap.getItems() + 1);
            }
        });


        // Create question
        Question question = new Question();
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
                } else {
                    question.setQuestion(etQuestion.getText().toString());

                    String[] choices = new String[4];
                    choices[0] = etChoice1.getText().toString();
                    choices[1] = etChoice2.getText().toString();
                    choices[2] = etChoice3.getText().toString();
                    choices[3] = etChoice4.getText().toString();
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

                    quizRef.update("items", tvQNum);
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
}