package com.example.boot3.config;

import com.example.boot3.provider.OauthProvider;
import com.example.boot3.adapter.OauthAdapter;
import com.example.boot3.entity.OauthProperties;
import com.example.boot3.repository.InMemoryProviderRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(OauthProperties.class)
public class OauthConfig {

    private final OauthProperties properties;

    public OauthConfig(OauthProperties properties) {
        this.properties = properties;
    }

    @Bean
    public InMemoryProviderRepository inMemoryProviderRepository() {
        Map<String, OauthProvider> providers = OauthAdapter.getOauthProviders(properties);

        return new InMemoryProviderRepository(providers);
    }
}
