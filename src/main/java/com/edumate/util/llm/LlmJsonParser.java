package com.edumate.util.llm;

public interface LlmJsonParser<R> {
    R parser(String jsonText);
}
