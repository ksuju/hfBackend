package com.ll.hfback.standard.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;


public class Ut {

  public static class str {
    public static boolean isBlank(String str) {
      return str == null || str.trim().isEmpty();
    }

    public static String maskEmail(String email) {
      if (isBlank(email)) {
        return email;
      }

      String[] parts = email.split("@");
      if (parts.length != 2) {
        return email;
      }

      String name = parts[0];
      String domain = parts[1];

      String maskedName = name.length() <= 3
          ? name.substring(0, name.length() - 1) + "*"
          : name.charAt(0) + "*".repeat(name.length() - 1);

      return maskedName + "@" + domain;
    }
  }


  public static class json {
    private static final ObjectMapper om = new ObjectMapper();
    @SneakyThrows
    public static String toString(Object obj) {
      return om.writeValueAsString(obj);
    }
  }


  public static class jwt {
    public static String toString(String secret, long expireSeconds, Map<String, Object> body) {
      Date issuedAt = new Date();
      Date expiration = new Date(issuedAt.getTime() + 1000L * expireSeconds);
      SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
      String jwt = Jwts.builder()
          .claims(body)
          .issuedAt(issuedAt)
          .expiration(expiration)
          .signWith(secretKey)
          .compact();
      return jwt;
    }

    public static boolean isValid(String secret, String jwtStr) {
      SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
      try {
        Jwts
            .parser()
            .verifyWith(secretKey)
            .build()
            .parse(jwtStr);
      } catch (Exception e) {
        return false;
      }
      return true;
    }

    public static Map<String, Object> payload(String secret, String jwtStr) {
      SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
      try {
        return (Map<String, Object>) Jwts
            .parser()
            .verifyWith(secretKey)
            .build()
            .parse(jwtStr)
            .getPayload();
      } catch (Exception e) {
        return null;
      }
    }
  }
}
