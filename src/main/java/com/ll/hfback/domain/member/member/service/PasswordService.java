package com.ll.hfback.domain.member.member.service;

import com.ll.hfback.domain.member.alert.service.AlertEventPublisher;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordService {

  private final PasswordEncoder passwordEncoder;
  private final MemberRepository memberRepository;
  private final AlertEventPublisher alertEventPublisher;


  @Transactional
  public void changePassword(String email, String currentPassword, String newPassword) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new ServiceException(ErrorCode.INVALID_EMAIL));

    if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
      throw new ServiceException(ErrorCode.INVALID_PASSWORD);
    }
    member.setPassword(passwordEncoder.encode(newPassword));

    alertEventPublisher.publishPasswordChange(member.getId());
  }


  @Transactional
  public void resetPassword(String email, String newPassword) {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new ServiceException(ErrorCode.INVALID_EMAIL));

    member.setPassword(passwordEncoder.encode(newPassword));
  }
}
