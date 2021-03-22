package com.example.remote_learning_plus.remotelearningplus;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.remote_learning_plus.remotelearningplus.Home_Student;
import com.example.remote_learning_plus.remotelearningplus.Join_Course;
import com.example.remote_learning_plus.remotelearningplus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JoinCourse extends AppCompatActivity {

    DocumentReference instructor;
    ArrayList<String> roster;
    private static final String TAG = "JoinCourse" ;
    public static final  String TAG2 = "CHECKCOURSE";
    String studentname;
    String accountname;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_course);
        EditText inviteCode = findViewById(R.id.EditTxtInviteCode);
        Button submit = findViewById(R.id.JoinCourseBtn);

        //Bundle bundle = getIntent().getExtras();
        //studentname = "jdoe456@psu.edu";
        //accountname = "student";
        //TODO grab Student's name and account name from menu activity.
        DocumentReference studentRef = db.collection("users").document(mAuth.getCurrentUser().getUid());
        studentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    studentname = documentSnapshot.getString("username");
                    Log.d("Username", studentname);
                }else{
                    Toast.makeText(JoinCourse.this,"User does not exist.",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(JoinCourse.this,"Error!",Toast.LENGTH_SHORT).show();
                Log.d("studentRef", e.toString());
            }
        });
        accountname = mAuth.getCurrentUser().getUid();
        //studentname = bundle.getString("lastName ") + bundle.getString("firstName");
        //accountname = bundle.getString("account");

        submit.setOnClickListener(v -> {
            String code = inviteCode.getText().toString();
            if(code.isEmpty()){
                Toast.makeText(JoinCourse.this,"Please enter an invite code",Toast.LENGTH_SHORT).show();
            }
            else {
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("masterSectionList").document(code);

            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                       instructor = FirebaseFirestore.getInstance().document(document.get("sectionReference").toString());
                       Log.d("sectionRef", document.get("sectionReference").toString());
                        if (instructor != null){

                            instructor.get().addOnCompleteListener(task2 -> {
                                if(task2.isSuccessful()){
                                    DocumentSnapshot documentSnapshot = task2.getResult();
                                    if(documentSnapshot.exists()){
                                        Log.d(TAG2, "DocumentSnapshot data: " + documentSnapshot.getData());
                                        if(Objects.requireNonNull(documentSnapshot.getData()).containsKey("roster")){
                                            roster =  (ArrayList<String>) documentSnapshot.getData().get("roster");
                                        }
                                        else {
                                            roster = new ArrayList<>();
                                        }
                                        assert roster != null;
                                        if(!roster.contains(studentname)) {
                                            roster.add(studentname);
                                            HashMap<String, Object> updateclass = new HashMap<>();
                                            updateclass.put("roster", roster);
                                            instructor.set(updateclass);
                                            DocumentReference studentClass = FirebaseFirestore.getInstance().collection("users/" + accountname + "/courses").document(instructor.getParent().getParent().getId());
                                            HashMap<String, Object> addclass = new HashMap<>();
                                            addclass.put("classRef", document.get("sectionReference").toString());
                                            addclass.put("courseID", document.get("courseID").toString());
                                            addclass.put("courseSection", document.get("section").toString());
                                            studentClass.set(addclass, SetOptions.merge());
                                            Toast.makeText(JoinCourse.this, "Class Successfully added", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(JoinCourse.this, "You are already a student for this course section.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                        Log.d(TAG2, "No such document");
                                        Toast.makeText(JoinCourse.this,"No such class", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d(TAG2, "get failed with ", task2.getException());
                                }
                            });


                        }

                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(JoinCourse.this,"Invalid Invite Code", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            });
                //Navigation Bar

                BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
                bottomNavigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
                    @Override
                    public void onNavigationItemReselected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.btnHome:
                                Intent intent = new Intent(getApplicationContext(), Home_Student.class);
                                startActivity(intent);
                                break;
                            case R.id.btnAdd:
                                Intent intent2 = new Intent(getApplicationContext(), JoinCourse.class);
                                startActivity(intent2);
                                break;
                        }
                    }
                });

}});

    }
}
