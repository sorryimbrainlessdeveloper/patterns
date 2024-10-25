package org.example.chainofresponsibility.config;

import org.example.chainofresponsibility.handlers.FileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.example.chainofresponsibility.utils.MessagesUtils.NO_HANDLERS_AVAILABLE;

@Configuration
public class FileHandlerConfig {

    @Bean
    public FileHandler fileHandlerChain(List<FileHandler> handlers) {
        if (handlers.isEmpty()) throw new IllegalStateException(NO_HANDLERS_AVAILABLE);

        // We go through the list of handlers and create a chain
        for (int i = 0; i < handlers.size() - 1; i++) {
            handlers.get(i).setNextHandler(handlers.get(i + 1));
        }

        // Return the first handler, the beginning of the chain
        return handlers.get(0);
    }
}


