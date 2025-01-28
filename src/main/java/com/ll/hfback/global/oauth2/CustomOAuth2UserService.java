package com.ll.hfback.global.oauth2;

import com.ll.hfback.domain.member.auth.service.AuthService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final AuthService authService;

  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    String oauthId = oAuth2User.getName();
    String loginType = userRequest
        .getClientRegistration()
        .getRegistrationId()
        .toUpperCase(Locale.getDefault());


    Map<String, Object> attributes = oAuth2User.getAttributes();

    Map<String, String> properties = (Map<String, String>) attributes.get("properties");
    System.out.println("카카오 로그인 정보 꺼낸다 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    String nickname = properties.get("nickname");
    System.out.println("nickname = " + nickname);
    String profilePath = properties.get("profile_image");
    System.out.println(
        "profilePath = " + profilePath
    );

    Map<String, String> kakaoAccount = (Map<String, String>) attributes.get("kakao_account");
    String email = kakaoAccount.get("email");
    System.out.println("email = " + email);
    String providerId = loginType + "__" + oauthId;

    Member member = authService.modifyOrSignup(
        email, nickname, loginType, profilePath, providerId
    );

    return new SecurityUser(
        member.getId(),
        member.getEmail(),
        "",
        member.getNickname(),
        member.getProfilePath(),
        member.getAuthorities());
  }
}
