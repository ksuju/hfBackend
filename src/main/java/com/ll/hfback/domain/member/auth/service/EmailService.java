package com.ll.hfback.domain.member.auth.service;

import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender emailSender;
  private final StringRedisTemplate redisTemplate;
  private final SecureRandom secureRandom = new SecureRandom();


  private static final String KEY_PREFIX = "email:verification:";


  // 인증 메일 발송
  @Async
  public void sendVerificationEmail(String email) {
    String verificationCode = _generateVerificationCode();
    _saveVerificationCode(email, verificationCode);

    String title = "이메일 인증 코드";
    String text = String.format("""
        안녕하세요. 숨은친구찾기입니다.
        인증 코드: %s
        
        해당 인증 코드는 30분간 유효합니다.
        """, verificationCode);

    _sendEmail(email, title, text);
  }


  // 인증 코드 검증
  public boolean verifyCode(String email, String code) {
    String key = KEY_PREFIX + email;
    String savedCode = redisTemplate.opsForValue().get(key);

    log.debug("Verification attempt - Email: {}, Input Code: {}, Saved Code: {}", email, code, savedCode);

    if (savedCode == null) {
      throw new ServiceException(ErrorCode.EMAIL_VERIFICATION_NOT_FOUND);
    }

    if (!savedCode.equals(code)) {
      throw new ServiceException(ErrorCode.EMAIL_VERIFICATION_NOT_MATCH);
    }

    redisTemplate.delete(key);
    return true;
  }


  private void _sendEmail(String toEmail, String title, String text) {
    SimpleMailMessage emailForm = _createEmailForm(toEmail, title, text);
    try {
      emailSender.send(emailForm);

    } catch (RuntimeException e) {
      throw new ServiceException(ErrorCode.EMAIL_SEND_FAIL);
    }
  }


  // 이메일 폼 새성
  private SimpleMailMessage _createEmailForm(String toEmail, String title, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(title);
    message.setText(text);

    return message;
  }


  // 인증 코드 생성
  private String _generateVerificationCode() {
    return String.format("%06d", secureRandom.nextInt(1000000));  // 6자리 숫자
  }


  // 인증 코드 저장 (Redis : 30분 유효)
  public void _saveVerificationCode(String email, String code) {
    String key = KEY_PREFIX + email;
    redisTemplate.opsForValue()
        .set(key, code, Duration.ofMinutes(30));  // TTL 설정 추가
  }

}
