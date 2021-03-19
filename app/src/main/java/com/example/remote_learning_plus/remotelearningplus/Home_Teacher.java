package com.example.remote_learning_plus.remotelearningplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Home_Teacher extends AppCompatActivity {
    FloatingActionButton createBtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference courseRef = db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("courses");
    private CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__teacher);


        setUpRecyclerView();


 
        //Bottom Navigation
        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
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
            }
        });
        

        // Initialize FAB
        createBtn = findViewById(R.id.btn_create_course);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Teacher.this, CourseInformation.class);
                startActivity(intent);
            }
        });
    }


    private void setUpRecyclerView() {
        Query query = courseRef;

        FirestoreRecyclerOptions<CourseModel> options = new FirestoreRecyclerOptions.Builder<CourseModel>()
                .setQuery(query, CourseModel.class)
                .build();
        adapter = new CourseAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}

