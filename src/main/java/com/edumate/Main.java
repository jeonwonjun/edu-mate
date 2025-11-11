package com.edumate;


import com.edumate.domain.chat.ChatResponse;
import com.edumate.io.GeminiClient;
import com.edumate.io.InputHandler;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        //String prompt = "자바의 StringBuilder에 대한 간단한 퀴즈를 내줘.";
        String prompt = InputHandler.readInput();

        String requestBody = String.format("""
                {
                    "contents": [{
                        "parts": [{"text": "%s"}]
                    }]
                }
                """, prompt);
        HttpRequest request = GeminiClient.contact(prompt,requestBody);

        String response = ChatResponse.responseChat(client, request);
        System.out.println(response);
    }
}