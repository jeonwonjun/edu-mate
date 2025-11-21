package com.edumate.util.llm;

public interface LlmJsonParser<R> {
    R parse(String jsonText);
}
