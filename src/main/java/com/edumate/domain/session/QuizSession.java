package com.edumate.domain.session;

import com.edumate.domain.quiz.Difficulty;

public class QuizSession {

    private static final int ZERO = 0;
    private Difficulty currentDifficulty;
    private int correctStreak;
    private int wrongStreak;

    public QuizSession(Difficulty difficulty) {
        this.currentDifficulty = difficulty;
        this.correctStreak = ZERO;
        this.wrongStreak = ZERO;
    }

    public void markCorrect() {
        wrongStreak = ZERO;
        correctStreak++;
    }

    public void markWrong() {
        correctStreak = ZERO;
        wrongStreak++;
    }

    public void updateDifficulty(Difficulty next) {
        currentDifficulty = next;
    }

    public Difficulty getCurrentDifficulty() {
        return currentDifficulty;
    }

    public int getCorrectStreak() {
        return correctStreak;
    }

    public int getWrongStreak() {
        return wrongStreak;
    }
}
