# login-chat
소셜로그인, 웹소켓 채팅서비스

## 목차

 * [개발환경](#개발환경)
 * [소셜로그인](#소셜로그인)
 * [웹소켓통신](#웹소켓통신)
 

## 개발환경

1. BE
   * kotlin 1.5
   * Spring boot 2.7.5
   * gradle
   * webflux 2.7.5
   * websocket 2.7.5 (stomp프로토콜)

2. DOCS
   * javadoc


## 소셜로그인

1. 동작방식
   * 기본적으로 카카오,네이버로그인의 동작방식은 동일하다.
   * Third-party에서 제공하는 로그인 완료시 "인가코드"를 획득받고, redirect_uri로 redirect 된다.
   * redirect_uri에서 인가코드를 통해 access_token을 발급받는다.
2. 프로젝트 생성
   * 카카오,네이버 로그인을 위해 "내 어플리케이션" 설정이 필수이다.
   * "내 어플리케이션"을 등록함으로써 clientId, clientSecret 키값을 획득받아 활용한다.
   * [카카오](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api)
   * [네이버](https://developers.naver.com/docs/login/api/api.md)


## 웹소켓통신

1. STOMP 프로토콜
   * pub/sub 구조
   * Session정보 관리하지 않아도 자동으로 관리해줌
2. pub
   * `@MessageMapping` stomp 메세지 수신
   * 수신한 메세지를 topic에 publish
3. sub
   * STOMP 프로토콜을 통해 topic을 구독한 Client
   * 따로 구현하지 않고 크롬 플러그인 사용