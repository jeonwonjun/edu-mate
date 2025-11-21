package com.edumate.domain.quiz;

import com.edumate.util.llm.LlmTask;
import java.util.Collections;
import java.util.List;

public class QuizGenerator {

    private static final int QUIZ_COUNT = 3;

    private final LlmTask<QuizPromptContext, List<Quiz>> quizTask;

    public QuizGenerator(LlmTask<QuizPromptContext, List<Quiz>> quizTask) {
        this.quizTask = quizTask;
    }

    public List<Quiz> generate(String studyNotes, Difficulty level) {
        QuizPromptContext context = new QuizPromptContext(studyNotes, level, QUIZ_COUNT);
        return quizTask.execute(context)
                .orElse(Collections.emptyList());
    }

}