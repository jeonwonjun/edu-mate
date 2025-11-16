package com.edumate;


import com.edumate.domain.feedback.Feedback;
import com.edumate.domain.feedback.FeedbackGenerator;
import com.edumate.domain.quiz.Quiz;
import com.edumate.domain.quiz.QuizGenerator;
import com.edumate.io.GeminiClient;
import com.edumate.io.InputHandler;
import com.edumate.io.OutputHandler;
import com.edumate.util.Information;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        GeminiClient client = new GeminiClient();
        QuizGenerator quizGenerator = new QuizGenerator(client);
        FeedbackGenerator feedbackGenerator = new FeedbackGenerator(client);
        System.out.println(Information.INPUT_QUESTION.getMessage());
        String question = InputHandler.read();

        List<Quiz> quizzes = quizGenerator.generate(question, 3);

        for (Quiz quiz : quizzes) {
            OutputHandler.printQuiz(quiz);

            System.out.print("답변: ");
            String userAnswer = InputHandler.read();
            Feedback feedback = feedbackGenerator.generate(quiz, userAnswer);

            OutputHandler.printFeedback(feedback);
        }
    }
}