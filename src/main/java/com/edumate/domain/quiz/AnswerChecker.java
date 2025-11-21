package com.edumate.domain.quiz;

import java.util.List;

public class AnswerChecker {

    public boolean isCorrect(String correctAnswer,
                             List<String> options,
                             String userInput) {

        String normalizedUserAnswer = normalize(userInput);

        if (isNumeric(normalizedUserAnswer)) {
            return checkByNumeric(correctAnswer, options, normalizedUserAnswer);
        }

        String normalizedCorrect = normalize(correctAnswer);
        return normalizedCorrect.contains(normalizedUserAnswer);
    }

    private boolean checkByNumeric(String correctAnswer,
                                   List<String> options,
                                   String normalizedUserAnswer) {

        int index = Integer.parseInt(normalizedUserAnswer) - 1;
        if (index < 0 || index >= options.size()) {
            return false;
        }

        String optionText = normalize(options.get(index));
        String correct = normalize(correctAnswer);
        return optionText.equals(correct);
    }

    private boolean isNumeric(String userInput) {
        return userInput.matches("\\d+");
    }

    private String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.trim()
                .replaceAll("\\s+", " ")
                .toLowerCase();
    }
}
