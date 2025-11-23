package com.edumate.service.quiz;

import com.edumate.domain.quiz.Difficulty;
import com.edumate.domain.quiz.Quiz;
import com.edumate.domain.quiz.QuizPromptContext;
import com.edumate.infra.quiz.QuizJsonParser;
import com.edumate.infra.quiz.QuizPromptBuilder;
import com.edumate.util.llm.LlmClient;
import com.google.gson.Gson;
import java.util.List;
import java.util.Optional;

public class QuizGenerator {

    private static final int QUIZ_COUNT = 1;

    private final LlmClient llmClient;
    private final QuizPromptBuilder promptBuilder;
    private final Gson gson;

    public QuizGenerator(LlmClient llmClient, QuizPromptBuilder promptBuilder, Gson gson) {
        this.llmClient = llmClient;
        this.promptBuilder = promptBuilder;
        this.gson = gson;
    }

    public Quiz generateOne(String studyNotes, Difficulty level) {
        QuizPromptContext context = new QuizPromptContext(studyNotes, level, QUIZ_COUNT);
        String prompt = promptBuilder.build(context);

        Optional<String> response = llmClient.generate(prompt);
        if (response.isEmpty()) {
            return null;
        }

        QuizJsonParser parser = new QuizJsonParser(gson);
        List<Quiz> quizzes = parser.parse(response.get());

        if (quizzes.isEmpty()) {
            return null;
        }

        return quizzes.getFirst();
    }

}