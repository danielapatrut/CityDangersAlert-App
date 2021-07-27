package com.example.citydangersalert_app;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class RegisterUtils {

    public static boolean checkPasswordsMatch(EditText mConfirmPassword, String password, String confirmPassword) {
        if(!(password.equals(confirmPassword)))
        {
            mConfirmPassword.setError("Passwords don't match");
            return true;
        }
        return false;
    }

    public static boolean checkPassLength(EditText mPassword, String password) {
        if(password.length() < 6){
            mPassword.setError("Password is too short");
            return true;
        }
        return false;
    }

    public static boolean checkEmptyPassword(EditText mPassword, String password) {
        if(TextUtils.isEmpty(password)){
            mPassword.setError("Password is Required!");
            return true;
        }
        return false;
    }

    public static boolean checkEmptyEmail(EditText mEmail, String email) {
        if(TextUtils.isEmpty(email) & !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Email is Required!");
            return true;
        }
        return false;


    }



    public static boolean checkEmptyFirstName(EditText mFirstName, String firstName) {
        if(TextUtils.isEmpty(firstName)){
            mFirstName.setError("First name is Required!");
            return true;
        }
        return false;
    }

    public static boolean checkEmptyLastName(EditText mLastName, String lastName) {
        if(TextUtils.isEmpty(lastName)){
            mLastName.setError("Last name is Required!");
            return true;
        }
        return false;
    }

    public static boolean checkEmptyAdress(EditText mAdress, String address) {
        if(TextUtils.isEmpty(address)){
            mAdress.setError("Adress is Required!");
            return true;
        }
        return false;
    }

    public static boolean checkEmptyPhone(EditText mPhone, String phone) {
        if(TextUtils.isEmpty(phone)){
            mPhone.setError("Telephone number is Required!");
            return true;
        }
        return false;
    }
}
