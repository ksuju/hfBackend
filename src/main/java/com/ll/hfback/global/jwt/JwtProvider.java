package com.ll.hfback.global.jwt;

import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.util.Ut;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtProvider {

    @Value("${custom.jwt.secretKey}")
    private String secretKeyOrigin;

    @Value("${custom.accessToken.expirationSeconds}")
    private int accessTokenExpirationSeconds;

    private SecretKey cacheSecretKey;

    public SecretKey getSecretKey() {
        if (cacheSecretKey == null) {
            cacheSecretKey = _getSecretKey();
        }
        return cacheSecretKey;
    }

    private SecretKey _getSecretKey() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyOrigin.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    public String genAccessToken(Member member) {
        return genToken(member, accessTokenExpirationSeconds);
    }

    public String genRefreshToken(Member member) {
        return genToken(member, 60 * 60 * 24 * 365);  // 1년으로 설정함
    }

    public String genToken(Member member, int seconds) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getId());
        claims.put("email", member.getEmail());
        claims.put("nickname", member.getNickname());
        claims.put("role", member.getRole());
        claims.put("state", member.getState());

        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now + 1000L * seconds);
        return Jwts.builder().claim("body", Ut.json.toStr(claims)).setExpiration(accessTokenExpiresIn)
            .signWith(getSecretKey(), SignatureAlgorithm.HS512).compact();
    }

    public Map getClaims(String token) {
        return null;
    }

    public boolean verify(String token) {
        return false;
    }

}
