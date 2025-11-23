package com.edumate.domain.feedback;

import com.edumate.domain.quiz.Quiz;

public class FeedbackPromptContext {

    private final Quiz quiz;
    private final String userAnswer;
    private final boolean correct;

    public FeedbackPromptContext(Quiz quiz, String userAnswer, boolean correct) {
        this.quiz = quiz;
        this.userAnswer = userAnswer;
        this.correct = correct;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public boolean getCorrect() {
        return correct;
    }

}
