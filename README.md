# example-community

## 소개

* 스프링부트 + JPA 를 기반으로 진행한 게시판 예제입니다.
* 핵심 기능으로 게시글, 댓글, 좋아요 CRUD 와 페이징, 검색 기능을 포함합니다.

* https://hhj-community.herokuapp.com 에서 해당 서비스 이용이 가능합니다. 
* Heroku의 저비용 서비스(echo dyno)와 무료 제공 DB를 사용해 다소 느릴 수 있습니다. 특히 첫 접속 시 프로젝트가 sleep 모드에서 새로 구동되어 로딩이 1분 가량 걸릴 수 있습니다. (sleep : 30분간 요청이 없는 경우 프로젝트 서버가 내려감)

이 프로젝트는 검증(Validation) 기능이 빠져있습니다. 때문에 일반적이지 않은 데이터 형식으로 입력 시 오류가 발생할 수 있습니다.

개발 과정은 [블로그](https://velog.io/@gudwn357/series/community)에 작성했으니 참고하시길 바랍니다.

## 로컬 사용법 (윈도우 10, 인텔리제이 기준)
1. 프로젝트 다운
2. 인텔리제이 Open `build.gradle` (`Open as Project`).
3. 인텔리제이 터미널에 `./gradlew clean compileQuerydsl` 입력
4. application.yml 에서 spring.datasource 부분 주석 처리 및 jpa.hibernate.ddl-auto: create 로 수정
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

> 홈 화면 / 경로 : Get = "/"

![](https://velog.velcdn.com/images/gudwn357/post/740e3db9-3d6d-4f94-a389-51f262a5ddca/image.png)

> 회원 가입 / 경로 : Get = "/join", Post = "/member"

![](https://velog.velcdn.com/images/gudwn357/post/f148cbc0-ffc1-4030-8477-269c8af92e4c/image.png)

회원 가입 시 로그인 ID는 중복이 허용되지 않습니다.

* 회원 가입 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/9f16be73-c43b-470f-84f4-c2c7f00bcd48/image.png)

처음에 발생하는 select 쿼리는 exist 쿼리입니다.

> 로그인 / 경로 : Get = "/login", Post = "/login"

![](https://velog.velcdn.com/images/gudwn357/post/ce094588-778d-45ba-a26b-090fe5f5c2b6/image.png)

스프링 시큐리티를 이용하여 로그인 기능을 처리합니다.

* 로그인 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/645446f1-f762-472e-adec-fe34dcde8bd8/image.png)

> 게시글 리스트 조회 / 경로 : Get = "/post"

![](https://velog.velcdn.com/images/gudwn357/post/f1cf6b0c-62fe-4b23-8c64-4f67c2a45392/image.png)

* 접속 시 발생하는 쿼리 : 

![](https://velog.velcdn.com/images/gudwn357/post/66f02260-1e0c-4b8d-a213-a3562fb69a9a/image.png)

* 검색 조건 : 작성자, 검색어 : member1 로 검색 시 화면 : 
![](https://velog.velcdn.com/images/gudwn357/post/57ef6163-cd95-476a-af79-5a346e5fb19f/image.png)

* 검색 조건 : 작성자, 검색어 : member1 로 검색 시 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/8ac80090-143c-4600-9444-cb109c6afda0/image.png)

처음 발생하는 select 쿼리에 where 가 알맞게 추가되었습니다.

> 게시글 작성 / 경로 : Get ="/post/add", Post = "/post"

![](https://velog.velcdn.com/images/gudwn357/post/5827284a-be3f-4a31-89ba-0273d13f59a8/image.png)

* 게시글 작성 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/068bbb80-0f6f-4429-82b2-2127fd0c66e6/image.png)

> 게시글 조회 / 경로 : Get = "/post/{post_id}"

![](https://velog.velcdn.com/images/gudwn357/post/d9521510-5554-4db0-804c-ff8ab904f49d/image.png)

* 게시글 조회 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/3a48153c-cea2-43af-9569-2f2149d7fc4c/image.png)

> 게시글 수정 / 경로 : Get = "/post/edit/{post_id}", Patch = "/post/{post_id}"

![](https://velog.velcdn.com/images/gudwn357/post/69978816-d6cd-4b1b-960c-3df392238027/image.png)

* 게시글 수정 페이지 조회 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/1d7dc110-a44e-4c35-9ac3-07ffe91af100/image.png)

* 게시글 수정 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/3624af28-ea07-41b6-a687-64e4146aead2/image.png)

> 댓글 작성 / 경로 : Post = "/post/{post_id}/comment"

![](https://velog.velcdn.com/images/gudwn357/post/91a0cbd0-0eb3-4ae4-9000-3fee8560a81b/image.png)

* 댓글 작성 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/389744f0-bff0-4525-9a24-bd111e8e37b4/image.png)

> 댓글 수정 / 경로 : Get = "/post/{post_id}/comment/edit/{comment_id}",
Post = "/post/{post_id}/comment/{comment_id}"

![](https://velog.velcdn.com/images/gudwn357/post/eb24d045-30f9-4690-a103-9915d3ee9871/image.png)

* 댓글 수정 페이지 조회 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/6becd22d-3c54-4b08-ba74-9e3c864d878e/image.png)

* 댓글 수정 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/a6d23a58-0615-4906-b774-feb4b434e1d4/image.png)

> 좋아요 생성 및 취소 / 경로 : Post = "/post/{post_id}/heaat"

![](https://velog.velcdn.com/images/gudwn357/post/69432419-54bf-45e8-9c6c-bf5c210755f0/image.png)

좋아요 버튼 클릭 시 상단의 좋아요 0 -> 1로 증가

* 좋아요 생성 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/345f99b7-070f-4c92-a2f2-fad299cc1a60/image.png)

---

이미 좋아요 등록한 게시글의 좋아요 버튼 클릭 시 상단의 좋아요 1 -> 0로 감소

![](https://velog.velcdn.com/images/gudwn357/post/88176432-e14d-4363-b09a-49d6d5e432fe/image.png)


* 좋아요 취소 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/01fb8e2f-4a27-4b1c-9c37-a4d144082fc8/image.png)


> 댓글 삭제 / 경로 : Delete = "/post/{post_id}/comment/{comment_id}"

* 댓글 삭제 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/26f92405-6686-4cbf-ac16-4e04f32a4ca1/image.png)

> 게시글 삭제 / 경로 : Delete = "/post/{post_id}"

* 게시글 삭제 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/4f77053c-a7ae-4e56-8e03-a6967a5a4fbb/image.png)

> 회원 탈퇴 / 경로 : Delete = "/member/{member_id}"

![](https://velog.velcdn.com/images/gudwn357/post/3702407d-76c9-41c2-a608-68d27c9565e2/image.png)

* 회원 탈퇴 시 발생하는 쿼리 : 
![](https://velog.velcdn.com/images/gudwn357/post/169fe372-012b-4efe-a96f-496553b658db/image.png)

1. 회원이 작성한 댓글, 좋아요 삭제
2. 회원이 작성한 게시글에 등록된 댓글, 좋아요 삭제
3. 회원이 작성한 게시글 삭제
4. 회원 삭제

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



