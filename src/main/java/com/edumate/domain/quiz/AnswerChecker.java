package com.edumate.domain.quiz;

import java.util.List;

public class AnswerChecker {

    public boolean isCorrect(String correctAnswer,
                             List<String> options,
                             String userInput) {

        String normalizedUserAnswer = normalize(userInput);
        if (normalizedUserAnswer.isEmpty()) {
            return false;
        }

        if (isNumeric(normalizedUserAnswer)) {
            return checkByIndex(correctAnswer, options, normalizedUserAnswer);
        }

        String normalizedCorrect = normalize(correctAnswer);

        if (normalizedCorrect.equals(normalizedUserAnswer)) {
            return true;
        }

        return normalizedCorrect.contains(normalizedUserAnswer);
    }

    private boolean checkByIndex(String correctAnswer,
                                 List<String> options,
                                 String numericInput) {

        int index = Integer.parseInt(numericInput) - 1;
        if (index < 0 || index >= options.size()) {
            return false;
        }

        String selectedOption = normalize(options.get(index));
        String normalizedCorrect = normalize(correctAnswer);

        return selectedOption.equals(normalizedCorrect);
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
