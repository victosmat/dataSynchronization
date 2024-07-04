package com.sync.dataSynchronization.config;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public MeterRegistry meterRegistry() {
        // You can return any MeterRegistry implementation you need
        // For example, PrometheusMeterRegistry or SimpleMeterRegistry
        return new SimpleMeterRegistry();
    }
}

