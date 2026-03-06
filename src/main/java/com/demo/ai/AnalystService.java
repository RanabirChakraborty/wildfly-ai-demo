package com.demo.ai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
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
        // Get model name from environment variable, default to gemma:2b
        String modelName = System.getenv("OLLAMA_MODEL");
        if (modelName == null || modelName.isEmpty()) {
            modelName = "gemma:2b";
            System.out.println("INFO: OLLAMA_MODEL not set. Using default: " + modelName);
        }

        // Get Ollama base URL from environment variable, default to localhost
        String baseUrl = System.getenv("OLLAMA_BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "http://localhost:11434";
            System.out.println("INFO: OLLAMA_BASE_URL not set. Using default: " + baseUrl);
        }

        // Create the ChatLanguageModel
        ChatLanguageModel chatModel = OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .temperature(0.7)
                .timeout(java.time.Duration.ofSeconds(60))
                .build();

        // Generate the AI service implementation using LangChain4j
        this.analyst = AiServices.create(Analyst.class, chatModel);

        System.out.println("AI Analyst Service initialized successfully with Ollama model: " + modelName);
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
