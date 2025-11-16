package com.edumate.domain.feedback;

import com.edumate.domain.quiz.Quiz;
import com.edumate.io.GeminiClient;

public class Feedback {
    private final boolean isCorrect;
    private final String message;

    public Feedback(boolean isCorrect, String message) {
        this.isCorrect = isCorrect;
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
