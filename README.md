# example-community

## 소개

* 스프링 + Thymeleaf + JPA 를 기반으로 진행한 게시판 예제입니다.
* 핵심 기능으로 게시글, 댓글, 좋아요 CRUD 와 페이징, 검색 기능을 포함합니다.

* https://hhj-community.herokuapp.com 에서 해당 서비스 이용이 가능합니다. 
* Heroku의 저비용 서비스(echo dyno)와 무료 제공 DB를 사용해 다소 느릴 수 있습니다. 특히 첫 접속 시 프로젝트가 sleep 모드에서 새로 구동되어 로딩이 1분 가량 걸릴 수 있습니다. (sleep : 30분간 요청이 없는 경우 프로젝트 서버가 내려감)

이 프로젝트는 검증(Validation) 기능이 빠져있습니다. 때문에 일반적이지 않은 데이터 형식으로 입력 시 오류가 발생할 수 있습니다.

개발 과정은 [블로그](https://velog.io/@gudwn357/series/community)에 작성했으니 참고하시길 바랍니다.

## 로컬 사용법 (윈도우 10, 인텔리제이 기준)
1. 프로젝트 다운
2. 인텔리제이 Open `build.gradle` (`Open as Project`)<br>
2-1. gradle을 통해 외부 라이브러리의 의존성이 추가되지 않았다면 우측의 gradle 패널을 펼처 직접 reload
3. 인텔리제이 터미널에 `./gradlew clean compileQuerydsl` 입력
4. application.yml 에서 spring.datasource 부분 주석 처리 및 jpa.hibernate.ddl-auto: create 로 수정 (메모리에서 프로젝트 구동, 데이터 저장 X)<br>
4-1. 만약 데이터를 DB에 저장하고 싶다면 `spring.datasource` 부분을 사용할 DB에 맞게 작성 후 사용
```yml
spring:
#  datasource:
#    url: jdbc:h2:tcp://localhost/~/example-community # db 경로
#    username: sa
#    password:
#    driver-class-name: org.h2.Driver

  jpa:
    hibernate:
      ddl-auto: create
```
5. CommuniyApplication 실행
6. http://localhost:8080 접속

## 개발환경
* IDE : IntelliJ
* OS : Window 10
* SpringBoot 2.7.6
* Java 11
* Gradle 7.6

## 라이브러리
* Spring Web
* Spring Data Jpa
* H2 Database (MySQL)
* Thymeleaf
* Lombok
* Spring Security 5.7.5

<hr>

* Bootstrap 5.1.3
* Querydsl 5.0.0

로컬에선 H2 Database를 사용했고 heroku에 배포 시에는 heroku 내장 db(JawsDB MySQL)를 사용했습니다.
디자인에 사용한 css와 js는 부트스트랩을 활용했습니다.

## 스프링부트 설정

> application.yml

```yml
spring:
  datasource:
    url: jdbc:h2:tcp://localhost/~/example-community # db 경로
    username: sa
    password:
    driver-class-name: org.h2.Driver # 배포 시에는 MySQL 드라이버 사용

  jpa:
    hibernate:
      ddl-auto: none

    properties:
      hibernate:
        format_sql: true # 로컬에서만 사용
        default_batch_fetch_size: 100

  mvc:
    hiddenmethod:
      filter:
        enabled: true

logging.level:
  org.hibernate.SQL: debug # 로컬에서만 사용

server:
  servlet:
    session:
    tracking-modes: cookie
```

## 사용 예제

사용 예제는 분량이 길어 [블로그 게시글](https://velog.io/@gudwn357/Spring-Example-Community-10)로 대체 하겠습니다.

## 세부 기능

### 회원 가입
* 이름, 로그인 ID, 비밀번호를 입력 받아 회원 가입
* 로그인 ID 는 중복 허용 X
* 비밀번호는 Spring Security가 제공하는 `BCryptPasswordEncoder`를 사용하여 암호화 후 저장

### 로그인
* 로그인 ID, 비밀번호를 입력받아 로그인
* Spring Security가 제공하는 `UserDetails`, `UserDetailsService`, `AuthenticationProvider`, 을 구현하고 `SecurityFilterChain`을 Bean으로 등록하여 기능 위임

### 회원 탈퇴
* 회원 탈퇴 시 회원이 생성한 게시글, 댓글, 좋아요 함께 삭제

### 게시글 리스트 조회
* 작성자, 글 제목, 댓글 수,작성일,	좋아요 수를 포함하여 리스트로 조회
* 페이징, 정렬
  * 페이징 : 한 번에 최대 10개씩 조회하며 화면 하단의 페이지 이동 버튼으로 이전, 혹은 다음 페이지 조회 가능
  하단 페이지 이동 버튼은 최대 5개 생성되며 게시글의 수에 맞게 페이지 버튼이 생성 됨 (게시글이 35개면 1~4 페이지 버튼 생성)
  * 정렬 : 작성일(PK) 기준 내림차순 정렬
* 검색 : 작성자, 글 제목, 내용을 기준으로 검색. 해당 기능에 필요한 동적 조회 쿼리는 Querydsl 을 사용하여 구현
검색 후 페이지 이동 시 검색 데이터 유지

### 게시글 생성 / 수정 / 삭제
* 제목, 내용을 입력 받아 게시글 생성 및 수정, 해당 게시글을 생성한 회원만 수정 가능
* 해당 게시글을 생성한 회원만 삭제 가능. 게시글 삭제 시 해당 게시글에 등록된 댓글, 좋아요 함께 삭제

### 댓글 생성 및 수정 / 삭제
* 내용을 입력 받아 댓글 생성 및 수정, 해당 댓글을 생성한 회원만 수정 및 삭제 가능

### 좋아요
* 회원은 한 게시글당 1개의 좋아요 생성 가능

### 공통
* 모든 엔티티에 생성 시간, 수정 시간을 JPA Auditing 을 이용하여 생성
* Spring Security를 이용하여 로그인 사용자 인증, 인가, csrf 공격 방어 기능 구현
* Spring Interceptor를 이용하여 권한 인가 기능 구현 ex) 본인이 생성한 게시글, 댓글에만 수정(수정 페이지 포함) 및 삭제 요청 가능



