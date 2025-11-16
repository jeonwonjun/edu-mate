package com.edumate.domain.quiz;

public class AnswerChecker {

    public boolean isCorrect(Quiz quiz, String userInput) {
        String normalizedUserAnswer = normalize(userInput);

        if (isNumeric(normalizedUserAnswer)) {
            return checkByNumeric(quiz, normalizedUserAnswer);
        }

        String normalizedCorrect = normalize(quiz.getAnswer());
        return normalizedUserAnswer.equals(normalizedCorrect);
    }

    private boolean checkByNumeric(Quiz quiz, String normalizedUserAnswer) {
        int index = Integer.parseInt(normalizedUserAnswer) - 1;
        if (index >= 0 && index < quiz.getOptions().size()) {
            String optionText = normalize(quiz.getOptions().get(index));
            String correct = normalize(quiz.getAnswer());
            return optionText.equals(correct);
        }
        return false;
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
