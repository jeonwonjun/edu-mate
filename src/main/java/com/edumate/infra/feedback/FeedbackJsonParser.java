package com.edumate.infra.feedback;

import com.edumate.domain.feedback.Feedback;
import com.edumate.util.llm.LlmJsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FeedbackJsonParser implements LlmJsonParser<Feedback> {

    private final boolean correct;

    public FeedbackJsonParser(boolean correct) {
        this.correct = correct;
    }

    @Override
    public Feedback parse(String feedbackText) {
        try {
            String cleaned = cleanJson(feedbackText);

            JsonObject root = JsonParser.parseString(cleaned).getAsJsonObject();
            String message = root.get("message").getAsString();

            return new Feedback(correct, message);
        } catch (Exception e) {
            return new Feedback(correct, "피드백 생성에 실패했습니다.");
        }
    }

    private String cleanJson(String text) {
        return text.trim()
                .replaceAll("^```json", "")
                .replaceAll("```$", "");
    }
}
