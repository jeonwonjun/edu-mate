package com.edumate.util.llm;

import java.util.Optional;

public interface LlmClient {
    Optional<String> generate(String prompt);
}
