package com.edumate.domain.session;

import com.edumate.domain.quiz.Difficulty;

public class QuizSession {
    private Difficulty currentDifficulty;
    private int correctStreak;
    private int wrongStreak;

    public void markCorrect() {
        correctStreak++;
    }

    public void markWrong() {
        wrongStreak++;
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
