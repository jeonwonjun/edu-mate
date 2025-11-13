package com.edumate.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class GeminiClient {
    private static final String MODEL_NAME = "gemini-2.5-flash";
    private static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/" + MODEL_NAME + ":generateContent";
    private final HttpClient httpClient;
    private final String apiKey;

    public GeminiClient() {
        this.apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null) {
            throw new IllegalStateException("환경 변수 GEMINI_API_KEY가 설정되지 않았습니다.");
        }
        this.httpClient = HttpClient.newHttpClient();
    }

    public Optional<String> getResponse(String prompt) {
        try {
            String requestBody = buildRequestBody(prompt);
            HttpRequest request = buildRequest(requestBody);
            String responseBody = sendRequest(request);
            return parseContent(responseBody);
        } catch (Exception e) {
            System.err.println("Gemini 요청 실패: " + e.getMessage());
            return Optional.empty();
        }
    }

    private String buildRequestBody(String prompt) {
        JsonObject textPart = new JsonObject();
        textPart.addProperty("text", prompt);

        JsonArray partsArray = new JsonArray();
        partsArray.add(textPart);

        JsonObject contentObj = new JsonObject();
        contentObj.add("parts", partsArray);

        JsonArray contentsArray = new JsonArray();
        contentsArray.add(contentObj);

        JsonObject root = new JsonObject();
        root.add("contents", contentsArray);

        return root.toString();
    }

    private HttpRequest buildRequest(String requestBody){
        return HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    private String sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private Optional<String> parseContent(String responseBody) {
        try {
            JsonObject root = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray candidates = root.getAsJsonArray("candidates");
            if (candidates == null || candidates.isEmpty()) {
                return Optional.empty();
            }

            JsonObject content = candidates.get(0).getAsJsonObject()
                    .getAsJsonObject("content");
            JsonArray parts = content.getAsJsonArray("parts");
            if (parts == null || parts.isEmpty()) {
                return Optional.empty();
            }

            String text = parts.get(0).getAsJsonObject().get("text").getAsString();
            return Optional.ofNullable(text);
        } catch (Exception e) {
            System.err.println("응답 파싱 오류: " + e.getMessage());
            return Optional.empty();
        }
    }
}
