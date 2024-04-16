package com.earuile.bubble.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class RestTaskExecutorConfig {
    @Bean(destroyMethod = "close")
    public ExecutorService uiExecutorService() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> Thread.currentThread().setName("server_interaction_thread"));
        return executorService;
    }
}
