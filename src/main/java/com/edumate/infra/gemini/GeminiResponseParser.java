package com.edumate.infra.gemini;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Optional;

public class GeminiResponseParser {
    public Optional<String> parse(String responseBody) {
        try {
            JsonObject root = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray candidates = root.getAsJsonArray("candidates");
            if (candidates == null || candidates.isEmpty()) {
                return Optional.empty();
            }

            JsonObject content = candidates.get(0)
                    .getAsJsonObject()
                    .getAsJsonObject("content");

            JsonArray parts = content.getAsJsonArray("parts");
            if (parts == null || parts.isEmpty()) {
                return Optional.empty();
            }

            String text = parts.get(0)
                    .getAsJsonObject()
                    .get("text")
                    .getAsString();

            return Optional.ofNullable(text);
        } catch (Exception e) {
            System.err.println("응답 파싱 오류: " + e.getMessage());
            return Optional.empty();
        }
    }
}
