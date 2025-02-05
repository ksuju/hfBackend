package com.ll.hfback.domain.member.auth.service;

import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PhoneVerificationService {

  private final SmsService smsService;
  private final RedisTemplate<String, String> redisTemplate;

  private static final String VERIFICATION_PREFIX = "phone:verification:";
  private static final String DAILY_COUNT_PREFIX = "phone:daily-count:";  // 하루에 보낸 횟수 기록
  private static final String LAST_SENT_PREFIX = "phone:last-sent:";  // 마지막 전송 시간 기록

  private static final long VERIFICATION_TTL = 3; // 3분
  private static final long RESEND_WAIT_TIME = 3;  // 3분
  private static final int MAX_DAILY_SENDS = 3;  // 3회


  public void sendVerificationCode(String phoneNumber) {
    // 재발송 대기 시간 확인
    String lastSentKey = LAST_SENT_PREFIX + phoneNumber;
    String lastSentTime = redisTemplate.opsForValue().get(lastSentKey);
    if (lastSentTime != null) {
      long timeDiff = System.currentTimeMillis() - Long.parseLong(lastSentTime);
      if (timeDiff < RESEND_WAIT_TIME * 60 * 1000) {
        throw new ServiceException(ErrorCode.SMS_RESEND_TOO_EARLY);
      }
    }

    // 하루에 보낸 횟수 확인
    String dailyCountKey = DAILY_COUNT_PREFIX + phoneNumber + ":" + LocalDate.now();
    String countStr = redisTemplate.opsForValue().get(dailyCountKey);
    int count = countStr == null ? 0 : Integer.parseInt(countStr);
    if (count >= MAX_DAILY_SENDS) {
      throw new ServiceException(ErrorCode.SMS_DAILY_LIMIT_EXCEEDED);
    }


    // SMS 보내기
    redisTemplate.delete(VERIFICATION_PREFIX + phoneNumber);  // 이전 기록 삭제

    String verificationCode = generateRandomCode();  // 6자리 인증번호 생성
    redisTemplate.opsForValue().set(
        VERIFICATION_PREFIX + phoneNumber,
        verificationCode,
        VERIFICATION_TTL,
        TimeUnit.MINUTES
    );

    String message = String.format("[HiddenFriend] 인증번호 [%s]를 입력해주세요.", verificationCode);
    smsService.sendMessage(phoneNumber, message);

    redisTemplate.opsForValue().set(lastSentKey, String.valueOf(System.currentTimeMillis()));
    redisTemplate.expire(lastSentKey, 1, TimeUnit.DAYS);

    redisTemplate.opsForValue().increment(dailyCountKey);
    redisTemplate.expire(dailyCountKey, 1, TimeUnit.DAYS);
  }


  public boolean verifyCode(String phoneNumber, String code) {
    String storedCode = redisTemplate.opsForValue().get(VERIFICATION_PREFIX + phoneNumber);
    if (storedCode == null) {
      throw new ServiceException(ErrorCode.SMS_CODE_EXPIRED);
    }

    boolean isValid = storedCode.equals(code);
    if (isValid) {
      redisTemplate.delete(VERIFICATION_PREFIX + phoneNumber);        // 인증 후 바로 삭제
    }

    return isValid;
  }



  private String generateRandomCode() {
    return String.format("%06d", new Random().nextInt(1000000));
  }

}
