package com.edumate.domain.quiz;

import java.util.List;

public class Quiz {
    private final String question;
    private final List<String> options;
    private final String answer;

    public Quiz(String question, List<String> options, String answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean checkAnswer(String userAnswer) {
        return userAnswer.trim().equalsIgnoreCase(answer.trim());
    }

    @Override
    public String toString() {
        return "Q: " + question + "\nOptions:" + options;
    }
}
