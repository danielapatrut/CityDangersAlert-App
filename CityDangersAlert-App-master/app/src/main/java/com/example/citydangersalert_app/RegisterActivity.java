package com.example.citydangersalert_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, registerUser;
    private EditText editTextFirstName, editTextLastName, editTextAdress, editTextEmail, editTextPhone, editTextPassword, editTextConfirmPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.textViewCreateAccount);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerButton);
        registerUser.setOnClickListener(this);

        editTextFirstName = (EditText) findViewById(R.id.registerFisrtName);
        editTextLastName = (EditText) findViewById(R.id.registerLastName);
        editTextAdress = (EditText) findViewById(R.id.registerAddress);
        editTextEmail = (EditText) findViewById(R.id.registerEmail);
        editTextPhone = (EditText) findViewById(R.id.registerPhone) ;
        editTextPassword = (EditText) findViewById(R.id.registerPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.registerConfirmPassword);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewCreateAccount:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerButton:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmpassword = editTextConfirmPassword.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String adress = editTextAdress.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if(firstName.isEmpty()){
            editTextFirstName.setError("First name is required!");
            editTextFirstName.requestFocus();
            return;
        }

        if(lastName.isEmpty()){
            editTextLastName.setError("Last name is required!");
            editTextLastName.requestFocus();
            return;
        }

        if(adress.isEmpty()){
            editTextAdress.setError("Adress is required!");
            editTextAdress.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }


        if(phone.isEmpty()){
            editTextPhone.setError("Phone number is required!");
            editTextPhone.requestFocus();
            return;
        }

        if(phone.length() != 10){
            editTextPassword.setError("Phone number length should be 10 characters!");
            editTextPassword.requestFocus();
            return;
        }


        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(confirmpassword.isEmpty()){
            editTextConfirmPassword.setError("Confirm password is required!");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        if(confirmpassword.length() < 6){
            editTextConfirmPassword.setError("Min confirm password length should be 6 characters!");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if(!confirmpassword.equals(password)){
            Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(firstName, lastName, adress,email, phone);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();

                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}