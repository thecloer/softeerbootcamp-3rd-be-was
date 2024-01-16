# 현대자동차 소프티어 부트캠프 3기 BE WAS 과제

## 프로젝트 정보 

소프티어 부트캠프 3기 BE 과제로 진행한 프로젝트입니다.
과제 레포지토리: https://github.com/softeerbootcamp-3rd/be-was

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.

## 1단계 - index.html 응답

### 학습 키워드 & 학습 목표
> HTTP, java, HTTP GET, thread, concurrent
- HTTP를 학습하고 학습 지식을 기반으로 웹 서버를 구현한다.
- Java 멀티스레드 프로그래밍을 경험한다.
- 유지보수에 좋은 구조에 대해 고민하고 코드를 개선해 본다.

### 기능 요구사항
- ✅http://localhost:8080/index.html 로 접속했을 때 `src/main/resources/templates` 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
- ✅서버로 들어오는 HTTP Request의 내용을 읽고 적절하게 파싱해서 로거(log.debug)를 이용해 출력한다.

### 프로그래밍 요구사항 
- 프로젝트 분석
  - 단순히 요구사항 구현이 목표가 아니라 프로젝트의 동작 원리에 대해 파악한다.
- 구조 변경
  - 자바 스레드 모델에 대해 학습한다. 버전별로 어떤 변경점이 있었는지와 향후 지향점에 대해서도 학습해 본다.
  - 자바 Concurrent 패키지에 대해 학습한다.
  - 기존의 프로젝트는 Java Thread 기반으로 작성되어 있다. 이를 Concurrent 패키지를 사용하도록 변경한다.
- OOP와 클린코딩
  - 주어진 소스코드를 기반으로 기능요구사항을 만족하는 코드를 작성한다.
  - 유지보수에 좋은 구조에 대해 고민하고 코드를 개선해 본다.
  - 웹 리소스 파일은 제공된 파일을 수정해서 사용한다. (직접 작성해도 무방하다.)

### 구현 내용
- ✅ Http request 로깅
  - "[ {protocol} {method} ] {path?query#fragment}"
- ✅ 정적 파일 응답
    - 요청 리소스의 확장자에 따라 응답 헤더의 `Content-Type`설정, 리소스 경로 분기 응답
    - `.html` -> `resources/templates`
    - 이외 -> `resources/static`
- ✅ `Concurrent` 패키지가 제공하는 스레드 풀을 이용해 요청 처리
    - 기존 `Thread` 클래스로 요청마다 스레드를 생성, 제거 하며 요청을 처리하는 방식에서 요청을 큐에 넣고 스레드 풀에서 사용 가능한 스레드가 요청을 꺼내 처리하는 방식으로 변경

### 고민 사항
- 요청 url에서 확장자를 추출하는 메서드는 어떤 클래스에 구현해야할까?
    - ContentType enum에 private 메서드로 구현 vs 새로운 클래스 혹은 URI extends 한 클래스
    - -> 우선 아직은 사용되는 곳이 Content-Type 뿐이므로 ContentType enum에 private 메서드로 구현
    - 추후 필요시 유틸 클래스 혹은 URI 클래스를 상속받는 클래스를 만들어 빼낼 예정
- 요청 예외처리(오류 메세지 응답 혹은 오류 페이지 응답은 어떻게 할까?)
    - step-2에서 라우터를 구현 한뒤 예외에 따라 오류 메세지 혹은 오류 페이지 응답 해보자

### 기타
- MIME(Multipurpose Internet Mail Extensions) type
    - [MIME 타입](https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types)
    - [최신 미디어 타입 리스트](https://www.iana.org/assignments/media-types/media-types.xhtml)
- 자바 `Thread`, `Concurrent` 패키지, `Virtual Thread`에 대해 학습, 정리 
  - 블로그 글: [Thread와 Concurrent 패키지 그리고 Virtual Thread](https://cloer.tistory.com/266)

## 2단계 - GET으로 회원가입

### 학습 키워드 & 학습 목표
> HTTP GET
- HTTP GET 프로토콜을 이해한다. 
- HTTP GET에서 parameter를 전달하고 처리하는 방법을 학습한다. 
- HTTP 클라이언트에서 전달받은 값을 서버에서 처리하는 방법을 학습한다.

### 기능 요구사항
- [ ] HTTP GET으로 회원가입
  - "회원가입" 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동, 회원가입 폼을 표시한다. 
  - 이 폼을 통해서 회원가입을 할 수 있다.

### 프로그래밍 요구사항
- 회원가입 폼에서 가입 버튼을 클릭하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
  ```
  /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
  ```
- HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
- 유지보수가 편한 코드가 되도록 코드품질을 개선해 본다.
- `Junit`을 활용한 단위 테스트를 적용해 본다.

### 구현 내용
- [ ] "회원가입" 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동, 회원가입 폼을 표시
- [ ] 폼을 통한 회원가입
- [ ] `Junit`을 활용한 단위 테스트 적용

### 고민 사항

### 기타