package com.edumate;


import com.edumate.io.GeminiClient;
import com.edumate.io.InputHandler;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        GeminiClient geminiClient = new GeminiClient();
        //String prompt = "자바의 StringBuilder에 대한 간단한 퀴즈를 내줘.";
        String prompt = InputHandler.readInput();
        Optional<String> response = geminiClient.getResponse(prompt);
        System.out.println(response);
    }
}