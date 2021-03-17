package com.sosaley.hatsun.utils;

import android.util.Patterns;

public class Validation {

    public static boolean validateName(String name) {

        if (name.length() < 3) {
            return false;
        }

        return true;

    }

    public static boolean validateMobileNumberLength(String mobile){

        if(mobile.length()<10){
            return false;
        }
        return true;

    }

    public static boolean validateMobileNumber(String mobile) {

        if(Patterns.PHONE.matcher(mobile).matches()){
            return true;
        }
        return false;
    }

    public static boolean validateEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern) && email.length() > 0){
            return true;
        }

        return false ;
    }

    public static boolean validatePassword(String password) {
        if (password.length() < 6) {

            return false;
        }
        return true;
    }

}
