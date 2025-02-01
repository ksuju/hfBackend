package com.ll.hfback.domain.member.member.repository;

import com.ll.hfback.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long>  {
    Optional<Member> findByEmail(String email);
    List<Member> findAllByPhoneNumber(String phoneNumber);
}
