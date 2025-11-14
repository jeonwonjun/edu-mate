package com.edumate.domain.feedback;

import com.edumate.domain.quiz.Quiz;
import com.edumate.io.GeminiClient;
import java.net.http.HttpRequest;
import java.util.Optional;

public class FeedbackGenerator {
    private final GeminiClient geminiClient;

    public FeedbackGenerator(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }

    public Feedback generate(Quiz quiz, String userAnswer) {
        boolean isCorrect = quiz.checkAnswer(userAnswer);

        if (isCorrect) {
            return new Feedback(true, "정답입니다! 잘 이해하고 있어요");
        }
        String prompt = buildFeedbackPrompt(quiz, userAnswer);
        Optional<String> response = geminiClient.getResponse(prompt);

        String feedbackText = response.orElse("오답입니다. 정답은 %s 입니다.".formatted(quiz.getAnswer()));
        return new Feedback(false, feedbackText);
    }

    private String buildFeedbackPrompt(Quiz quiz, String userAnswer) {
        return """
                당신은 스탠포드 CS 교수입니다.
                아래는 학생의 퀴즈 답변에 정보입니다.
                학생이 오답을 제출했다면, 이유를 간단히 설명하고 올바른 개념을 정리해 주세요.
                너무 장황하지 않게, 핵심만 간결히 설명해 주세요.
                
                문제: %s
                선택지: %s
                학생의 답변: %s
                정답: %s
                
                출력 형식:
                - 한 문장으로 핵심 피드백
                """.formatted(quiz.getOptions(), quiz.getOptions(), userAnswer, quiz.getAnswer());
    }
}
