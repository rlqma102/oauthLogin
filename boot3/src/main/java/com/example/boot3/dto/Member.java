package com.example.boot3.dto;

import com.example.boot3.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;

    private String name;

    private String email;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String auth;

    protected Member() {
    }

    @Builder
    public Member(Long id, String oauthId, String name, String email, String imageUrl, Role role, String auth) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.auth = auth;
    }

    public Member update(String name, String email, String imageUrl) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        return this;
    }
}
