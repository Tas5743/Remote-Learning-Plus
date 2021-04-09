package com.example.remote_learning_plus.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mifmif.common.regex.Main;

public class MainActivity extends AppCompatActivity {
    EditText edtTxtEmail, edtTxtPassword;
    Button btnLogin;
    TextView txtRegister;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);
        txtRegister = findViewById(R.id.txtSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        /*
        mAuthStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, LoginSuccess.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };*/

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = edtTxtEmail.getText().toString();
                String password = edtTxtPassword.getText().toString();
                if(email.isEmpty()){
                    edtTxtEmail.setError("Please enter your email.");
                    edtTxtEmail.requestFocus();
                }
                else if(password.isEmpty()){
                    edtTxtPassword.setError("Please enter your password.");
                    edtTxtPassword.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && password.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Log.d("LOGIN_AUTH", task.getException().getLocalizedMessage());
                                edtTxtEmail.setError("Invalid User Credentials");
                                edtTxtEmail.requestFocus();
                            }
                            else{

                                DocumentReference docRef = db.collection("users").document(mFirebaseAuth.getCurrentUser().getUid());
                                docRef.get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if(documentSnapshot.exists()){
                                                    String userType = documentSnapshot.getString("userType");
                                                    if(userType.equalsIgnoreCase("student")){
                                                        Intent intSuccess = new Intent(MainActivity.this, Home_Student.class);
                                                        startActivity(intSuccess);
                                                    }
                                                    else{
                                                        Intent intSuccess = new Intent(MainActivity.this, Home_Teacher.class);
                                                        startActivity(intSuccess);
                                                    }
                                                } else{
                                                    Toast.makeText(MainActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intRegister = new Intent(MainActivity.this,RegisterUser.class);
                startActivity(intRegister);
            }
        });
    }
}