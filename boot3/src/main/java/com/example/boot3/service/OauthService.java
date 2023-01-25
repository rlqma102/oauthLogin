package com.example.boot3.service;

import com.example.boot3.enums.OauthAttributes;
import com.example.boot3.provider.JwtTokenProvider;
import com.example.boot3.provider.OauthProvider;
import com.example.boot3.dto.LoginResponse;
import com.example.boot3.dto.Member;
import com.example.boot3.dto.OauthTokenResponse;
import com.example.boot3.dto.UserProfile;
import com.example.boot3.repository.InMemoryProviderRepository;
import com.example.boot3.repository.MemberRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Service
public class OauthService {

    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public OauthService(InMemoryProviderRepository inMemoryProviderRepository, MemberRepository memberRepository,
                        JwtTokenProvider jwtTokenProvider) {
        this.inMemoryProviderRepository = inMemoryProviderRepository;
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(String providerName, String code) {
        // 프론트에서 넘어온 provider 이름을 통해 InMemoryProviderRepository에서 OauthProvider 가져오기
        OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

        System.out.println("code");
        System.out.println(code);
        System.out.println("code");
        // access token 가져오기
        OauthTokenResponse tokenResponse = getToken(code, provider);
        System.out.println("tokenResponse");
        System.out.println(tokenResponse.toString());
        System.out.println("tokenResponse");

        // 유저 정보 가져오기
        UserProfile userProfile = getUserProfile(providerName, tokenResponse, provider);

        // 유저 DB에 저장
        Member member = saveOrUpdate(userProfile);

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        // TODO 레디스에 refresh token 추가
        // redisUtil.setData(String.valueOf(member.getId()), refreshToken);

        return LoginResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .imageUrl(member.getImageUrl())
                .role(member.getRole())
                .auth(member.getAuth())
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Member saveOrUpdate(UserProfile userProfile) {
        Member member = memberRepository.findByOauthId(userProfile.getOauthId())
                .map(entity -> entity.update(userProfile.getName(), userProfile.getEmail(), userProfile.getImageUrl()))
                .orElseGet(userProfile::toMember);
        return memberRepository.save(member);
    }

    private OauthTokenResponse getToken(String code, OauthProvider provider) {
        Map result = WebClient.create()
                .post()
                .uri(provider.getTokenUrl())
                .headers(header -> {
                    header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        System.out.println("result");
        System.out.println(result.toString());
        System.out.println("result");

        return new OauthTokenResponse(result.get("access_token").toString(), result.get("scope").toString(), result.get("token_type").toString());
//        return WebClient.create()
//                .post()
//                .uri(provider.getTokenUrl())
//                .headers(header -> {
//                    header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
//                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
//                })
//                .bodyValue(tokenRequest(code, provider))
//                .retrieve()
//                .bodyToMono(OauthTokenResponse.class)
//                .block();
    }

    private MultiValueMap<String, String> tokenRequest(String code, OauthProvider provider) {
        System.out.println("provider.getRedirectUrl()");
        System.out.println(provider.getRedirectUrl());
        System.out.println("provider.getRedirectUrl()");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUrl());
        return formData;
    }

    private UserProfile getUserProfile(String providerName, OauthTokenResponse tokenResponse, OauthProvider provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
        return OauthAttributes.extract(providerName, userAttributes);
    }

    // OAuth 서버에서 유저 정보 map으로 가져오기
    private Map<String, Object> getUserAttributes(OauthProvider provider, OauthTokenResponse tokenResponse) {
        System.out.println("provider.getUserInfoUrl()");
        System.out.println(provider.getUserInfoUrl());
        System.out.println("tokenResponse.getAccessToken()");
        System.out.println(tokenResponse.getAccessToken());
        System.out.println("tokenResponse.getAccessToken()");
        return WebClient.create()
                .get()
                .uri(provider.getUserInfoUrl())
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }
}

