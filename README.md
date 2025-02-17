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
### 실시간 채팅 (WebSocket STOMP +팀)
[발표 영상 올릴예정]

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

#### 실시간 채팅 송/수신
- 실시간 단체 채팅 기능 구현
- 10개의 메시지로 페이징
- 메시지 수신 시, 스크롤 위치에 따라 '새 메시지' 버튼 표시
![시연2cut](https://github.com/user-attachments/assets/dcc2e18e-d507-42ed-8602-db4595540e30)

#### 실시간 이미지 송/수신, 삭제
- 실시간 이미지 송신 수신 가능
- 이미지 삭제시 채팅방에 실시간으로 반영됨
![시연3cut](https://github.com/user-attachments/assets/a9cd9b40-3a22-4889-85b1-e47c6dad5238)

#### 채팅 메시지 검색
- 키워드, 닉네임으로 채팅방 내에 있는 메시지 검색 가능
![시연4cut](https://github.com/user-attachments/assets/0123d93b-9a64-4ec2-beca-63a0ebab10fc)
