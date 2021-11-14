# 📝 프로젝트 소개
>약 2주 동안 클라이언트 1명, 백엔드 2명에서 진행한 원티드 웹 클론 프로젝트입니다.  
>제작 기간: 2021년 10월 30일 ~ 11월 12일


</br>

## 💁‍♂️ Wiki

- [Ground Rule]()
- [기획서]()
- [명세서]()
- [디렉토리 구조]()
- [Issue, PR 예시]()
- [시연 영상](https://youtu.be/6_us3-ApNu4)

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
![](https://user-images.githubusercontent.com/70616657/141680903-f4a489db-7e7b-444e-995c-5ddcbde26ac7.png)

   
### Interceptor

![](https://user-images.githubusercontent.com/70616657/141680914-05c681e6-346b-4dc5-b861-20b696ec2564.png)

- **Preflight Request 처리** :pushpin: [코드 확인](https://github.com/Jsim6342/wanted-clone/blob/59232b2abe9e51ff515f14a12825b699205f2bc5/src/main/java/com/example/demo/com/interceptor/LoginCheckInterceptor.java#L30)
  - 브라우저는 요청을 보내기 전 Preflight Request를 우선적으로 보내기 때문에 Interceptor에서 토큰을 검사하기 위해 Preflight Request를 가장 먼저 선별, 처리해줘야 한다.

- **로그인 인가 확인** :pushpin: [코드 확인](https://github.com/Jsim6342/wanted-clone/blob/59232b2abe9e51ff515f14a12825b699205f2bc5/src/main/java/com/example/demo/com/interceptor/LoginCheckInterceptor.java#L36)
  - @UnAuth 어노테이션을 만들어서 인가가 필요하지 않은 API 메서드에 명시하였다. 그 후, Interceptor에서 @UnAuth 어노테이션을 체크하여 로그인이 필요한 API와 그렇지 않은 API를 구분하였다.

- **토큰 여부 확인** :pushpin: [코드 확인](https://github.com/Jsim6342/wanted-clone/blob/59232b2abe9e51ff515f14a12825b699205f2bc5/src/main/java/com/example/demo/com/interceptor/LoginCheckInterceptor.java#L39)
  - 보내온 Request에 토큰이 있는지 확인하고, 해당 토큰의 여부에 따라 알맞은 로직을 처리해주었다.
   

### Controller

![](https://user-images.githubusercontent.com/70616657/141680924-87bed3d8-36d5-426d-b398-2121ec51f1ff.png)

- **요청 처리** :pushpin: [코드 확인](https://github.com/Jsim6342/wanted-clone/blob/59232b2abe9e51ff515f14a12825b699205f2bc5/src/main/java/com/example/demo/src/company/CompanyController.java#L64)
  - Controller에서는 요청을 화면단에서 넘어온 요청을 받고, Service 계층에 로직 처리를 위임합니다.
  - 로그인이 필요한 서비스의 경우, Interceptor에서 토큰 검사 후, Request에 저장해둔 userId를 HttpServletRequest 객체로 받는다.

- **결과 응답** :pushpin: [코드 확인](https://github.com/Jsim6342/wanted-clone/blob/59232b2abe9e51ff515f14a12825b699205f2bc5/src/main/java/com/example/demo/src/company/CompanyController.java#L66)
  - Service 계층에서 넘어온 로직 처리 결과(메세지)를 미리 정의해둔 BaseResponse 객체에 담아 화면단에 응답해줍니다.
   

### Service

![](https://user-images.githubusercontent.com/70616657/141680931-7c1b26b4-847f-446f-92a8-8f1767d10689.png)

- **검증 처리** :pushpin: [코드 확인](https://github.com/Jsim6342/wanted-clone/blob/59232b2abe9e51ff515f14a12825b699205f2bc5/src/main/java/com/example/demo/src/company/CompanyService.java#L34)
  - 이미 등록된 사업자등록번호, 접속한 회원이 소유한 회사인지 등 의미적 검증 처리를 진행하였습니다.

- **트랜잭션 처리** :pushpin: [코드 확인](https://github.com/Jsim6342/wanted-clone/blob/59232b2abe9e51ff515f14a12825b699205f2bc5/src/main/java/com/example/demo/src/company/CompanyService.java#L16)
  - 쿼리 로직 중에 에러가 발생할 경우, 롤백 처리를 하기 위한 트랜잭션 처리를 어노테이션을 활용하여 처리해주었습니다.


### Dao

![](https://user-images.githubusercontent.com/70616657/141680944-61a60ec1-a9d4-4c1e-8c9a-1a5d0200c543.png)

- **쿼리 수행** :pushpin: [코드 확인](https://github.com/Jsim6342/wanted-clone/blob/59232b2abe9e51ff515f14a12825b699205f2bc5/src/main/java/com/example/demo/src/company/CompanyDao.java#L108)
  - JDBC Template를 활용하여 DB 쿼리 로직을 수행했습니다.
  - JOIN문 등 긴 쿼리문을 수행할 경우, Buffer를 활용하여 가독성과 '+' 연산을 최소화 하였습니다.
   
   
### Etc
   
- **타입 검증 처리** :pushpin: [코드 확인](https://github.com/Jsim6342/wanted-clone/blob/59232b2abe9e51ff515f14a12825b699205f2bc5/src/main/java/com/example/demo/src/company/model/req/PostCompanyReq.java#L12)
   - Bean Validation을 활용하여 DTO에서 타입(형식) 검증을 수행하였다. 이를 통해 검증 로직을 분리할 수 있었다.
   
- **예외 처리** :pushpin: [코드 확인](https://github.com/Jsim6342/wanted-clone/blob/59232b2abe9e51ff515f14a12825b699205f2bc5/src/main/java/com/example/demo/com/advice/ControllerAdvice.java#L13)
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

- 클라이언트 개발자를 고려하여, API 응답을 화면에 가장 Fit하게 내려주기 위해 노력했습니다. 그 과정에서 많은 VO와 DTO를 만들어야 하는 문제가 발생했고, 그에 따라 DTO 네이밍 문제와 관리 문제를 겪게 되었습니다.
- 가장 먼저, `Entity Class`를 만들어 해결하고자 했습니다. Entity를 활용하여 쿼리 조회에서 데이터를 받아오거나, DTO에 멤버변수 형태로 넣어 DTO의 객체 수를 줄이고자 했습니다. 하지만, 쿼리로 부터 필요하지 않은 데이터까지 모두 받아야와야 한다는 번거로움과 성능이슈, null이 들어가는 문제 등이 발생했습니다.
- 이후, `Inner Class`를 활용하여 해결했습니다. 응답을 내려줄 객체 DTO를 만들고, 그 안에 해당 DTO와 연관된 객체들을 Inner Class 형태로 만들어 네이밍 문제와 DTO를 보다 효율적으로 관리할 수 있게 하였습니다.
- 추가적으로 `Request와 Response DTO 패키지를 분리`하여 관리를 수월하게 했습니다.

</div>
</details>

<details>
<summary>Jdbc템플릿 queryForObject() null 값 문제</summary>
<div markdown="1">
  
  - Jdbc템플릿 queryForObject()메서드는 하나의 데이터를 반환한다. 0개 or 2개 이상일 경우 에러를 반환
  - 하지만, 데이터가 없을 경우 에러가 아닌 값을 null로도 반환해야할 경우가 있다.
  - 이럴 경우를 대비하여 데이터가 1개 or 0개가 예상되는 경우라도, queryForObject()를 사용하는 것이 아닌, `query()를 활용`하여 List형태로 받아내어 null을 처리하면 된다.
  - 결론: jdbc에서 null값이 예상되는 쿼리문은 무조건 query()로 받아 `list형태로 받자`.
  
</div>
</details>

<details>
<summary>쿼리문 문자 부분에 '?' 파라미터가 들어가야할 경우</summary>
<div markdown="1">
  
  - LIKE '%?%' 와 같은 조건식에서 ?를 쿼리로 데이터를 조회하고 싶을 때 기존의 쿼리 문법으로는 '?' 문자 자체가 검색이되는 현상이 발생.
  - 쿼리에서 ?가 들어가는 부분을 문자열 덧셈 사이에 두어 해결할 수 있다.
  - 다음과 같이 버퍼를 통해서도 해결이 가능하다.  
  
  ```java
  String sql = sb.append("select * from goods where name = '").append(name).append("';").toString();
  ```
  
</div>
</details>

<details>
<summary> CORS 정책 </summary>
<div markdown="1">
  
  - 원티드 클론코딩 중, CORS 이슈가 발생하였습니다.
  - 이 문제는 SOP 보안 정책에 관한 것으로, 이 보안 정책에 따르면 URI Schema, Hostname, Port가 모두 같은 Origin 내에서 req와 res를 주고 받아야한다고 한다.
  - 프론트 프로젝트 저장소와 백엔드 프로젝트 저장소가 상이하여 URL이 달라 해당 보안 정책에 어긋나서 발생하는 문제였다.
  - 해결하기 위해서 프론트 측에서는 프록시 서버를 경유하여 해결하는 방법이 있으며, 서버 측에서는 Config 설정을 통해 해결할 수 있다.
  - 다만, 보안적인 부분이다보니 프론트 측에서 먼저 프록시 서버를 경유하는 방법을 시도하였고, 이후에 config 파일을 설정하는 방향으로 구현하였다.
  
</div>
</details>
    
<details>
<summary> Interceptor와 CORS 정책 </summary>
<div markdown="1">
  
  - config 파일 설정으로 해결된줄 알았던 CORS 에러가 또 발생하였다.
  - 원인은 Interceptor의 구현과 Preflight Request에 의한 에러였다.
  - 브라우저는 서버로 요청을 보낼 때, Preflight Request를 우선적으로 보내 접근 권한을 확인한다.
  - Interceptor는 접근한 Preflight Request에게서 토큰을 확인하는 작업을 하게되어 해당 문제가 발생했다.
  - 요청이 Preflight Request인지 확인하는 로직을 Interceptor에 추가하여 해결하였다.
   
</div>
</details>    

    
</br>

## 👩‍💻 리팩토링 계획

**해당 프로젝트는 차후에 다음과 같이 보완, 리팩토링할 예정입니다.**  

- Stream을 활용한 코드 리팩토링.
- MyBatis를 활용한 쿼리 분리 작업.
- JUnit을 활용한 테스트코드 작성.
- 프론트와 협업하여 API 연결 및 서비스 구동 상태 구축


