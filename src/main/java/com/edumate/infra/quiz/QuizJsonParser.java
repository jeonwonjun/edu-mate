package com.edumate.infra.quiz;

import com.edumate.domain.quiz.Quiz;
import com.edumate.util.llm.LlmJsonParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class QuizJsonParser implements LlmJsonParser<List<Quiz>> {

    private final Gson gson;

    public QuizJsonParser(Gson gson) {
        this.gson = gson;
    }

    @Override
    public List<Quiz> parse(String responseText) {
        try {
            String cleanedJson = cleanJson(responseText);
            Type list = new TypeToken<List<Quiz>>() {
            }.getType();
            return gson.fromJson(cleanedJson, list);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private String cleanJson(String text) {
        return text.trim()
                .replaceAll("^```json", "")
                .replaceAll("```$", "");
    }
}
