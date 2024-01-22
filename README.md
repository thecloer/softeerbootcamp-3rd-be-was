# 현대자동차 소프티어 부트캠프 3기 BE WAS 과제

## 프로젝트 정보

소프티어 부트캠프 3기 BE 과제로 진행한 프로젝트입니다.  
과제 레포지토리: https://github.com/softeerbootcamp-3rd/be-was  
개인 레포지토리: https://github.com/thecloer/softeerbootcamp-3rd-be-was

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was
를 참고하여 작성되었습니다.

## 1단계 - index.html 응답

### 학습 키워드 & 학습 목표

> HTTP, java, HTTP GET, thread, concurrent

- HTTP를 학습하고 학습 지식을 기반으로 웹 서버를 구현한다.
- Java 멀티스레드 프로그래밍을 경험한다.
- 유지보수에 좋은 구조에 대해 고민하고 코드를 개선해 본다.

### 기능 요구사항

- ✅ http://localhost:8080/index.html 로 접속했을 때 `src/main/resources/templates` 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
- ✅ 서버로 들어오는 HTTP Request의 내용을 읽고 적절하게 파싱해서 로거(log.debug)를 이용해 출력한다.

### 프로그래밍 요구사항

- ✅ 프로젝트 분석
    - 단순히 요구사항 구현이 목표가 아니라 프로젝트의 동작 원리에 대해 파악한다.
- ✅ 구조 변경
    - 자바 스레드 모델에 대해 학습한다. 버전별로 어떤 변경점이 있었는지와 향후 지향점에 대해서도 학습해 본다.
    - 자바 Concurrent 패키지에 대해 학습한다.
    - 기존의 프로젝트는 Java Thread 기반으로 작성되어 있다. 이를 Concurrent 패키지를 사용하도록 변경한다.
- ✅ OOP와 클린코딩
    - 주어진 소스코드를 기반으로 기능요구사항을 만족하는 코드를 작성한다.
    - 유지보수에 좋은 구조에 대해 고민하고 코드를 개선해 본다.
    - 웹 리소스 파일은 제공된 파일을 수정해서 사용한다. (직접 작성해도 무방하다.)

### 구현 내용

- Http request 로깅
    - "[ {protocol} {method} ] {path?query#fragment}"
- 정적 파일 응답
    - 요청 리소스의 확장자에 따라 응답 헤더의 `Content-Type`설정, 리소스 경로 분기 응답
    - `.html` -> `resources/templates`
    - 이외 -> `resources/static`
- `Concurrent` 패키지가 제공하는 스레드 풀을 이용해 요청 처리
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

- ✅ HTTP GET으로 회원가입
    - "회원가입" 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동, 회원가입 폼을 표시한다.
    - 이 폼을 통해서 회원가입을 할 수 있다.

### 프로그래밍 요구사항

- 회원가입 폼에서 가입 버튼을 클릭하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
  ```
  /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
  ```
- ✅ HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.
- ✅ 유지보수가 편한 코드가 되도록 코드품질을 개선해 본다.
- ✅ `Junit`을 활용한 단위 테스트를 적용해 본다.

### 구현 내용

- "회원가입" 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동, 회원가입 폼을 표시
- 폼을 통한 회원가입
    - 회원가입 성공시 `/user/profile.html?userId={userId}`로 리다이렉트
    - 회원가입 실패시 `/user/form.html?message={errorMessage}`로 리다이렉트
        - step 3 완료 이후 프론트에서 메세지 표시 구현할 예정
- 잘못된 경로 접근 시 404 페이지 리다이렉트
- `Junit`을 활용한 단위 테스트 적용
    - `UserControllerTest`: 회원가입 요청을 받고 응답하는 테스트
    - `UserServiceTest`: DB에 회원 저장, 입력 형식 예외 테스트
    - 테스트의 편의를 위해 DI 도입
        - DB static 변수에서 `ConcurrentHashMap`으로 변경
        - `BeanContainer`에서 컨트롤러와 서비스 객체를 관리하도록 변경

### 고민 사항

- `/` 접속 시 `/index.html`을 보여주는 기능을 어디에 구현해야할까?
    - `/`를 `/index.html`로 매핑하는 것은 루트 경로가 비어있을 경우 표시할 기본 경로 설정이라 생각
    - 기본 경로 설정은 라우터에서 필터링되지 않은 경로를 처리하는 `ResourceController`에 구현
- `ApplicationContainer`와 `UserService`가 꼭 필요할까?
    - `ApplicationContainer`
        - static 변수로 생성된 DB의 경우 테스트마다 DB 초기화 필요
        - 테스트를 위해 서비스에 불필요한 DB 초기화 메서드를 구현해야함
        - DB를 인스턴스 + `CuncurrentHashMap`으로 변경하여 테스트마다 DB 인스턴스를 생성해 주입
        - DB 인스턴스를 전역에서 관리하기 위해 `ApplicationContainer` 생성
        - 컨트롤러, 서비스 또한 `ApplicationContainer`에서 관리하여 후에 객체 생성 방법이 변경되어도 `ApplicationContainer`에서만 수정하면 되도록 객체의 생성과 소비를
          분리
    - `UserService`
        - 테스트 코드 작성의 편의를 위해 도입
        - 컨트롤러가 많아지고 많은 컨트롤러에서 `User`를 다룬다면 중복 코드가 많아질거라 생각돼 컨트롤러에서 서비스로 분리
- 오류 응답 상태코드
    - 없는 경로 요청 시 not found 404 응답 vs 302 리다이렉트 /404.html 응답
    - 회원가입 실패 시 bad request 400 응답 vs 302 다시 회원가입 폼으로 메시지와 함께 리다이렉트
    - 사용자의 예상 가능한 잘못된 요청에 대해 서버가 사용자의 다음 행동을 지정하는 것이므로 리다이렉트로 구현

### 기타

#### HTTP GET 프로토콜

GET 메서드는 주로 데이터 요청에 사용되며 서버에 저장된 데이터에 영향을 주지 않는다.  
데이터 요청에 필요한 추가 정보는 URI의 쿼리 스트링으로 전달할 수 있다. (예: `https://example.com/products?category=books&page=1`)
자주 바뀌지 않는 데이터에 대한 GET 요청은 결과를 캐싱해 같은 요청이 재발생할 경우 빠르게 응답을 제공할 수 있다.

## 3단계 - 다양한 컨텐츠 타입 지원

### 학습 키워드 & 학습 목표

> HTTP GET, HTTP Response, MIME

- HTTP Response 에 대해 학습한다.
- MIME 타입에 대해 이해하고 이를 적용할 수 있다.

### 기능 요구사항

- 지금까지 구현한 소스 코드는 stylesheet 와 파비콘 등을 지원하지 못하고 있다. 다양한 컨텐츠 타입을 지원하도록 개선해 본다.
- 지원할 컨텐츠의 확장자:
    - html, css, js, ico, png, jpg

### 프로그래밍 요구사항

- ✅ 구현
    - 기능요구사항이 정상적으로 동작하도록 구현한다.
- ✅ 검증
    - 서버의 static 폴더에 있는 정적 컨텐츠들에 대한 요청이 브라우저에서 정상적으로 처리되는지 확인해 본다.

### 구현 내용

- 다양한 컨텐츠 타입을 지원
    - step 3를 보지못하고 step 1 정적 파일 응답에서 이미 구현
    - html, css, js, ico, png, svg, txt, eot, ttf, woff, woff2 지원
- step 2에서 구현했던 회원가입 실패 시 클라이언트로 내려준 메세지 프론트에서 표시
    - 회원가입 실패 시 메세지 JSON 형태로 응답
        - 프론트에서 JSON 형태로 응답을 받아 메세지 표시
    - 회원가입 성공 시 생성한 데이터를 받을 수 있는 URI를 응답 헤더에 포함해 응답
        - `Location: /user/profile.html?userId={userId}`
        - 프론트에서 응답받은 URI로 회원 정보 요청
        - [참고](https://www.rfc-editor.org/rfc/rfc9110#name-201-created)
- 유틸 클래스 테스트 코드 추가
- 클린 코드, OOP 리팩터링
    - 메서드 가능하면 10줄 이하
    - 한 메서드는 하나의 기능
    - else 사용 지양
    - Uri 관련기능 유틸 클래스로 분리

### 고민 사항

- step 2에서 고민했던 오류 응답 상태코드에 대한 고민
    - ~~없는 경로 요청 시 not found 404 응답 vs 302 리다이렉트 /404.html 응답~~
    - ~~회원가입 실패 시 bad request 400 응답 vs 302 다시 회원가입 폼으로 메시지와 함께 리다이렉트~~
    - ~~사용자의 예상 가능한 잘못된 요청에 대해 서버가 사용자의 다음 행동을 지정하는 것이므로 리다이렉트로 구현~~
    - 서버와 클라이언트의 역할을 분리하기 위해 서버는 요청에 대한 응답만 처리하도록 변경
    - 클라이언트가 응답에 따른 다음 행동을 결정하도록 변경
    - 회원가입 실패 시 400 bad request 상태코드와 메세지를 포함해 json 응답
    - 회원가입 성공 시 201 created 상태코드와 생성한 데이터를 받을 수 있는 URI를 응답 헤더에 포함해
      응답 ([참고](https://www.rfc-editor.org/rfc/rfc9110#name-201-created))
- step 2에서 고민했던 서비스 레이어에 대한 고민
    - ~~테스트 코드 작성의 편의를 위해 도입~~
    - ~~컨트롤러가 많아지고 많은 컨트롤러에서 `User`를 다룬다면 중복 코드가 많아질거라 생각돼 컨트롤러에서 서비스로 분리~~
    - `RequestHandler`와 컨트롤러의 역할을 명확하게 분리
        - `RequestHandler`는 요청 생성과 대한 응답을 전송
        - 컨트롤러는 요청에 대한 처리를 통해 응답 생성
        - 요청을 클라이언트로 전송하는 부분을 `RequestHandler`로 옮기니 컨트롤러의 테스트가 쉬워짐
        - 컨트롤러의 테스트가 어려워 생성했던 `UserService`는 도입했던 이유는 사라졌으므로 제거

## 4단계 - POST로 회원 가입

### 학습 키워드 & 학습 목표

> HTTP POST

- HTTP POST의 동작방식을 이해하고 이를 이용해 회원가입을 구현할 수 있다.
- HTTP redirection 기능을 이해하고 회원가입 후 페이지 이동에 적용한다.

### 기능 요구사항

- [ ] 회원가입을 GET에서 POST로 수정 후 정상 동작하도록 구현한다.
- ✅ 가입을 완료하면 /index.html 페이지로 이동한다.

### 프로그래밍 요구사항

- [ ] 불필요한 외부 의존성 제거
    - 자바 기본 패키지, Junit, AssertJ, Logger 외의 외부 패키지는 사용하지 않는다.
    - Lombok 은 사용하지 않는다.
- ✅ java.nio 에서 java.io 로 변환
    - 만약 java.nio를 사용하고 있었다면 java.io를 사용하도록 수정한다.
- [ ] POST로 수정
    - http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한다.
    - 나머지 회원가입 기능이 정상적으로 동작하도록 구현한다.
    - 가입 후 페이지 이동을 위해 HTTP redirection 기능을 구현한다.

### 구현 내용

- 정적 파일 읽는 부분 `java.nio`에서 `java.io`로 변경
  - [기타](#javanio와-javaio)에 정리

### 고민 사항

### 기타

#### `java.nio`와 `java.io`
`java.io`는 java 1.0 부터 지원되는 I/O 패키지로 전통적인 블로킹 I/O 모델을 사용한다. 
스트림 기반 I/O로 바이트 스트림(InputStream, OutputStream)과 문자 스트림(Reader, Writer)으로 나뉜다.
바이트 스트림의 경우 1바이트씩 읽고 쓰기 때문에 일반적으로 느린 성능을 보인다. 
이 경우 `BufferedInputStream`, `BufferedOutputStream`을 사용해 버퍼를 통한 성능을 개선할 수 있다.
java 1.0 부터 지원한 만큼 기초적이고 직관적인 API를 제공한다.  

`java.nio`는 java 1.4 부터 지원되는 I/O 패키지로 블로킹 I/O 모델에 더해 논블로킹 I/O 모델을 지원한다.
`java.nio`는 기본적으로 버퍼를 이용하며 채널 기반 I/O를 한다. 
[채널](https://docs.oracle.com/javase/8/docs/api/java/nio/channels/Channel.html)은 I/O 작업을 수행할 수 있는 엔티티(파일, 소켓)에 대한 연결을 나타낸다.
채널 기반 I/O와 스트림 기반 I/O의 큰 차이점은 채널은 양방성으로 읽기와 쓰기 모두 지원한다는 점이다.
채널 역시 기본적으로 블로킹 I/O로 동작하며 [configureBlocking(false)](https://docs.oracle.com/javase/8/docs/api/java/nio/channels/spi/AbstractSelectableChannel.html#configureBlocking-boolean-)와 같은 설정을 통해 논블로킹 모드로 전환할 수 있다.
이렇게 논블로킹 I/O로 동작하는 채널들은 [Selector](https://docs.oracle.com/javase/8/docs/api/java/nio/channels/package-summary.html)에 등록 되어 관리될 수 있다.
`Selector`를 이용하면 단일 스레드에서 여러 채널의 I/O 작업을 효율적으로 모니터링하고 관리할 수 있다.

과제에서 제작 중인 웹서버의 경우 `Thread per Request` 방식으로 동작하며 요청당 최대 하나의 정적 페이지를 마지막에 읽어온다.
따라서 `java.nio`를 사용해 non-blocking 비동기 I/O로 구현했을 때 얻을 수 있는 이점은 크지 않다.