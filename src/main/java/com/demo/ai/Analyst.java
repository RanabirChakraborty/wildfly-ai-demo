package com.demo.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * AI Service Interface
 *
 * This interface defines the contract for conversational AI interactions.
 * LangChain4j's AiServices.create() will generate an implementation
 * that uses the configured ChatLanguageModel (Ollama gemma:2b).
 *
 * The @SystemMessage defines the AI's role and behavior.
 * The @UserMessage parameter accepts the text input from users.
 */
public interface Analyst {

    /**
     * Analyzes the text and provides a conversational AI response.
     *
     * @param text The text to analyze
     * @return AI-generated conversational response
     */
    @SystemMessage("You are a helpful AI assistant that analyzes text sentiment and provides friendly, conversational feedback.")
    String analyze(@UserMessage String text);
}
