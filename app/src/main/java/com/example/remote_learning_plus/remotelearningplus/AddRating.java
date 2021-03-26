package com.example.remote_learning_plus.remotelearningplus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddRating extends AppCompatActivity {

    // TODO: get following info from previous activity

    String thisLecture="courses/cmpsc475/sections/section1/lectures/02242021";
    String thisStudentID="1";

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


                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Rating rating = documentSnapshot.toObject(Rating.class);

                        assert rating != null;
                        if (rating.getComprehension()!=null) rating.getComprehension().put(thisStudentID, comp.getRating());
                        else {
                            HashMap<String, Object> comprehension = new HashMap<>();
                            comprehension.put(thisStudentID, comp.getRating());
                            rating.setComprehension(comprehension);
                        }

                        if (rating.getEngagement()!=null) rating.getEngagement().put(thisStudentID, eng.getRating());
                        else {
                            HashMap<String, Object> engagement = new HashMap<>();
                            engagement.put(thisStudentID, eng.getRating());
                            rating.setComprehension(engagement);
                        }

                        if (rating.getPace()!=null) rating.getPace().put(thisStudentID, paceBar.getRating());
                        else {
                            HashMap<String, Object> pace = new HashMap<>();
                            pace.put(thisStudentID, paceBar.getRating());
                            rating.setComprehension(pace);
                        }

                        docRef.set(rating);

                    }
                });

            }
        } );
    }
}