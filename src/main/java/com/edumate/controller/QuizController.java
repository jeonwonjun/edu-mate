package com.edumate.controller;

import com.edumate.domain.feedback.Feedback;
import com.edumate.domain.feedback.FeedbackGenerator;
import com.edumate.domain.quiz.Difficulty;
import com.edumate.domain.quiz.Quiz;
import com.edumate.domain.quiz.QuizGenerator;
import com.edumate.ui.QuizInputView;
import java.util.List;

public class QuizController {

    private final QuizGenerator quizGenerator;
    private final FeedbackGenerator feedbackGenerator;
    private final QuizInputView view;

    public QuizController(QuizGenerator quizGenerator, FeedbackGenerator feedbackGenerator, QuizInputView view) {
        this.quizGenerator = quizGenerator;
        this.feedbackGenerator = feedbackGenerator;
        this.view = view;
    }

    public void startQuiz(String topic, Difficulty level) {
        try {
            List<Quiz> quizzes = loadQuizzes(topic, level);

            int totalQuestions = quizzes.size();
            int finalScore = runQuizSession(quizzes, topic, level);

            view.displayQuizResult(finalScore, totalQuestions);

        } catch (IllegalStateException e) {
            view.displayError(e.getMessage());
        } finally {
            view.closeScanner();
        }
    }

    private List<Quiz> loadQuizzes(String topic, Difficulty level) {
        List<Quiz> quizzes = quizGenerator.generate(topic, level);
        if (quizzes.isEmpty()) {
            throw new IllegalStateException("퀴즈 데이터가 비어있습니다. API 연결을 확인해 주세요.");
        }
        return quizzes;
    }

    private int runQuizSession(List<Quiz> quizzes, String topic, Difficulty level) {
        int totalQuestions = quizzes.size();
        view.displayQuizStart(topic, level.getKoreanName(), totalQuestions);

        int score = 0;

        for (int i = 0; i < totalQuestions; i++) {
            Quiz quiz = quizzes.get(i);
            int questionNumber = i + 1;

            boolean isCorrect = processSingleRound(quiz, questionNumber, score, totalQuestions);

            if (isCorrect) {
                score++;
            }
        }
        return score;
    }

    private boolean processSingleRound(Quiz quiz, int questionNumber, int currentScore, int totalQuestions) {
        view.displayQuestion(quiz, questionNumber);

        String userAnswer = view.getUserAnswer();
        Feedback feedback = feedbackGenerator.generate(quiz, userAnswer);

        int displayScore = currentScore;
        if (feedback.getCorrect()) {
            displayScore = currentScore + 1;
        }

        view.displayFeedback(feedback, displayScore, totalQuestions);

        return feedback.getCorrect();
    }
}