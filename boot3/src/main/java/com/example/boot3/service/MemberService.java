package com.example.boot3.service;

import com.example.boot3.entity.Member;
import com.example.boot3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    //기존 소셜로그인 되어있는 사용자는 password만 추가로 저장함
    //소셜로그인 하지 않은 사용자는 아래 정보 모두 저장함
    public Member joinMember(String password) {
        Member member = new Member();
        member.setPassword(member.getPassword());

        member.setId();
        member.setName();
        //기존에 등록되지 않은 email일 경우만 set
        member.setEmail();
        member.getAuth();
        member.getImageUrl();

        this.memberRepository.save(member);

        return member;
    }
}
