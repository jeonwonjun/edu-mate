package com.edumate.infra.quiz;

import com.edumate.domain.quiz.Difficulty;
import com.edumate.domain.quiz.QuizPromptContext;
import com.edumate.util.llm.PromptBuilder;

public class QuizPromptBuilder implements PromptBuilder<QuizPromptContext> {

    @Override
    public String build(QuizPromptContext context) {
        String studyNotes = context.getStudyNotes();
        Difficulty level = context.getDifficulty();
        int count = context.getCount();

        return """
                너는 CS 개념을 쉽게 설명하고 문제를 만드는 전문 강사야.
                다음 내용을 바탕으로 %d개의 객관식 퀴즈를 JSON 배열 형식으로만 만들어줘.
                난이도는 '%s' (%s)에 맞춰줘.
                각 퀴즈는 "question", "options", "answer" 키를 가져야 해.
                
                내용:
                %s
                
                출력 형식:
                [
                    {
                        "question": "string",
                        "options": ["string", "string", "string", "string"],
                        "answer": "string (정답 보기 텍스트)"
                    }
                ]
                """.formatted(count, level.getKoreanName(), level.getPromptInstruction(), studyNotes);
    }
}
