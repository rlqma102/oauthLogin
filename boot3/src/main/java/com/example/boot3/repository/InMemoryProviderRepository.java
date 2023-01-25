package com.example.boot3.repository;

import com.example.boot3.provider.OauthProvider;

import java.util.HashMap;
import java.util.Map;

public class InMemoryProviderRepository {

    private final Map<String, OauthProvider> providers;

    public InMemoryProviderRepository(Map<String, OauthProvider> providers) {
        this.providers = new HashMap<>(providers);
    }

    public OauthProvider findByProviderName(String name) {
        Map<String, OauthProvider> result = new HashMap<String, OauthProvider>();
        result.put("github", new OauthProvider("9adb75cbb02ec73228b3","f8b1699699b17afd81919d225e5ae1c2d6719071", "http://localhost:8080/redirect/oauth", "https://github.com/login/oauth/access_token", "https://api.github.com/user"));
        result.put("google", new OauthProvider("130018349769-enrk74a1tvt8rhaoockie6gv2gd8ntm0.apps.googleusercontent.com", "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", "http://localhost:3000/redirect/oauth", "https://www.googleapis.com/oauth2/v4/token", "https://www.googleapis.com/oauth2/v3/userinfo"));
        return result.get(name);
    }
}
