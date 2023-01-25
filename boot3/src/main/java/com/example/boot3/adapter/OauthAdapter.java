package com.example.boot3.adapter;

import com.example.boot3.provider.OauthProvider;
import com.example.boot3.entity.OauthProperties;

import java.util.HashMap;
import java.util.Map;

//security의 OAuth2ClientPropertiesRegistrationAdapter 역할
public class OauthAdapter {

    private OauthAdapter() {}

    // OauthProperties를 OauthProvider로 변환해준다.
    public static Map<String, OauthProvider> getOauthProviders(OauthProperties properties) {
        Map<String, OauthProvider> oauthProvider = new HashMap<>();

        properties.getUser().forEach((key, value) -> {
            System.out.println("key   : "+key);
            System.out.println("value : "+value);
            oauthProvider.put(key, new OauthProvider(value, properties.getProvider().get(key)));
        });
        return oauthProvider;
    }
}


