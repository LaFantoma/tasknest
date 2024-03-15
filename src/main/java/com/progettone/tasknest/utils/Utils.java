package com.progettone.tasknest.utils;

public class Utils {
    public static boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[@#$%&*!]).{8,}$"; 
        return password.matches(passwordPattern);
    }

    public static boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailPattern);
    }
}
