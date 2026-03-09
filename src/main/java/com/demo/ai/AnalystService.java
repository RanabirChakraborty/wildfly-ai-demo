package com.demo.ai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Conversational AI Service
 *
 * This service uses LangChain4j with Ollama to provide conversational AI responses.
 * It creates an AI service implementation at startup using the Analyst interface.
 */
@ApplicationScoped
public class AnalystService {

    private Analyst analyst;

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

    public String analyze(String text) {
        if (analyst == null) {
            return "Error: AI service not initialized";
        }
        return analyst.analyze(text);
    }
}
