package com.edumate.service.quiz;

import com.edumate.domain.quiz.Difficulty;
import com.edumate.domain.session.QuizSession;

public interface DifficultyAdjuster {
    Difficulty adjust(QuizSession quizSession);
}