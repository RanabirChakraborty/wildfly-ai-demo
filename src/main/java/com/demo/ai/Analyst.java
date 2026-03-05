package com.demo.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * AI Service Interface
 *
 * This interface defines the contract for sentiment analysis.
 * LangChain4j's AiServices.create() will generate an implementation
 * that uses the configured ChatLanguageModel.
 *
 * The @SystemMessage defines the AI's role and behavior.
 * The @UserMessage parameter accepts the text to analyze.
 */
public interface Analyst {

    /**
     * Analyzes the sentiment of the provided text.
     *
     * @param text The text to analyze
     * @return AI-generated sentiment analysis
     */
    @SystemMessage("You are a professional sentiment analyst.")
    String analyze(@UserMessage String text);
}
