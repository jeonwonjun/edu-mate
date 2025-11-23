package com.edumate.service.feedback;

import com.edumate.domain.feedback.Feedback;
import com.edumate.domain.feedback.FeedbackPromptContext;
import com.edumate.infra.feedback.FeedbackJsonParser;
import com.edumate.infra.feedback.FeedbackPromptBuilder;
import com.edumate.util.llm.LlmClient;
import java.util.Optional;

public class FeedbackGenerator {
    private final LlmClient llmClient;
    private final FeedbackPromptBuilder feedbackPromptBuilder;

    public FeedbackGenerator(LlmClient llmClient,
                             FeedbackPromptBuilder feedbackPromptBuilder) {
        this.llmClient = llmClient;
        this.feedbackPromptBuilder = feedbackPromptBuilder;
    }

    public Feedback generate(FeedbackPromptContext context) {
        String prompt = feedbackPromptBuilder.build(context);

        Optional<String> response = llmClient.generate(prompt);
        if (response.isEmpty()) {
            return new Feedback(context.getCorrect(), "피드백 생성 실패");
        }

        FeedbackJsonParser parser = new FeedbackJsonParser(context.getCorrect());
        return parser.parse(response.get());
    }
}
