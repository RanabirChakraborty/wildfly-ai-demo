package com.demo.ai;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * JAX-RS Configuration Class
 *
 * This class activates JAX-RS for the application.
 * The @ApplicationPath annotation defines the base URI path for all REST endpoints.
 * All REST resources will be available under /api path.
 */
@ApplicationPath("/api")
public class RestConfig extends Application {
    // Empty class - the annotation does all the work
}
