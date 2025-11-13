package com.edumate.util;

public enum Information {
    INPUT_QUESTION("무엇을 공부하셨나요?");

    private final String information;

    Information (String information) {
        this.information = information;
    }

    public String getMessage() {
        return this.information;
    }
}
