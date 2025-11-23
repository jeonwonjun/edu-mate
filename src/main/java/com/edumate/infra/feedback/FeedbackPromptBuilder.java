package com.edumate.infra.feedback;

import com.edumate.domain.feedback.FeedbackPromptContext;
import com.edumate.domain.quiz.Quiz;
import com.edumate.util.llm.PromptBuilder;

public class FeedbackPromptBuilder implements PromptBuilder<FeedbackPromptContext> {
    @Override
    public String build(FeedbackPromptContext context) {
        Quiz quiz = context.getQuiz();
        String userAnswer = context.getUserAnswer();
        boolean correct = context.getCorrect();

        String correctText = "오답";
        if (correct) {
            correctText = "정답";
        }

        return """
                당신은 퀴즈에 대해 해설을 작성하는 교사 역할의 AI입니다.
                목적은 학생에게 '왜 정답이 이것인지'와\s
                '왜 학생의 선택이 틀렸는지'를 명확하게 이해시키는 것입니다.
                아래의 퀴즈, 사용자의 답변, 정답 여부를 보고
                간단한 피드백 메시지를 JSON으로 만들어줘.
                
                - 너무 장황하지 않게, 핵심만 간결히 설명해 주세요.
                
                퀴즈:
                %s
                
                정답:
                %s
                
                사용자의 답변:
                %s
                
                정답 여부: %s
                
                출력 형식 (JSON 객체 하나만):
                {
                  "message": "사용자에게 보여줄 자연스러운 한국어 피드백",
                }
                """
                .formatted(
                        quiz.toString(),
                        quiz.getAnswer(),
                        userAnswer,
                        correctText
                );
    }
}
