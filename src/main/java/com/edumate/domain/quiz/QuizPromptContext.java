package com.edumate.domain.quiz;

public class QuizPromptContext {
    private final String studyNotes;
    private final Difficulty difficulty;
    private final int count;

    public QuizPromptContext(String studyNotes, Difficulty difficulty, int count) {
        this.studyNotes = studyNotes;
        this.difficulty = difficulty;
        this.count = count;
    }

    public String getStudyNotes() {
        return studyNotes;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getCount() {
        return count;
    }
}
