package com.edumate.util.llm;

import com.edumate.infra.gemini.GeminiClient;
import java.util.Optional;

public class LlmTask<C, R> {
    private final PromptBuilder<C> promptBuilder;
    private final LlmClient client;
    private final LlmJsonParser<R> parser;

    public LlmTask(PromptBuilder<C> promptBuilder, GeminiClient client, LlmJsonParser<R> parser) {
        this.promptBuilder = promptBuilder;
        this.client = client;
        this.parser = parser;
    }

    public Optional<R> execute(C context) {
        String prompt = promptBuilder.build(context);
        Optional<String> response = client.generate(prompt);

        if (response.isEmpty()) {
            return Optional.empty();
        }
        R result = parser.parse(response.get());
        return Optional.ofNullable(result);
    }
}
