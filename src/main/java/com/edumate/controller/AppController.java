package com.edumate.controller;

import com.edumate.domain.feedback.Feedback;
import com.edumate.domain.feedback.FeedbackPromptContext;
import com.edumate.domain.quiz.AnswerChecker;
import com.edumate.domain.quiz.Difficulty;
import com.edumate.domain.quiz.Quiz;
import com.edumate.domain.session.QuizSession;
import com.edumate.infra.feedback.FeedbackPromptBuilder;
import com.edumate.infra.gemini.GeminiClient;
import com.edumate.infra.quiz.QuizPromptBuilder;
import com.edumate.service.feedback.FeedbackGenerator;
import com.edumate.service.quiz.DifficultyAdjuster;
import com.edumate.service.quiz.QuizGenerator;
import com.edumate.service.quiz.StreakBasedDifficultyAdjuster;
import com.edumate.ui.console.InputHandler;
import com.edumate.ui.console.OutputHandler;
import com.edumate.util.llm.LlmClient;
import com.google.gson.Gson;
import java.util.List;

public class AppController {
    private final QuizGenerator quizGenerator;
    private final FeedbackGenerator feedbackGenerator;
    private final AnswerChecker answerChecker;
    private final QuizSession quizSession;
    private final DifficultyAdjuster difficultyAdjuster;

    public AppController() {
        LlmClient llmClient = new GeminiClient();
        QuizPromptBuilder quizPromptBuilder = new QuizPromptBuilder();
        FeedbackPromptBuilder feedbackPromptBuilder = new FeedbackPromptBuilder();
        Gson gson = new Gson();

        this.quizGenerator = new QuizGenerator(llmClient, quizPromptBuilder, gson);
        this.feedbackGenerator = new FeedbackGenerator(llmClient, feedbackPromptBuilder);
        this.answerChecker = new AnswerChecker();
        this.quizSession = new QuizSession(Difficulty.MEDIUM);
        this.difficultyAdjuster = new StreakBasedDifficultyAdjuster();
    }

    public void run() {
        String topic = askTopic();
        Difficulty level = quizSession.getCurrentDifficulty();

        List<Quiz> quizzes = quizGenerator.generate(topic, level);
        if (quizzes.isEmpty()) {
            System.out.println("퀴즈 생성에 실패했습니다.");
            return;
        }

        for (Quiz quiz : quizzes) {
            boolean continueQuiz = askOneQuiz(quiz, level);
            if (!continueQuiz) {
                System.out.println("퀴즈를 종료합니다.");
                return;
            }
        }
    }

    private String askTopic() {
        System.out.println("무엇을 공부했나요?");
        System.out.println("한 줄로 입력 후 Enter를 누르세요.");
        return InputHandler.read();
    }

    private boolean askOneQuiz(Quiz quiz, Difficulty level) {
        OutputHandler.showQuiz(quiz, level);

        String userAnswer = InputHandler.read();
        if (isQuit(userAnswer)) {
            return false;
        }

        boolean correct = quiz.checkAnswer(userAnswer, answerChecker);

        FeedbackPromptContext feedbackPromptContext = new FeedbackPromptContext(quiz, userAnswer, correct);
        Feedback feedback = feedbackGenerator.generate(feedbackPromptContext);

        OutputHandler.showFeedback(feedback);

        updateSession(correct);

        return true;
    }

    private void updateSession(boolean correct) {
        if (correct) {
            quizSession.markCorrect();
        }

        if (!correct) {
            quizSession.markWrong();
        }

        Difficulty next = difficultyAdjuster.adjust(quizSession);

        quizSession.updateDifficulty(next);
    }

    private boolean isQuit(String userAnswer) {
        if (userAnswer == null) {
            return false;
        }

        return "q".equals(userAnswer.trim());
    }
}
