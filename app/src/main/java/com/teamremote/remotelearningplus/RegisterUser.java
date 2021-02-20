package com.teamremote.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    String txtUserType = "student";
    private CollectionReference colRef = FirebaseFirestore.getInstance().collection("users");
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

    }

    public void saveUser(View view){
        EditText edtTxtFirstName = findViewById(R.id.edtTxtFirstName);
        EditText edtTxtLastName = findViewById(R.id.edtTxtLastName);
        EditText edtTxtInstitution = findViewById(R.id.edtTxtInstitution);
        EditText edtTxtEmail = findViewById(R.id.edtTxtEmail);
        EditText edtTxtPassword = findViewById(R.id.edtTxtPassword);

        String txtFirstName = edtTxtFirstName.getText().toString().trim();
        String txtLastName = edtTxtLastName.getText().toString().trim();
        String txtInstitution = edtTxtInstitution.getText().toString().trim();
        String txtEmail = edtTxtEmail.getText().toString().trim();
        String txtPassword = edtTxtPassword.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();

        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put("email", txtEmail);
        dataToSave.put("firstName", txtFirstName);
        dataToSave.put("lastName", txtLastName);
        dataToSave.put("institution", txtInstitution);
        dataToSave.put("userType", txtUserType);
        dataToSave.put("username", txtFirstName + " " + txtLastName);
        colRef.add(dataToSave);

        mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(RegisterUser.this, "User Registration Failed", Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(RegisterUser.this, MainActivity.class));
                }
            }
        });

        txtPassword = "";

    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()){
            case R.id.radioInstructor:
                if(checked){
                    txtUserType = "instructor";
                }
                break;
            case R.id.radioStudent:
                if(checked){
                    txtUserType = "student";
                }
                break;
        }
    }
}