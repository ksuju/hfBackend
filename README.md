# 숨은 사람 친구 - Backend
### 개발 기간 : 2025년 01월 16일(목) ~ 02월 14일(금)
![팀ppt](https://github.com/user-attachments/assets/1d0633db-d088-4153-9d9c-264a584a5eb3)


## 💡프로젝트 개요
- 프로젝트명
  - 숨은 사람 친구
- 목적
  - 축제, 공연 정보 제공 및 동행자 모임 생성
- 페르소나
  - 학생 김토끼 : 좋아하는 아이돌 콘서트를 가고 싶은데, 혼자 가기에는 애매하네...
같이 갈 사람 어디 없나?
  - 직장인 최거북이 : 다음 주 데이트때 뭘 하지? 우리 동네 축제나 공연 정보를 한눈에 찾아볼 수 있을까?
 

## 🙂 역할 분배

![역할 분배](https://github.com/user-attachments/assets/460fa7c7-f700-483c-8204-e7a6482bc9f2)


## ⚙️ 개발 환경
> BE Repo : [백엔드 레포 바로가기](https://github.com/BackEndSchoolPlus3th/hfBackend)   
> FE Repo : [프론트 레포 바로가기](https://github.com/BackEndSchoolPlus3th/hfFrontend)

- FE : Vite(React + Typescript), Axios
- BE : SpringBoot, JPA, WebSocket 
- 버전 및 이슈관리 : Github
- 협업 툴 : Discord, Notion, Jira
- 서비스 배포 환경   
   - FE: Vercel   
   - BE: Terraform, GitAction, AWS
   - Elasticsearch : Ncloud, Terraform
- 디자인 : Figma, Cursor


## 📂 프로젝트 구조

<details>
<summary>프로젝트 구조</summary>

```
hfBackend
├─ .DS_Store
├─ Dockerfile
├─ GITCONVENTION.md
├─ README.md
├─ db_dev.trace.db
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
├─ infraScript
│  └─ zero_downtime_deploy.py
├─ readme-photo
└─ src
   ├─ .DS_Store
   ├─ main
   │  ├─ .DS_Store
   │  ├─ generated
   │  │  └─ com
   │  │     └─ ll
   │  │        └─ hfback
   │  │           ├─ domain
   │  │           │  ├─ board
   │  │           │  │  ├─ comment
   │  │           │  │  │  └─ entity
   │  │           │  │  │     └─ QBoardComment.java
   │  │           │  │  └─ notice
   │  │           │  │     └─ entity
   │  │           │  │        └─ QBoard.java
   │  │           │  │           ...
   │  ├─ java
   │  │  └─ com
   │  │     └─ ll
   │  │        └─ hfback
   │  │           ├─ HfbackApplication.java
   │  │           ├─ domain
   │  │           │  ├─ board
   │  │           │  │  ├─ comment
   │  │           │  │  │  ├─ controller
   │  │           │  │  │  │  └─ ApiV1BoardCommentController.java
   │  │           │  │  │  ├─ dto
   │  │           │  │  │  │  └─ BoardCommentDto.java
   │  │           │  │  │  ├─ entity
   │  │           │  │  │  │  └─ BoardComment.java
   │  │           │  │  │  ├─ repository
   │  │           │  │  │  │  └─ BoardCommentRepository.java
   │  │           │  │  │  └─ service
   │  │           │  │  │     └─ BoardCommentService.java
   │  │           │  │  └─ notice
   │  │           │  │     ├─ controller
   │  │           │  │     │  └─ ApiV1BoardController.java
   │  │           │  │     ├─ dto
   │  │           │  │     │  └─ BoardDto.java
   │  │           │  │     ├─ entity
   │  │           │  │     │  └─ Board.java
   │  │           │  │     ├─ repository
   │  │           │  │     │  └─ BoardRepository.java
   │  │           │  │     └─ service
   │  │           │  │        └─ BoardService.java
   │  │           │  ├─ festival
   │  │           │  │  ├─ api
   │  │           │  │  │  ├─ scheduler
   │  │           │  │  │  │  ├─ FetchApisScheduler.java
   │  │           │  │  │  │  └─ FetchKopisScheduler.java
   │  │           │  │  │  ├─ service
   │  │           │  │  │  │  └─ ApiService.java
   │  │           │  │  │  └─ serviceImpl
   │  │           │  │  │     └─ ApiServiceImpl.java
   │  │           │  │  ├─ comment
   │  │           │  │  │  ├─ controller
   │  │           │  │  │  │  └─ ApiV1CommentController.java
   │  │           │  │  │  ├─ dto
   │  │           │  │  │  │  └─ CommentDto.java
   │  │           │  │  │  ├─ entity
   │  │           │  │  │  │  └─ Comment.java
   │  │           │  │  │  ├─ form
   │  │           │  │  │  │  ├─ AddCommentForm.java
   │  │           │  │  │  │  └─ UpdateCommentForm.java
   │  │           │  │  │  ├─ repository
   │  │           │  │  │  │  └─ CommentRepository.java
   │  │           │  │  │  ├─ service
   │  │           │  │  │  │  └─ CommentService.java
   │  │           │  │  │  └─ serviceImpl
   │  │           │  │  │     └─ CommentServiceImpl.java
   │  │           │  │  ├─ post
   │  │           │  │  │  ├─ controller
   │  │           │  │  │  │  └─ ApiV1PostController.java
   │  │           │  │  │  ├─ dto
   │  │           │  │  │  │  ├─ DetailPostDto.java
   │  │           │  │  │  │  └─ PostDto.java
   │  │           │  │  │  ├─ entity
   │  │           │  │  │  │  └─ Post.java
   │  │           │  │  │  ├─ repository
   │  │           │  │  │  │  └─ PostRepository.java
   │  │           │  │  │  ├─ service
   │  │           │  │  │  │  └─ PostService.java
   │  │           │  │  │  └─ serviceImpl
   │  │           │  │  │     └─ PostServiceImpl.java
   │  │           │  │  └─ search
   │  │           │  │     ├─ controller
   │  │           │  │     │  └─ ApiV1PostDocController.java
   │  │           │  │     ├─ document
   │  │           │  │     │  └─ MainPostDoc.java
   │  │           │  │     ├─ dto
   │  │           │  │     │  └─ ResponseFestivalSearch.java
   │  │           │  │     ├─ repository
   │  │           │  │     │  └─ PostDocRepository.java
   │  │           │  │     └─ service
   │  │           │  │        └─ PostDocService.java
   │  │           │  ├─ group
   │  │           │  │  ├─ chat
   │  │           │  │  │  ├─ chatMessageK6Test
   │  │           │  │  │  │  ├─ controller
   │  │           │  │  │  │  │  └─ ApiV1ChatMessageK6TestController.java
   │  │           │  │  │  │  └─ service
   │  │           │  │  │  │     └─ ChatMessageK6TestService.java
   │  │           │  │  │  ├─ controller
   │  │           │  │  │  │  ├─ ApiV1ChatFileController.java
   │  │           │  │  │  │  └─ ApiV1ChatMessageController.java
   │  │           │  │  │  ├─ entity
   │  │           │  │  │  │  ├─ ChatMessage.java
   │  │           │  │  │  │  └─ ChatRoomUser.java
   │  │           │  │  │  ├─ enums
   │  │           │  │  │  │  └─ ChatRoomUserStatus.java
   │  │           │  │  │  ├─ repository
   │  │           │  │  │  │  ├─ ChatMessageRepository.java
   │  │           │  │  │  │  └─ ChatRoomUserRepository.java
   │  │           │  │  │  ├─ request
   │  │           │  │  │  │  ├─ MessageReadStatusRequest.java
   │  │           │  │  │  │  ├─ MessageSearchKeywordsRequest.java
   │  │           │  │  │  │  └─ RequestMessage.java
   │  │           │  │  │  ├─ response
   │  │           │  │  │  │  ├─ ResponseMemberStatus.java
   │  │           │  │  │  │  ├─ ResponseMessage.java
   │  │           │  │  │  │  └─ ResponseMessageCount.java
   │  │           │  │  │  ├─ search
   │  │           │  │  │  │  ├─ controller
   │  │           │  │  │  │  │  └─ ApiV1ChatMessageDocController.java
   │  │           │  │  │  │  ├─ document
   │  │           │  │  │  │  │  └─ ChatMessageDoc.java
   │  │           │  │  │  │  ├─ repository
   │  │           │  │  │  │  │  └─ ChatMessageDocRepository.java
   │  │           │  │  │  │  ├─ service
   │  │           │  │  │  │  │  └─ ChatMessageDocService.java
   │  │           │  │  │  │  └─ serviceImpl
   │  │           │  │  │  │     └─ ChatMessageDocServiceImpl.java
   │  │           │  │  │  ├─ service
   │  │           │  │  │  │  ├─ ChatMessageService.java
   │  │           │  │  │  │  └─ ChatS3Service.java
   │  │           │  │  │  └─ serviceImpl
   │  │           │  │  │     ├─ ChatMessageServiceImpl.java
   │  │           │  │  │     └─ ChatS3ServiceImpl.java
   │  │           │  │  └─ chatRoom
   │  │           │  │     ├─ controller
   │  │           │  │     │  └─ ApiV1ChatRoomController.java
   │  │           │  │     ├─ converter
   │  │           │  │     │  ├─ StringDoubleListConverter.java
   │  │           │  │     │  └─ StringListConverter.java
   │  │           │  │     ├─ dto
   │  │           │  │     │  ├─ ChatRoomDto.java
   │  │           │  │     │  └─ DetailChatRoomDto.java
   │  │           │  │     ├─ entity
   │  │           │  │     │  └─ ChatRoom.java
   │  │           │  │     ├─ form
   │  │           │  │     │  ├─ CreateChatRoomForm.java
   │  │           │  │     │  └─ UpdateChatRoomForm.java
   │  │           │  │     ├─ repository
   │  │           │  │     │  └─ ChatRoomRepository.java
   │  │           │  │     ├─ service
   │  │           │  │     │  └─ ChatRoomService.java
   │  │           │  │     └─ serviceImpl
   │  │           │  │        └─ ChatRoomServiceImpl.java
   │  │           │  ├─ home
   │  │           │  │  └─ main
   │  │           │  │     └─ controller
   │  │           │  │        └─ MainController.java
   │  │           │  └─ member
   │  │           │     ├─ alert
   │  │           │     │  ├─ controller
   │  │           │     │  │  └─ ApiV1AlertController.java
   │  │           │     │  ├─ dto
   │  │           │     │  │  ├─ AlertRequest.java
   │  │           │     │  │  └─ AlertResponse.java
   │  │           │     │  ├─ entity
   │  │           │     │  │  └─ Alert.java
   │  │           │     │  ├─ enums
   │  │           │     │  │  ├─ AlertType.java
   │  │           │     │  │  ├─ BoardAlertType.java
   │  │           │     │     ├─ ...
   │  │           │     │  ├─ events
   │  │           │     │  │  ├─ AlertEvent.java
   │  │           │     │  │  ├─ AlertEventHandler.java
   │  │           │     │     ├─ ...
   │  │           │     │  ├─ repository
   │  │           │     │  │  └─ AlertRepository.java
   │  │           │     │  └─ service
   │  │           │     │     ├─ AlertEventPublisher.java
   │  │           │     │     └─ AlertService.java
   │  │           │     ├─ auth
   │  │           │     │  ├─ config
   │  │           │     │  │  ├─ AsyncConfig.java
   │  │           │     │  │  ├─ EmailConfig.java
   │  │           │     │  │  └─ SmsConfig.java
   │  │           │     │  ├─ controller
   │  │           │     │  │  └─ ApiV1AuthController.java
   │  │           │     │  ├─ dto
   │  │           │     │  │  ├─ EmailInfo.java
   │  │           │     │  │  ├─ EmailRequest.java
   │  │           │     │  │  ├─ EmailVerifyRequest.java
   │  │           │     │     ├─ ...
   │  │           │     │  ├─ entity
   │  │           │     │  │  └─ SocialAccount.java
   │  │           │     │  ├─ repository
   │  │           │     │  │  └─ SocialAccountRepository.java
   │  │           │     │  └─ service
   │  │           │     │     ├─ AuthService.java
   │  │           │     │     ├─ AuthTokenService.java
   │  │           │     │     ├─ ...
   │  │           │     ├─ member
   │  │           │     │  ├─ controller
   │  │           │     │  │  ├─ ApiV1FriendController.java
   │  │           │     │  │  └─ ApiV1MemberController.java
   │  │           │     │  ├─ dto
   │  │           │     │  │  ├─ AddPasswordRequest.java
   │  │           │     │  │  ├─ AddressResponse.java
   │  │           │     │  │  ├─ ...
   │  │           │     │  ├─ entity
   │  │           │     │  │  ├─ Friend.java
   │  │           │     │  │  └─ Member.java
   │  │           │     │  ├─ repository
   │  │           │     │  │  ├─ FriendRepository.java
   │  │           │     │  │  └─ MemberRepository.java
   │  │           │     │  └─ service
   │  │           │     │     ├─ FriendService.java
   │  │           │     │     ├─ MemberService.java
   │  │           │     │     ├─ ...
   │  │           │     └─ report
   │  │           │        ├─ controller
   │  │           │        │  └─ ApiV1ReportController.java
   │  │           │        ├─ dto
   │  │           │        │  ├─ ReportListResponse.java
   │  │           │        │  ├─ ReportRequest.java
   │  │           │        │  ├─ ...
   │  │           │        ├─ entity
   │  │           │        │  └─ Report.java
   │  │           │        ├─ repository
   │  │           │        │  └─ ReportRepository.java
   │  │           │        └─ service
   │  │           │           └─ ReportService.java
   │  │           ├─ global
   │  │           │  ├─ app
   │  │           │  │  └─ AppConfig.java
   │  │           │  ├─ aspect
   │  │           │  │  └─ ResponseAspect.java
   │  │           │  ├─ chatS3
   │  │           │  │  └─ ChatS3Config.java
   │  │           │  ├─ exceptions
   │  │           │  │  ├─ ErrorCode.java
   │  │           │  │  ├─ GlobalExceptionHandler.java
   │  │           │  │  └─ ServiceException.java
   │  │           │  ├─ initData
   │  │           │  │  └─ NotProd.java
   │  │           │  ├─ jpa
   │  │           │  │  └─ BaseEntity.java
   │  │           │  ├─ oauth2
   │  │           │  │  ├─ CustomAuthorizationRequestResolver.java
   │  │           │  │  ├─ ...
   │  │           │  ├─ redis
   │  │           │  │  └─ RedisConfig.java
   │  │           │  ├─ rq
   │  │           │  │  └─ Rq.java
   │  │           │  ├─ rsData
   │  │           │  │  └─ RsData.java
   │  │           │  ├─ security
   │  │           │  │  ├─ ApiSecurityConfig.java
   │  │           │  │  ├─ CustomAuthenticationFilter.java
   │  │           │  │  ├─ ...
   │  │           │  ├─ storage
   │  │           │  │  ├─ FileStorageHandler.java
   │  │           │  │  ├─ FileUploadRequest.java
   │  │           │  │  ├─ ...
   │  │           │  ├─ web
   │  │           │  │  └─ WebConfig.java
   │  │           │  ├─ webMvc
   │  │           │  │  ├─ CustomWebMvcConfig.java
   │  │           │  │  ├─ LoginUser.java
   │  │           │  │  └─ LoginUserArgumentResolver.java
   │  │           │  └─ websocket
   │  │           │     ├─ ChatMessageHandler.java
   │  │           │     └─ WebSocketConfig.java
   │  │           └─ standard
   │  │              ├─ base
   │  │              │  └─ Empty.java
   │  │              └─ util
   │  │                 └─ Ut.java
   │  └─ resources
   │     ├─ application-dev.yml
   │     ├─ application-prod.yml
   │     ├─ ...
   │     ├─ elasticsearch
   │     │  ├─ mappings.json
   │     │  └─ settings.json
   │     ├─ static
   │     └─ templates
   └─ test
   
```
</details>


## 📋 ERD
![erd](https://github.com/user-attachments/assets/a93e37f1-84aa-4c1c-abfe-849775c61b7d)


## 📄 API 명세서
![명세](https://github.com/user-attachments/assets/bfec13ae-199b-4880-9e1d-5edd58eeaaa3)


## 📄 GitConvention
GitConvention에 시간을 너무 쏟지 않도록 모두에게 익숙한 깃컨벤션 채택   
```
main
├─ dev
│  └─ 개인 브랜치
```


## 🔧 기술 스택
![기술스택](https://github.com/user-attachments/assets/6f3419e2-5620-443e-b6fd-312e2c783874)
### FE
React + TypeScript
   - 프론트 서버를 분리하면서 힘을 최대한 덜 들이기 위해 가장 유명하고 익숙한 **리액트**를 선정.
   - 자바스크립트의 변수 데이터 타입이 동적이기 때문에 추후 찾지 못할 타입 에러를 방지해 **타입 스크립트** 추가
   - 간편한 API 요청을 위해 **axios** 사용

### BE
SpringBoot + JPA + QueryDSL
   - 빠른 프로젝트 시작을 위해 WAS가 내장되어있는 **SpringBoot** 선정
   - 데이터 매핑 자동화를 위한 **JPA** 도입 및 **QueryDSL** 사용

WebSocket STOMP
   - 채팅 기능 구현을 위한 **WebSocket STOMP** 라이브러리 사용

JWT
   - 회원 정보 토큰 인증, 인가 사용을 위한 **JWT** 채택

### DBMS
Mysql, Redis
   - 짧은 개발 기간을 고려하여 모두에게 익숙한 **Mysql** 채택
   - 이메일, 휴대폰 인증 여부 확인을 위한 **Redis** 채택

### Elasticsearch
   - 축제, 채팅 데이터의 빠른 검색을 위한 **Elasticsearch** 도입


## 🔧 아키텍쳐
![아키텍처](https://github.com/user-attachments/assets/f18aae8b-605b-43a2-9f55-8b80abe945fa)
![cicd파이프라인](https://github.com/user-attachments/assets/4b7a95f4-155d-4210-9936-e9e989c46019)


## 💁‍♂ 구현 기능 - 개인
### 실시간 채팅 (WebSocket STOMP + REST API)
- WebSocket + STOMP를 사용한 실시간 채팅 송/수신
- 메시지를 읽지 않은 사람 수 표시 (카카오톡의 1)
- 채팅방 멤버 온라인/오프라인 상태 표시
- AWS S3를 사용한 이미지 업로드, 삭제 기능 구현 (Gif, Png, Jpg, Jpeg)
### 검색 기능 (V1: MySQL, V2: Elasticsearch)
- 채팅 (개인 구현)
	- V1 : 동적 쿼리를 사용한 채팅방 내 메시지 검색 기능 (QueryDSL)
	- V2 : Elasticsearch를 이용한 채팅방 내 메시지 검색 기능

- 축제/공연 (팀 구현)
	- V1 : 동적 쿼리를 사용한 축제/공연 키워드, 장르 검색 기능 (QueryDSL)
	- V2 : Elasticsearch를 이용한
   		- 축제/공연 키워드, 장르 검색 기능
   		- 날짜나 사용자 지역에 맞춘 메인 배너 기능


## 💁‍♀ 구현 기능 - 팀
### 알림
- 친구 추가, 채팅방, 공지사항 등 알림 기능

### 로그인 및 마이페이지 기능
- 자체 회원가입, 자체 로그인 기능
- 소셜 로그인 기능
- 마이페이지
   - 회원 프로필 수정 기능
   - 소셜 계정 연동 기능
   - 친구 추가 기능

### 축제 게시글 댓글&답글 및 모임(채팅방) 기능
- 메일 3시 스케줄러를 통한 축제/공연 API 정보 파싱
- 축제/공연 게시글 및 댓글과 답글 기능
- 축제/공연 게시글에 모임(채팅방) 생성 기능
- 모임(채팅방) 관리 기능
   - 방장 권한 위임
   - 참여 수락 및 참여자 강퇴 기능
 
### 관리자 페이지 및 공지사항 기능
- 관리자 권한을 가진 사용자만 진입할 수 있는 관리자 페이지
   - 회원 조회 기능
   - 모임 조회 및 삭제 기능
   - 공지사항 작성 기능
- 관리자가 작성한 공지사항 조회 및 댓글 작성 기능


## 🔧 기술구현(팀)
![Video Label](http://img.youtube.com/vi/A1Qw0O46Xkw/0.jpg)<br>(https://youtu.be/A1Qw0O46Xkw)


## 🔧 기술구현(개인) - 단체 채팅
![기술구현](https://github.com/user-attachments/assets/656f2d53-e2a4-43c2-841a-97ddefc70d33)


## 🔧 K6 부하테스트 결과
1. 테스트 환경
	- 동시 사용자 수를 단계적으로 증가 100 > 300 > 500
	- 각 단계에서 일정 시간 유지 (3분)

2. 테스트 데이터 크기
	- 첫 번째 테스트 : 채팅 데이터 100개
	- 두 번째 테스트 : 채팅 데이터 10,000개


#### 결과 분석

1. MySQL 데이터 크기가 증가 했을 때
	- 평균 응답 시간 : ** 12ms > 1s **로 크게 증가
	- 95분위 (=p95) 응답 시간 :  ** 16ms > 4s **로 급격히 느려짐

> MySQL은 대량 데이터를 처리할 때 성능 저하가 심각,
로컬 테스트 환경에서 부하를 견디지 못하고 병목 현상이 일어나서 응답시간의 증가폭이 더욱 커짐

2. 엘라스틱서치 데이터 크기가 증가 했을 때
	- 평균 응답 시간: ** 11ms → 22ms **로 약간 증가했지만 여전히 빠른 성능을 유지
	- 95분위 응답 시간: ** 15ms → 62ms **로 증가했으나, 높은 동시 사용자 부하에서도 안정적인 성능을 보임

> 엘라스틱서치는 대량 데이터에서도 효율적으로 작동하는 모습을 보임


#### 종합 결론

1. 엘라스틱 서치
	- 데이터 크기와 동시 사용자 수가 증가해도 비교적 안정적인 성능을 유지
	- 채팅 애플리케이션에서 실시간 검색 및 조회 작업에 적합

2. MySQL
	- 소규모 데이터(100개)에서는 유사한 성능을 보였으나, 대규모 데이터(10,000개)에서는 급격한 성능 저하 발생함
	- 관계형 데이터베이스의 특성상 대량 데이터를 처리하기 위해서는 인덱싱 최적화와 쿼리 개선이 필요

![1번k6](https://github.com/user-attachments/assets/9fdc33bc-09d1-4272-80bb-e1ffb65bb1ee)
![2번k6](https://github.com/user-attachments/assets/2f17478c-fd4c-405c-92b2-e7dc4854ab23)



##  ✒️ 메모
- [웹소켓 실습 - 웹소켓을 이용한 채팅 구현](https://ksuju.tistory.com/140)<br>
- [k6 단계별 부하 테스트 (채팅 메시지 검색기능, Elasticsearch)](https://ksuju.tistory.com/153)


## ⁉️ 트러블 슈팅
- [영속성 컨텍스트 문제를 DTO로 해결](https://ksuju.tistory.com/146)
- [QueryDSL 에러 - Execution failed for task ':compileQuerydsl'.> Annotation processor '' not found](https://ksuju.tistory.com/147)
- [영속성 컨텍스트 엔티티 참조 에러](https://ksuju.tistory.com/148)
- [채팅방에 처음 입장시 읽지 않은 메시지가 정상적으로 출력되지 않았던 문제](https://ksuju.tistory.com/150)
- [Logstash와 MySQL 연결 에러](https://ksuju.tistory.com/151)
- [VmmemWSL 메모리 과잉 할당 문제](https://ksuju.tistory.com/154)


## 🎞️ 시연 영상

#### 멤버 온라인/오프라인 표시, 메시지를 읽지 않은 사람의 수 카운트
- 채팅방에 참여 중인 멤버의 온라인/오프라인 표시가 실시간으로 변경
- 현재 온라인 상태의 사용자 수에 맞춰서 메시지를 읽지 않은 사람 수 카운트가 표시
![시연1cut](https://github.com/user-attachments/assets/222c69c3-7cc1-4fc0-802a-5d9c04a73165)
[온/오프라인 표시 소스코드](https://github.com/ksuju/hfBackend/blob/fe11ef94d7a2b0704c6eab166ff1ff9a78e3c206/src/main/java/com/ll/hfback/domain/group/chat/serviceImpl/ChatMessageServiceImpl.java#L295-L388)</br>
[카운트 소스코드](https://github.com/ksuju/hfBackend/blob/fe11ef94d7a2b0704c6eab166ff1ff9a78e3c206/src/main/java/com/ll/hfback/domain/group/chat/serviceImpl/ChatMessageServiceImpl.java#L135-L163)</br>
[카운트 JPQL](https://github.com/ksuju/hfBackend/blob/73813175ba3fcea12eadcac7a4c9385f18f81a56/src/main/java/com/ll/hfback/domain/group/chat/repository/ChatMessageRepository.java#L28-L37)

#### 실시간 채팅 송/수신
- 실시간 단체 채팅 기능 구현
- 10개의 메시지로 페이징
- 메시지 수신 시, 스크롤 위치에 따라 '새 메시지' 버튼 표시
![시연2cut](https://github.com/user-attachments/assets/dcc2e18e-d507-42ed-8602-db4595540e30)
[실시간 채팅 송/수신 소스코드](https://github.com/ksuju/hfBackend/blob/fe11ef94d7a2b0704c6eab166ff1ff9a78e3c206/src/main/java/com/ll/hfback/domain/group/chat/serviceImpl/ChatMessageServiceImpl.java#L59C1-L133C6)

#### 실시간 이미지 송/수신, 삭제
- 실시간 이미지 송신 수신 가능
- 이미지 삭제시 채팅방에 실시간으로 반영됨
![시연3cut](https://github.com/user-attachments/assets/a9cd9b40-3a22-4889-85b1-e47c6dad5238)
[이미지 송/수신, 삭제 소스코드](https://github.com/ksuju/hfBackend/blob/73813175ba3fcea12eadcac7a4c9385f18f81a56/src/main/java/com/ll/hfback/domain/group/chat/serviceImpl/ChatS3ServiceImpl.java#L35-L152)

#### 채팅 메시지 검색
- 키워드, 닉네임으로 채팅방 내에 있는 메시지 검색 가능
![시연4cut](https://github.com/user-attachments/assets/0123d93b-9a64-4ec2-beca-63a0ebab10fc)
[QueryDSL - 채팅 메시지 검색 소스코드](https://github.com/ksuju/hfBackend/blob/73813175ba3fcea12eadcac7a4c9385f18f81a56/src/main/java/com/ll/hfback/domain/group/chat/serviceImpl/ChatMessageServiceImpl.java#L180-L256)</br>
[Elasticsearch - 채팅 메시지 검색 소스코드](https://github.com/ksuju/hfBackend/blob/73813175ba3fcea12eadcac7a4c9385f18f81a56/src/main/java/com/ll/hfback/domain/group/chat/search/serviceImpl/ChatMessageDocServiceImpl.java#L30-L103)
