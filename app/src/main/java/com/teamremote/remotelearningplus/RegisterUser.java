package com.teamremote.remotelearningplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterUser extends AppCompatActivity {
    private static final String TAG = "RegisterUser";
    String txtUserType = "student";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
     FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private String uID;

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

        if(txtFirstName.isEmpty()){
            edtTxtFirstName.setError("Please enter your first name.");
            edtTxtFirstName.requestFocus();
        }
        else if(txtLastName.isEmpty()){
            edtTxtLastName.setError("Please enter your last name.");
            edtTxtLastName.requestFocus();
        }
        else if(txtInstitution.isEmpty()){
            edtTxtInstitution.setError("Please enter an institution.");
            edtTxtInstitution.requestFocus();
        }
        else if(txtEmail.isEmpty()){
            edtTxtEmail.setError("Please enter your email address.");
            edtTxtEmail.requestFocus();
        }
        else if(txtPassword.isEmpty()){
            edtTxtPassword.setError("Please enter your password.");
            edtTxtPassword.requestFocus();
        }
        else {

            mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        try{
                            throw Objects.requireNonNull(task.getException());
                        }
                        catch(FirebaseAuthUserCollisionException emailExist){
                            edtTxtEmail.setError("This email already exist, try another.");
                            edtTxtEmail.requestFocus();
                        } catch(FirebaseAuthWeakPasswordException e){
                            edtTxtPassword.setError("Password must be at least 6 characters long.");
                            edtTxtPassword.requestFocus();
                        }
                        catch(FirebaseAuthInvalidCredentialsException e){
                            edtTxtEmail.setError("Invalid email address.");
                            edtTxtEmail.requestFocus();
                        }
                        catch (Exception e) {
                            Log.d("User Creation", e.getMessage());
                            Log.d("User Creation", e.toString());

                        }
                        Toast.makeText(RegisterUser.this, "User Registration Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "organizing new user data");
                        // Get user information ready for database
                        Map<String, Object> dataToSave = new HashMap<String, Object>();
                        dataToSave.put("email", txtEmail);
                        dataToSave.put("firstName", txtFirstName);
                        dataToSave.put("lastName", txtLastName);
                        dataToSave.put("institution", txtInstitution);
                        dataToSave.put("userType", txtUserType);
                        dataToSave.put("username", txtFirstName + " " + txtLastName);
                        // Save new user information to database
                        db.collection("users").document(mAuth.getCurrentUser().getUid()).set(dataToSave);
                        Log.d(TAG, "new user id: " + mAuth.getCurrentUser().getUid());
                        Toast.makeText(RegisterUser.this, "User Registration Successful", Toast.LENGTH_SHORT).show();
                        // Redirect user to login page
                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                    }
                }
            });







            txtPassword = "";
        }

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