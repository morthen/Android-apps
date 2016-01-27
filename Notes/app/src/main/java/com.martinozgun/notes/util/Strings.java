package com.martinozgun.notes.util;

/**
 * Created by Martin Özgun.
 */
public final class Strings {

    public static final String EMPTY = "";

    private Strings() {
        throw new IllegalAccessError("This class cannot be instantiated nor extended");
    }


    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().length() == 0;
    }
}