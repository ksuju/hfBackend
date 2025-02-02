package com.ll.hfback.global.oauth2;

import com.ll.hfback.domain.member.auth.service.AuthService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.service.MemberService;
import com.ll.hfback.domain.member.member.service.SocialConnectService;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import com.ll.hfback.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Locale;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final AuthService authService;
  private final MemberService memberService;
  private final SocialConnectService socialConnectService;

  @Transactional
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    String oauthId = oAuth2User.getName();
    String loginType = userRequest
        .getClientRegistration()
        .getRegistrationId()
        .toUpperCase(Locale.getDefault());


    String email;
    String nickname;
    String profilePath;

    switch (loginType) {
      case "KAKAO" -> {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, String> properties = (Map<String, String>) attributes.get("properties");
        nickname = properties.get("nickname");
        profilePath = properties.get("profile_image");
        Map<String, String> kakaoAccount = (Map<String, String>) attributes.get("kakao_account");
        email = kakaoAccount.get("email");
      }

      case "GOOGLE" -> {
        email = oAuth2User.getAttribute("email");
        nickname = oAuth2User.getAttribute("name");
        profilePath = oAuth2User.getAttribute("picture");
      }

      case "NAVER" -> {
        Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
        email = (String) response.get("email");
        nickname = (String) response.get("nickname");
        profilePath = (String) response.get("profile_image");
        oauthId = (String) response.get("id");
      }

      case "GITHUB" -> {
        email = oAuth2User.getAttribute("email");
        if (email == null || email.isEmpty()) {
          email = _getGithubPrivateEmail(userRequest.getAccessToken());
          if (email == null) {
            email = oAuth2User.getAttribute("login") + "@github.com";
          }
        }

        nickname = oAuth2User.getAttribute("login");
        profilePath = oAuth2User.getAttribute("avatar_url");
      }

      default -> throw new ServiceException(ErrorCode.INVALID_LOGIN_TYPE);
    }

    String providerId = loginType + "__" + oauthId;   // ex) KAKAO__1234567890

    Long originMemberId = socialConnectService.getOrigin();
    boolean isConnection = originMemberId != null;

    if (isConnection) {
      Member loginUser = memberService.findById(originMemberId)
          .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));

      authService.connectSocial(loginUser, loginType, providerId, email);
      socialConnectService.removeOrigin();

      return new SecurityUser(
          loginUser.getId(),
          loginUser.getEmail(),
          loginUser.getPassword(),
          loginUser.getNickname(),
          loginUser.getProfilePath(),
          loginUser.getAuthorities()
      );

    } else {
      Member member = authService.handleSocialLogin(
          email, nickname, loginType, profilePath, providerId
      );

      return new SecurityUser(
          member.getId(),
          member.getEmail(),
          member.getPassword(),
          member.getNickname(),
          member.getProfilePath(),
          member.getAuthorities()
      );
    }
  }


  private String _getGithubPrivateEmail(OAuth2AccessToken accessToken) {
    try {
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(accessToken.getTokenValue());

      HttpEntity<?> entity = new HttpEntity<>(headers);
      ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
          "https://api.github.com/user/emails",
          HttpMethod.GET,
          entity,
          new ParameterizedTypeReference<List<Map<String, Object>>>() {}
      );

      return response.getBody().stream()
          .filter(emailObj -> (Boolean) emailObj.get("primary") && (Boolean) emailObj.get("verified"))
          .map(emailObj -> (String) emailObj.get("email"))
          .findFirst()
          .orElse(null);

    } catch (Exception e) {
      return null;
    }
  }
}
