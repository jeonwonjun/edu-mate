package com.edumate;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null) {
            System.err.println("환경 변수 GEMINI_API_KEY가 설정되지 않았습니다.");
            return ;
        }
        String prompt = "자바의 StringBuilder에 대한 간단한 퀴즈를 내줘.";

        String requestBody = String.format("""
                {
                    "contents": [{
                        "parts": [{"text": "%s"}]
                    }]
                }
                """, prompt);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Gemini API 응답:");
        System.out.println(response.body());
    }
}