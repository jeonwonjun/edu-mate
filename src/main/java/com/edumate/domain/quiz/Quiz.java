package com.edumate.domain.quiz;

import java.util.List;

public class Quiz {
    private final String question;
    private final List<String> options;
    private final String answer;
    private final AnswerChecker answerChecker;

    public Quiz(String question, List<String> options, String answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.answerChecker = new AnswerChecker();
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }
    public boolean checkAnswer(String userAnswer) {
        return answerChecker.isCorrect(this, userAnswer);
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Q: " + question + "\nOptions:" + options;
    }
}
