package com.yourcompany.petcare.ui.ai;

public class GeminiApiService {
    public static final String API_KEY = "AIzaSyAGrwIKrWEqigt-sJinhB_wZyt6dZk4xQ4";

    // FINAL WORKING MODEL
    public static final String MODEL = "gemini-flash-latest";

    public static final String API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/"
                    + MODEL + ":generateContent?key=" + API_KEY;
}
