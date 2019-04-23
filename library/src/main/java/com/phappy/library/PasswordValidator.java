package com.phappy.library;

import java.util.regex.Pattern;

public class PasswordValidator {
    private static final Pattern PASSWORD_PATTERN =Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
//(?=.*[@#$%])

    public static boolean isvalid(String s) {
        return !s.isEmpty();
    }

    public static boolean isValidPassword(CharSequence password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
}
