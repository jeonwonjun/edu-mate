package com.edumate.domain.quiz;

public enum Difficulty {
    EASY("쉬움", "간단한 개념과 정의 위주로 출제", 1),
    MEDIUM("보통", "주요 기능과 활용법에 대한 심화된 이해도를 측정", 2),
    HARD("어려움", "특정 상황에서의 예외 처리, 성능 최적화 등 심화 내용을 포함하여 출제", 3);

    // 인스턴스 변수 2개로 제약 준수
    private final String koreanName;
    private final String promptInstruction;
    private final int level;

    Difficulty(String koreanName, String promptInstruction, int level) {
        this.koreanName = koreanName;
        this.promptInstruction = promptInstruction;
        this.level = level;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public String getPromptInstruction() {
        return promptInstruction;
    }

    public int getLevel() {
        return level;
    }
}