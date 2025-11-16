package com.edumate.domain.quiz;

import com.edumate.io.GeminiClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuizGenerator {
    private final GeminiClient geminiClient;

    public QuizGenerator(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }

    public List<Quiz> generate(String topic, int tryCount) {
        String prompt = buildQuizPrompt(topic, tryCount);
        Optional<String> response = geminiClient.getResponse(prompt);

        if (response.isEmpty()) {
            System.err.println("퀴즈 생성 실패: 응답 없음");
            return List.of();
        }

        return parseQuizzes(response.get());
    }

    private String buildQuizPrompt(String topic, int count) {
        return """
            다음 내용을 바탕으로 %d개의 객관식 퀴즈를 JSON 형식으로 만들어줘.
            각 퀴즈는 "question", "options", "answer" 키를 가져야 해.
            
            내용:
            %s
            출력 예시:
            [
                {
                    "question": "OOP의 4가지 특징 중 캡슐화란?",
                    "options": ["1. 데이터 은닉", "2. 코드 재사용", "3. 상속", "4. 다형성"],
                    "answer": "1"
                }
            ]
            """.formatted(count, topic);
    }

    private List<Quiz> parseQuizzes(String responseText) {
        List<Quiz> quizzes = new ArrayList<>();
        try {
            int startIdx = responseText.indexOf('[');
            int endIdx = responseText.lastIndexOf(']');
            if (startIdx == -1 || endIdx == -1 || startIdx >= endIdx) {
                System.err.println("JSON 배열 형식이 아님:\n" + responseText);
                return quizzes;
            }

            String jsonPart = responseText.substring(startIdx, endIdx + 1);

            JsonArray jsonArray = com.google.gson.JsonParser.parseString(jsonPart).getAsJsonArray();
            jsonArray.forEach(element -> {
                JsonObject obj = element.getAsJsonObject();
                String question = obj.get("question").getAsString();

                JsonArray optionsArray = obj.getAsJsonArray("options");
                List<String> options = new ArrayList<>();
                optionsArray.forEach(o -> options.add(o.getAsString()));

                String answer = obj.get("answer").getAsString();

                quizzes.add(new Quiz(question, options, answer));
            });
        } catch (Exception e) {
            System.err.println("퀴즈 파싱 실패: " + quizzes.isEmpty());
        }

        return quizzes;
    }
}
