package com.edumate.domain.feedback;

import com.edumate.domain.quiz.AnswerChecker;
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
        boolean isCorrect = quiz.isCorrect(userAnswer);

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
                당신은 객관식 퀴즈에 대해 해설을 작성하는 교사 역할의 AI입니다.
                사용자의 목적은 학생에게 '왜 정답이 이것인지'와 '왜 학생의 선택이 틀렸는지'를
                명확하게 이해시키는 것입니다.
                
                아래는 학생의 퀴즈 답변에 정보입니다.
                학생이 오답을 제출했다면, 이유를 간단히 설명하고 올바른 개념을 정리해 주세요.
                너무 장황하지 않게, 핵심만 간결히 설명해 주세요.
                
                문제: %s
                선택지: %s
                학생의 답변: %s
                정답: %s
                
                """.formatted(quiz.getOptions(), quiz.getOptions(), userAnswer, quiz.getAnswer());
    }
}
