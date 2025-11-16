package com.edumate.io;

import com.edumate.domain.feedback.Feedback;
import com.edumate.domain.quiz.Quiz;

public class OutputHandler {
    public static void printQuiz(Quiz quiz) {
        System.out.println("문제: " + quiz.getQuestion());

        for (String option : quiz.getOptions()) {
            System.out.println(option);
        }
    }

    public static void printFeedback(Feedback feedback) {
        System.out.println(feedback);
        System.out.println();
    }
}
