package com.edumate.util.llm;

public interface PromptBuilder<C> {
    String build(C context);
}
