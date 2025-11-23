package com.edumate.service.quiz;

import com.edumate.domain.quiz.Difficulty;
import com.edumate.domain.quiz.Quiz;
import com.edumate.domain.quiz.QuizPromptContext;
import com.edumate.infra.quiz.QuizJsonParser;
import com.edumate.infra.quiz.QuizPromptBuilder;
import com.edumate.util.llm.LlmClient;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QuizGenerator {

    private static final int QUIZ_COUNT = 5;

    private final LlmClient llmClient;
    private final QuizPromptBuilder promptBuilder;
    private final Gson gson;

    public QuizGenerator(LlmClient llmClient, QuizPromptBuilder promptBuilder, Gson gson) {
        this.llmClient = llmClient;
        this.promptBuilder = promptBuilder;
        this.gson = gson;
    }


    public List<Quiz> generate(String studyNotes, Difficulty level) {
        QuizPromptContext context = new QuizPromptContext(studyNotes, level, QUIZ_COUNT);
        String prompt = promptBuilder.build(context);

        Optional<String> response = llmClient.generate(prompt);
        if (response.isEmpty()) {
            System.err.println("퀴즈 생성 실패: LLM 응답 없음");
            return Collections.emptyList();
        }

        QuizJsonParser parser = new QuizJsonParser(gson);

        return parser.parse(response.get());
    }


}