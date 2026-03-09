package com.demo.ai;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * REST Endpoint for AI Demo
 *
 * This is the gateway for external requests.
 * It exposes a simple REST API that accepts text and returns AI-powered analysis.
 *
 * Endpoint: POST /api/demo/check
 */
@Path("/demo")
public class DemoResource {

    @Inject
    AnalystService analystService;
    @POST
    @Path("/check")
    @Produces(MediaType.TEXT_PLAIN)
    public String check(String message) {
        if (message == null || message.trim().isEmpty()) {
            return "Error: Please provide text to analyze";
        }
        return analystService.analyze(message);
    }
}
