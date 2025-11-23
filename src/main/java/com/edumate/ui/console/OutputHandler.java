package com.edumate.ui.console;

import com.edumate.domain.feedback.Feedback;
import com.edumate.domain.quiz.Difficulty;
import com.edumate.domain.quiz.Quiz;

public class OutputHandler {


    public static void showQuiz(Quiz quiz, Difficulty level) {
        System.out.println();
        System.out.println("================================");
        System.out.println("난이도: " + level.getKoreanName());
        System.out.println("문제:");
        System.out.println(quiz.getQuestion());
        System.out.println();

        int index = 1;
        for (String option : quiz.getOptions()) {
            System.out.println(index + ". " + option);
            index++;
        }

        System.out.println();
        System.out.printf("정답 번호 또는 내용을 입력하세요. (종료: q): ");
    }

    public static void showFeedback(Feedback feedback) {
        System.out.println();
        System.out.println("[피드백]");
        System.out.println(feedback.getMessage());
    }
}
