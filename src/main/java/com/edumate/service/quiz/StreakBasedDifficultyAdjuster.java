package com.edumate.service.quiz;

import com.edumate.domain.quiz.Difficulty;
import com.edumate.domain.session.QuizSession;

public class StreakBasedDifficultyAdjuster implements DifficultyAdjuster {
    private static final int STREAK_BASE = 2;

    @Override
    public Difficulty adjust(QuizSession quizSession) {
        Difficulty current = quizSession.getCurrentDifficulty();
        if (quizSession.getCorrectStreak() >= STREAK_BASE) {
            return increase(current);
        }

        if (quizSession.getWrongStreak() >= STREAK_BASE) {
            return decrease(current);
        }
        
        return current;
    }

    private Difficulty increase(Difficulty current) {
        if (current == Difficulty.EASY) {
            return Difficulty.MEDIUM;
        }

        if (current == Difficulty.MEDIUM) {
            return Difficulty.HARD;
        }
        return Difficulty.HARD;
    }

    private Difficulty decrease(Difficulty current) {
        if (current == Difficulty.HARD) {
            return Difficulty.MEDIUM;
        }

        if (current == Difficulty.MEDIUM) {
            return Difficulty.EASY;
        }

        return Difficulty.EASY;
    }
}
