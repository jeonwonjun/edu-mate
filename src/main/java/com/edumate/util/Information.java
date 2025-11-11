package com.edumate.util;

public enum Information {
    INPUT_QUESTION("무엇이든 물어보세요.");

    private final String information;

    Information (String information) {
        this.information = information;
    }

    public String getMessage() {
        return this.information;
    }
}
