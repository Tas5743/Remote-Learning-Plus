package com.example.remote_learning_plus.remotelearningplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AddResource extends AppCompatActivity {

    // TODO: get course info from previous activity

    Intent intent;
    String course;
    CollectionReference colRef;
    private EditText etURL;
    private EditText etResourceTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource);

        etURL = findViewById(R.id.etURL);
        etResourceTitle = findViewById(R.id.etResourceTitle);
        intent = getIntent();
        course = intent.getStringExtra("course");
        //course = "0915U1AvP";
        colRef = FirebaseFirestore.getInstance()
                .collection("/courses/" + course + "/learningResources");

        Button addResource = findViewById(R.id.btnAddResource);
        addResource.setOnClickListener(v -> {

            Map<String, Object> resource = new HashMap<String, Object>();
            resource.put("URL", etURL.getText().toString());
            resource.put("title", etResourceTitle.getText().toString());
            colRef.add(resource);
            openNewResourcePageActivity(course);

        });

    }


    private void openNewResourcePageActivity(String course) {
        Intent intent = new Intent(this, ResourceHome.class);
        intent.putExtra("course", course);
        startActivity(intent);
    }





}