package com.edumate.io;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiClient {

    /**
     * Gemini API 연결
     * @param prompt
     * @param requestBody
     * @return
     */
    public static HttpRequest contact(String prompt, String requestBody){
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null) {
            System.err.println("환경 변수 GEMINI_API_KEY가 설정되지 않았습니다.");
            return null;
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return request;
    }

}
