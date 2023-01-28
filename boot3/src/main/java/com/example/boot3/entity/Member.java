package com.example.boot3.entity;

import com.example.boot3.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "올바른 이메일 형식으로 입력해 주세요")
    private String email;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String auth;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요")
    private String password;

    public Member() {
    }

    @Builder
    public Member(Long id, String oauthId, String name, String email, String imageUrl, Role role, String auth, String password) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.auth = auth;
        this.password = password;
    }

    public Member update(String name, String email, String imageUrl, String auth, String password) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.auth = auth;
        this.password = password;
        return this;
    }
}
