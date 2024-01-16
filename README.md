# 현대자동차 소프티어 부트캠프 3기 BE WAS 과제

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 1단계 - index.html 응답

> HTTP, java, HTTP GET, thread, concurrent

- 자바 스레드 모델에 대해 학습한다. 버전별로 어떤 변경점이 있었는지와 향후 지향점에 대해서도 학습해 본다.
- 자바 Concurrent 패키지에 대해 학습한다.
- 기존의 프로젝트는 Java Thread 기반으로 작성되어 있다. 이를 Concurrent 패키지를 사용하도록 변경한다.

## 구현 내용
- ✅ Http request 로깅
  - "[ {protocol} {method} ] {path?query#fragment}"
- ✅ 정적 파일 응답
    - 요청 리소스의 확장자에 따라 응답 헤더의 `Content-Type`설정, 리소스 경로 분기 응답
    - `.html` -> `resources/templates`
    - 이외 -> `resources/static`
- ✅ `Concurrent` 패키지가 제공하는 스레드 풀을 이용해 요청 처리
    - 기존 `Thread` 클래스로 요청마다 스레드를 생성, 제거 하며 요청을 처리하는 방식에서 요청을 큐에 넣고 스레드 풀에서 사용 가능한 스레드가 요청을 꺼내 처리하는 방식으로 변경

## 고민 사항
- 요청 url에서 확장자를 추출하는 메서드는 어떤 클래스에 구현해야할까?
    - ContentType enum에 private 메서드로 구현 vs 새로운 클래스 혹은 URI extends 한 클래스
    - -> 우선 아직은 사용되는 곳이 Content-Type 뿐이므로 ContentType enum에 private 메서드로 구현
    - 추후 필요시 유틸 클래스 혹은 URI 클래스를 상속받는 클래스를 만들어 빼낼 예정
- 요청 예외처리(오류 메세지 응답 혹은 오류 페이지 응답은 어떻게 할까?)
    - step-2에서 라우터를 구현 한뒤 예외에 따라 오류 메세지 혹은 오류 페이지 응답 해보자

## 기타
- MIME(Multipurpose Internet Mail Extensions) type
    - [MIME 타입](https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types)
    - [최신 미디어 타입 리스트](https://www.iana.org/assignments/media-types/media-types.xhtml)
- 자바 `Thread`와 `Concurrent` 패키지 블로그 정리: [링크](https://cloer.tistory.com/266)
    - JDK 21에 추가된 Virtual Thread에 대한 내용 추가해보자