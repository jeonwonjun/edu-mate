package com.edumate.infra.gemini;

import com.edumate.util.llm.LlmClient;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class GeminiClient implements LlmClient {

    private static final String MODEL_NAME = "gemini-2.5-flash";
    private static final String BASE_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/" + MODEL_NAME + ":generateContent";

    private final HttpClient httpClient;
    private final String apiKey;
    private final GeminiRequestBuilder geminiRequestBuilder;
    private final GeminiResponseParser geminiResponseParser;

    public GeminiClient(GeminiRequestBuilder geminiRequestBuilder, GeminiResponseParser geminiResponseParser) {
        this.apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null) {
            throw new IllegalStateException("환경 변수 GEMINI_API_KEY가 설정되지 않았습니다.");
        }
        this.geminiResponseParser = geminiResponseParser;
        this.geminiRequestBuilder = geminiRequestBuilder;
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public Optional<String> generate(String prompt) {
        try {
            String requestBody = geminiRequestBuilder.buildRequestBody(prompt);
            HttpRequest request = buildRequest(requestBody);
            String responseBody = sendRequest(request);
            return geminiResponseParser.parse(responseBody);
        } catch (Exception e) {
            System.err.println("Gemini 요청 실패: " + e.getMessage());
            return Optional.empty();
        }
    }

    private HttpRequest buildRequest(String requestBody) {
        return HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    private String sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
