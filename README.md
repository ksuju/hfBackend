# 숨은 사람 친구 - Backend
개발 기간 : 2025년 01월 16일(목) ~ 02월 14일(금)


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


## 💁‍♂ 구현 기능
### 실시간 채팅 (WebSocket STOMP + REST API)
- WebSocket + STOMP를 사용한 실시간 채팅 송/수신
- 메시지를 읽지 않은 사람 수 표시 (카카오톡의 1)
- 채팅방 멤버 온라인/오프라인 상태 표시
- AWS S3를 사용한 이미지 업로드, 삭제 기능 구현 (Gif, Png, Jpg, Jpeg)
### 검색 기능 (V1: MySQL, V2: Elasticsearch)
- V1 : 동적 쿼리를 사용한 채팅방 내 메시지 검색 기능 (QueryDSL)
- V2 : Elasticsearch를 이용한 채팅방 내 메시지 검색 기능


## 📋 ERD

![erd](https://github.com/user-attachments/assets/a93e37f1-84aa-4c1c-abfe-849775c61b7d)


## 📄 API 명세서

![명세](https://github.com/user-attachments/assets/bfec13ae-199b-4880-9e1d-5edd58eeaaa3)


## 🔧 기술 스택

![기술스택](https://github.com/user-attachments/assets/6f3419e2-5620-443e-b6fd-312e2c783874)


## 🔧 아키텍쳐

![아키텍처](https://github.com/user-attachments/assets/f18aae8b-605b-43a2-9f55-8b80abe945fa)
![cicd파이프라인](https://github.com/user-attachments/assets/4b7a95f4-155d-4210-9936-e9e989c46019)


## 🔧 기술구현(단체)
[발표 영상 올릴예정]

## 🔧 기술구현(개인) - 단체 채팅

![기술구현](https://github.com/user-attachments/assets/656f2d53-e2a4-43c2-841a-97ddefc70d33)


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
![시연1cut](https://github.com/user-attachments/assets/222c69c3-7cc1-4fc0-802a-5d9c04a73165)

#### 실시간 채팅 송/수신
![시연2cut](https://github.com/user-attachments/assets/dcc2e18e-d507-42ed-8602-db4595540e30)

#### 실시간 이미지 송/수신, 삭제
![시연3cut](https://github.com/user-attachments/assets/a9cd9b40-3a22-4889-85b1-e47c6dad5238)

#### 채팅 메시지 검색
![시연4cut](https://github.com/user-attachments/assets/0123d93b-9a64-4ec2-beca-63a0ebab10fc)





