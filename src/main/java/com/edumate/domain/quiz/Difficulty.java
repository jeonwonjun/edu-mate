package com.edumate.domain.quiz;

public enum Difficulty {
    EASY("쉬움", "간단한 개념과 정의 위주로 출제"),
    MEDIUM("보통", "주요 기능과 활용법에 대한 심화된 이해도를 측정"),
    HARD("어려움", "특정 상황에서의 예외 처리, 성능 최적화 등 심화 내용을 포함하여 출제");

    private final String koreanName;
    private final String promptInstruction;

    Difficulty(String koreanName, String promptInstruction) {
        this.koreanName = koreanName;
        this.promptInstruction = promptInstruction;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public String getPromptInstruction() {
        return promptInstruction;
    }
}