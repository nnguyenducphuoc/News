package com.phuoc.news.utils;

import android.util.Patterns;

public class Credentials {
    public static boolean isValidEmail(String email) {
        if (email.length() < 1) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

    }

    public static boolean isValidPassword(String password) {
        // Kiểm tra độ dài tối thiểu
        if (password.length() < 6) {
            return false;
        }

        // Kiểm tra xem có ít nhất 1 kí tự in hoa hay không
//        boolean hasUppercase = false;
//        for (char ch : password.toCharArray()) {
//            if (Character.isUpperCase(ch)) {
//                hasUppercase = true;
//                break;
//            }
//        }

        return true;
    }

    public static boolean isConfirmPasswordValid(String password, String confirmPassword) {
        if (confirmPassword.length() > 5 && password.equals(confirmPassword)) {
            return true;
        } else {
            return false;
        }
    }
}
