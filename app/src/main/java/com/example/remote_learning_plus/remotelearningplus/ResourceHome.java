package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ResourceHome extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ResourceAdapter adapter;

    // Intent data
    Intent intent;
    String course;
    CollectionReference resourcesRef;
    String url;
    String resourceTitle;

    // Data for testing
    // private CollectionReference quizRef = db.collection("/courses/cmpsc475/quizzes/");

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_home);

        setUpRecyclerView();

        // Bottom navigation

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.btnHome:
                    Intent intent = new Intent(getApplicationContext(), Home_Teacher.class);
                    startActivity(intent);
                    break;
                case R.id.btnAdd:
                    Intent resourceIntent = new Intent(getApplicationContext(), AddResource.class);
                    resourceIntent.putExtra("course", course);
                    startActivity(resourceIntent);
                    break;
            }
            return true;
        });


    }

    private void setUpRecyclerView() {

        intent = getIntent();
        course=intent.getStringExtra("course");
        resourcesRef = db.collection("/courses/" + course + "/learningResources");

        FirestoreRecyclerOptions<ResourceModel> options = new FirestoreRecyclerOptions.Builder<ResourceModel>()
                .setQuery(resourcesRef, ResourceModel.class)
                .build();
        adapter = new ResourceAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.resourceRecycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    private static final String TAG = "TAG";

                    public void onItemClick(View view, int position) {
                        resourceTitle = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position))
                                .itemView.findViewById(R.id.resourceTitle)).getText().toString();

                        resourcesRef.whereEqualTo("title", resourceTitle).get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                            ResourceModel resourceModel = document.toObject(ResourceModel.class);
                                            url = resourceModel.getUrl();
                                        }
                                    } else {
                                        url = "https://www.google.com";
                                            Toast.makeText(ResourceHome.this, "Invalid URL", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "error", task.getException());
                                    }
                                    openResourceInBrowser(url);
                                });
                    }


                    public void onLongItemClick(View view, int position) {
                        resourceTitle = ((TextView) Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position))
                                .itemView.findViewById(R.id.resourceTitle)).getText().toString();

                        resourcesRef.whereEqualTo("title", resourceTitle).get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                            ResourceModel resourceModel = document.toObject(ResourceModel.class);
                                            url = resourceModel.getUrl();
                                        }
                                    } else {
                                        url = "https://www.google.com";
                                        Toast.makeText(ResourceHome.this, "Invalid URL", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "error", task.getException());
                                    }
                                    openResourceInBrowser(url);
                                });
                    }
                })
        );
    }


    private void openResourceInBrowser(String url) {
        url = "https://google.com";
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(i);
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