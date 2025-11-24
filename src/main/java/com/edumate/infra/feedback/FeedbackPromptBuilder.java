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
                당신은 학생에게 퀴즈 해설을 제공하는 교사 역할의 AI입니다.
                목적은 학생이
                1) 왜 정답이 이것인지,
                2) 왜 자신의 선택이 틀렸는지
                를 명확히 이해하도록 돕는 것입니다.
                
                아래의 퀴즈, 정답, 사용자의 답변, 정답 여부를 보고
                학생에게 보여줄 간결한 피드백 메시지를 JSON으로 만들어 주세요.
                
                규칙:
                - 너무 장황하지 않게핵심만 설명하세요.
                - 정답인 경우: 칭찬 + 개념을 한 줄로 정리해 주세요.
                - 오답인 경우: 어떤 부분을 잘못 이해했는지 짚어주고, 올바른 개념을 설명해 주세요.
                
                퀴즈:
                %s
                
                정답:
                %s
                
                사용자의 답변:
                %s
                
                정답 여부: %s
                
                출력 형식 (JSON 객체 하나만):
                {
                  "message": "사용자에게 보여줄 자연스러운 한국어 피드백"
                }
                JSON 이외의 텍스트는 절대 출력하지 마세요.
                """
                .formatted(
                        quiz.toString(),
                        quiz.getAnswer(),
                        userAnswer,
                        correctText
                );
    }
}