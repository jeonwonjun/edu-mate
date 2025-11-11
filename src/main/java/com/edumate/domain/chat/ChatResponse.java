package com.edumate.domain.chat;

import com.edumate.io.GeminiClient;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatResponse {
    private static HttpResponse<String> response;

    public static String responseChat(HttpClient client, HttpRequest request)
            throws IOException, InterruptedException {
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
