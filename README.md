# 📝 프로젝트 소개
>약 2주 동안 클라이언트 1명, 백엔드 2명에서 진행한 원티드 웹 클론 프로젝트입니다. 
>제작 기간: 2021년 10월 30일 ~ 11월 12일


</br>

## 💁‍♂️ Wiki

- [Ground Rule]()
- [기획서]()
- [명세서]()

</br>

## 🛠 사용 기술
#### `Back-end`
  - Java 11
  - Spring Boot 2.4.2
  - Gradle
  - MySQL 5.7
#### `DevOps`
  - AWS(EC2, RDS)
  - Nginx
  - GitHub
#### `Etc`
  - JWT

</br>

## 📦 ERD 설계
![](https://user-images.githubusercontent.com/70616657/141675583-470edb24-219b-448f-93f7-fc49855dcc17.png)


## 🔎 핵심 기능 및 담당 기능
원티드 서비스의 핵심기능은 채용정보 제공 및 구직자와 구인자의 매칭입니다.
이러한 점에서 저희는 많은 원티드 서비스 중에서도 회원과 기업, 채용정보, 이력서 기능에 초점을 맞춰 개발했습니다.
저는 그 기능들 중에서도 `메인페이지, 기업, 회원 프로필, 이력서 출력`과 관련된 API를 담당하였습니다.



<details>
<summary><b>핵심 기능 설명 펼치기</b></summary>
<div markdown="1">

### 전체 흐름
![](https://zuminternet.github.io/images/portal/post/2019-04-22-ZUM-Pilot-integer/flow1.png)

   
### Interceptor

![](https://zuminternet.github.io/images/portal/post/2019-04-22-ZUM-Pilot-integer/flow_controller.png)

- **Preflight Request 처리** :pushpin: [코드 확인]()
  - 브라우저는 요청을 보내기 전 Preflight Request를 우선적으로 보내기 때문에 Interceptor에서 토큰을 검사하기 위해 Preflight Request를 가장 먼저 선별, 처리해줘야 한다.

- **로그인 인가 확인** :pushpin: [코드 확인]()
  - @UnAuth 어노테이션을 만들어서 인가가 필요하지 않은 API 메서드에 명시하였다. 그 후, Interceptor에서 @UnAuth 어노테이션을 체크하여 로그인이 필요한 API와 그렇지 않은 API를 구분하였다.

- **토큰 여부 확인** :pushpin: [코드 확인]()
  - 보내온 Request에 토큰이 있는지 확인하고, 해당 토큰의 여부에 따라 알맞은 로직을 처리해주었다.
   

### Controller

![](https://zuminternet.github.io/images/portal/post/2019-04-22-ZUM-Pilot-integer/flow_controller.png)

- **요청 처리** :pushpin: [코드 확인](https://github.com/Integerous/goQuality/blob/b2c5e60761b6308f14eebe98ccdb1949de6c4b99/src/main/java/goQuality/integerous/controller/PostRestController.java#L55)
  - Controller에서는 요청을 화면단에서 넘어온 요청을 받고, Service 계층에 로직 처리를 위임합니다.
  - 로그인이 필요한 서비스의 경우, Interceptor에서 토큰 검사 후, Request에 저장해둔 userId를 HttpServletRequest 객체로 받는다.

- **결과 응답** :pushpin: [코드 확인]()
  - Service 계층에서 넘어온 로직 처리 결과(메세지)를 미리 정의해둔 BaseResponse 객체에 담아 화면단에 응답해줍니다.
   

### Service

![](https://zuminternet.github.io/images/portal/post/2019-04-22-ZUM-Pilot-integer/flow_service1.png)

- **검증 처리** :pushpin: [코드 확인]()
  - 이미 등록된 사업자등록번호, 접속한 회원이 소유한 회사인지 등 의미적 검증 처리를 진행하였습니다.

- **트랜잭션 처리** :pushpin: [코드 확인]()
  - 쿼리 로직 중에 에러가 발생할 경우, 롤백 처리를 하기 위한 트랜잭션 처리를 어노테이션을 활용하여 처리해주었습니다.


### Repository

![](https://zuminternet.github.io/images/portal/post/2019-04-22-ZUM-Pilot-integer/flow_repo.png)

- **쿼리 수행** :pushpin: [코드 확인]()
  - JDBC Template를 활용하여 DB 쿼리 로직을 수행했습니다.
  - JOIN문 등 긴 쿼리문을 수행할 경우, Buffer를 활용하여 가독성과 '+' 연산을 최소화 하였습니다.
   
   
### Etc
   
- **타입 검증 처리** :pushpin: [코드 확인]()
   - Bean Validation을 활용하여 DTO에서 타입(형식) 검증을 수행하였다. 이를 통해 검증 로직을 분리할 수 있었다.
   
- **예외 처리** :pushpin: [코드 확인]()
   - ControllerAdvice에서 예외를 통합하여 처리하였다.
   - 각각의 예외의 경우 Enum을 통하여 등록, 관리할 수 있도록 하였다.
   - 등록되지 않은 예외의 경우, 예외 log를 console에 출력하고, 서버에 등록되지 않은 에러임을 응답해주었다.
   

   
</div>
</details>

</br>


## 🌟 트러블 슈팅
<details>
<summary>DTO가 많아지는 문제</summary>
<div markdown="1">

- 
- `$ npm install —save-dev webpack-dev-server@3.0.0`

</div>
</details>

<details>
<summary>Jdbc템플릿 queryForObject() null 값 문제</summary>
<div markdown="1">
  
  - main.js 파일에 `Vue.config.devtools = true` 추가로 해결
  - [https://github.com/vuejs/vue-devtools/issues/190](https://github.com/vuejs/vue-devtools/issues/190)
  
</div>
</details>

<details>
<summary>쿼리문 문자 부분에 '?' 파라미터가 들어가야할 경우</summary>
<div markdown="1">
  
  - `v-on:keyup.enter.native=""` 와 같이 .native 추가로 해결
  
</div>
</details>

<details>
<summary> CORS 정책 </summary>
<div markdown="1">
  
  - 에러 메세지(500에러)
    - No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationConfig.SerializationFeature.FAIL_ON_EMPTY_BEANS)
  - 해결
    - Post 엔티티에 @ManyToOne 연관관계 매핑을 LAZY 옵션에서 기본(EAGER)옵션으로 수정
  
</div>
</details>
    
<details>
<summary> Interceptor와 CORS 정책 </summary>
<div markdown="1">
  
  ```jsx
    $ npm run dev
    npm ERR! path C:\Users\integer\IdeaProjects\pilot\package.json
    npm ERR! code ENOENT
    npm ERR! errno -4058
    npm ERR! syscall open
    npm ERR! enoent ENOENT: no such file or directory, open 'C:\Users\integer\IdeaProjects\pilot\package.json'
    npm ERR! enoent This is related to npm not being able to find a file.
    npm ERR! enoent

    npm ERR! A complete log of this run can be found in:
    npm ERR!     C:\Users\integer\AppData\Roaming\npm-cache\_logs\2019-02-25T01_23_19_131Z-debug.log
  ```
  
  - 단순히 npm run dev/build 명령을 입력한 경로가 문제였다.
   
</div>
</details>    

    
</br>

## 6. 회고 / 느낀점
>프로젝트 개발 회고 글: https://zuminternet.github.io/ZUM-Pilot-integer/
