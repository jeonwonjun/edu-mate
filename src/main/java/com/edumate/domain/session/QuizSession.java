package com.edumate.domain.session;

import com.edumate.domain.quiz.Difficulty;

public class QuizSession {
    private Difficulty currentDifficulty;
    private int correctStreak;
    private int wrongStreak;

    public QuizSession(Difficulty difficulty) {
        this.currentDifficulty = difficulty;
        this.correctStreak = 0;
        this.wrongStreak = 0;
    }

    public void markCorrect() {
        wrongStreak = 0;
        correctStreak++;
    }

    public void markWrong() {
        correctStreak = 0;
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
