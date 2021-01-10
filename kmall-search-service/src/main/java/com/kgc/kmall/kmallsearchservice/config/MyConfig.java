package com.kgc.kmall.kmallsearchservice.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shkstart
 * @create 2021-01-05 15:50
 */
@Configuration
public class MyConfig {
    @Bean
    public JestClient getJestCline() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://192.168.164.130:9200")
                .multiThreaded(true)
                .build());
        return factory.getObject();
    }
}
