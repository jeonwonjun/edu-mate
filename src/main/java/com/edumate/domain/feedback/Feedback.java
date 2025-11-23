package com.edumate.domain.feedback;

public class Feedback {
    private final boolean correct;
    private final String message;

    public Feedback(boolean correct, String message) {
        this.correct = correct;
        this.message = message;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getMessage() {
        return message;
    }
}
