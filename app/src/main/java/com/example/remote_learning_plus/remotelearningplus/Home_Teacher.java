package com.example.remote_learning_plus.remotelearningplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home_Teacher extends AppCompatActivity {
    FloatingActionButton createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__teacher);
 
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

}
