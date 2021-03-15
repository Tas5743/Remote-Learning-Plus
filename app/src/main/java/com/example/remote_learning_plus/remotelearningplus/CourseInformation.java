package com.example.remote_learning_plus.remotelearningplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CourseInformation extends AppCompatActivity {

    Button btn;
    EditText edtTxtName, edtTxtId, edtTxtDesc;
    String name, id, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);

        // Initialize EditText Fields
        edtTxtName = findViewById(R.id.etCourse_name);
        edtTxtId = findViewById(R.id.etCourse_id);
        edtTxtDesc = findViewById(R.id.etCourse_description);


        // Initialize Button
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store EditText Input
                name = edtTxtName.getText().toString();
                id = edtTxtId.getText().toString();
                desc = edtTxtDesc.getText().toString();

                if(name.isEmpty()){
                    edtTxtName.setError("Please enter a course name.");
                    edtTxtName.requestFocus();
                }
                else if(id.isEmpty()){
                    edtTxtId.setError("Please enter a course ID.");
                    edtTxtId.requestFocus();
                }
                else if(desc.isEmpty()){
                    edtTxtDesc.setError("Please enter a course ID.");
                    edtTxtDesc.requestFocus();
                }
                else {
                    Intent intent = new Intent(CourseInformation.this, SelectCourseTimes.class);
                    intent.putExtra("courseName", name);
                    intent.putExtra("courseId", id);
                    intent.putExtra("courseDesc", desc);
                    startActivity(intent);
                }
            }
        });
    }


}