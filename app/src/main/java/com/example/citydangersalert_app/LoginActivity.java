package com.example.citydangersalert_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    TextView mRegText, mforgotPassword;
    EditText mEmail,mPassword;
    Button mLoginBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRegText = findViewById(R.id.signupText);
        mEmail = findViewById(R.id.loginEmail);
        mPassword = findViewById(R.id.loginPassword);
        mLoginBtn = findViewById(R.id.loginButton);
        fAuth = FirebaseAuth.getInstance();
        mforgotPassword = findViewById(R.id.forgotPassword);


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPassword.getText().toString().trim();
                String email = mEmail.getText().toString().trim();

                if (RegisterUtils.checkEmptyEmail(mEmail, email)) return;

                if (RegisterUtils.checkEmptyPassword(mPassword, password)) return;

                if (RegisterUtils.checkPassLength(mPassword, password)) return;

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                                //Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();

                        }else {
                            Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        });

        mRegText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        mforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
            }
        });


    }
}