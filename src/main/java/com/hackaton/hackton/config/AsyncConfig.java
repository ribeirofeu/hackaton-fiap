package com.hackaton.hackton.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableRetry
public class AsyncConfig {
}
