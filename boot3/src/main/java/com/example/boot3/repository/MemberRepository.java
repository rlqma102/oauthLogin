package com.example.boot3.repository;

import com.example.boot3.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthId(String id);
    List<Member> findAll();
}
