package com.sync.dataSynchronization.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.time.Duration;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.loadcell.repository")
public class ElasticsearchConfig extends ElasticsearchConfiguration {


    @Override
    public @NotNull ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .usingSsl("b3a8324d80680ff8ffb8e12dfefbc921b3f6759a4a403a6643fcda843e3ff843")
                .withBasicAuth("elastic", "+e7KZTX9bIYspIR_eLH=")
                .withConnectTimeout(Duration.ofSeconds(10))
                .withSocketTimeout(Duration.ofSeconds(10))
                .build();
    }
}
