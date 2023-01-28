package com.example.boot3.controller;

import com.example.boot3.dto.UserProfile;
import com.example.boot3.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private  final MemberService memberService;
    private final UserProfile userProfile;

    @PostMapping("/join")
    public String join() {
        memberService.joinMember(userProfile.getEmail(email));

        return ;
    }
}
