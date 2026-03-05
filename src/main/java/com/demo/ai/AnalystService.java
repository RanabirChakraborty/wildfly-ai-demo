package com.demo.ai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * AI Sentiment Analysis Service
 *
 * This service uses LangChain4j to analyze sentiment of text.
 * It creates an AI service implementation at startup using the Analyst interface.
 */
@ApplicationScoped
public class AnalystService {

    private Analyst analyst;

    /**
     * Initialize the AI service on startup.
     * Creates a ChatLanguageModel and generates an implementation of the Analyst interface.
     */
    @PostConstruct
    public void init() {
        // Get API key from environment variable
        String apiKey = System.getenv("OPENAI_API_KEY");

        // For demo purposes, use a dummy key if not set
        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = "demo-key-not-configured";
            System.out.println("WARNING: OPENAI_API_KEY not set. API calls will fail.");
        }

        // Create the ChatLanguageModel
        ChatLanguageModel chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-3.5-turbo")
                .temperature(0.7)
                .timeout(java.time.Duration.ofSeconds(60))
                .build();

        // Generate the AI service implementation using LangChain4j
        this.analyst = AiServices.create(Analyst.class, chatModel);

        System.out.println("AI Analyst Service initialized successfully");
    }

    /**
     * Analyze the sentiment of the provided text.
     *
     * @param text The text to analyze
     * @return AI-generated sentiment analysis
     */
    public String analyze(String text) {
        if (analyst == null) {
            return "Error: AI service not initialized";
        }
        return analyst.analyze(text);
    }
}
