package com.example.citydangersalert_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText mFirstName, mLastName,mEmail,mPassword,mConfirmPassword, mAddress, mPhone;
    Button mRegisterBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirstName = findViewById(R.id.registerFisrtName);
        mLastName = findViewById(R.id.registerLastName);
        mEmail = findViewById(R.id.registerEmail);
        mPassword   = findViewById(R.id.registerPassword);
        mConfirmPassword      = findViewById(R.id.registerConfirmPassword);
        mAddress = findViewById(R.id.registerAddress);
        mPhone = findViewById(R.id.registerPhone);
        mRegisterBtn= findViewById(R.id.registerButton);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPassword.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();
                String firstName = mFirstName.getText().toString().trim();
                String lastName = mLastName.getText().toString().trim();
                String address = mAddress.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();

                if (RegisterUtils.checkEmptyFirstName(mFirstName, firstName)) return;

                if (RegisterUtils.checkEmptyLastName(mLastName, lastName)) return;

                if (RegisterUtils.checkEmptyAdress(mAddress, address)) return;

                if (RegisterUtils.checkEmptyEmail(mEmail, email)) return;

                if (RegisterUtils.checkEmptyPhone(mPhone, phone)) return;

                if (RegisterUtils.checkEmptyPassword(mPassword, password)) return;

                if (RegisterUtils.checkPassLength(mPassword, password)) return;

                if (RegisterUtils.checkPasswordsMatch(mConfirmPassword, password, confirmPassword)) return;

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(RegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();
                           /* userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("userName", userName);
                            user.put("email", email);
                            user.put("password", password);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG","onSuccess: user profile is created for " + userID);
                                }
                            });*/
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}