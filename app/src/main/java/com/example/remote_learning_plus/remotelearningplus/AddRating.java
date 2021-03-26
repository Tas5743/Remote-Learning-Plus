package com.teamremote.remotelearningplus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddRating extends AppCompatActivity {

    // TODO: get following info from previous activity

    String thisLecture;
    String thisStudentID;

    //getIntent().getStringExtra().toString();
    private DocumentReference docRef = FirebaseFirestore.getInstance().document(thisLecture);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating);


        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                RatingBar comp = findViewById(R.id.ratingBarComp);
                RatingBar eng = findViewById(R.id.ratingBarEng);
                RatingBar paceBar = findViewById(R.id.ratingBarPace);

                Map<String, Object> rating = new HashMap<String, Object>();

                Map<String, Object> comprehension = new HashMap<String, Object>();
                comprehension.put(thisStudentID, comp.getRating());

                Map<String, Object> engagement = new HashMap<String, Object>();
                engagement.put(thisStudentID, eng.getRating());

                Map<String, Object> pace = new HashMap<String, Object>();
                pace.put(thisStudentID, paceBar.getRating());

                docRef.set(pace);
                docRef.set(engagement);
                docRef.set(comprehension);

            }
        } );
    }
}