# 이 프로젝트는

> Spring 게시판 프로젝트 입니다.

## 기술 사양

### 개발 환경

1. 개발 언어
   * **자바** (version : **8**)
2. 개발 IDE
   * [Intellij](https://www.jetbrains.com/idea/) 를 기본으로 사용한다.
3. 코드 형상 관리
   * 코드 형상관리는 [Git](https://git-scm.com/) 을 이용한다.
   * 원격 저장소는 [GitHub](https://about.gitlab.com/) 으로 관리한다.
4. 코드 빌드
   * 코드 빌드 및 라이브러리 의존성 관리는 Maven을 사용한다.
5. Database
   * 개인 개발환경에서의 Database 는 mysql 를 아래 명령어와 같이 Docker 로 구동하여 사용한다.

6. RESTful 도구
   * API 구현 및 테스트는 [POSTMAN](https://www.getpostman.com/apps) 을 이용한다.

### 주요 Framework

* [Spring Boot](https://projects.spring.io/spring-boot/) 2.x
  * Spring 을 이용한 프로젝트 구성의 기본이 프레임워크이다.
  * 별도로 명시된 협의사항이 없다면 Spring Boot 에서 제안하는 라이브러리 버젼을 사용한다.

### 주요 기술

* [Docker](https://www.docker.com/)
  * 즉시 배포 가능한 격리된 환경의 경량화된 가상화 기술로 본 프로젝트에서는 서비스 이미지 생성 및 개발환경 인프라 구성에 사용한다.

## 개발 주요 인프라

* [GitHub](https://about.gitlab.com/)
  * 코드 형상 관리

## 개발시 주의사항

* Database는 항상 새로운 환경에서 재사용 가능하게끔 `schema.sql`와 `data.sql` 파일을 작성한다.

## JAVA Coding Convention

### 기본

* [자바 기본 가이드라인](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html) 을 준수.
* [Statements](http://www.oracle.com/technetwork/java/javase/documentation/codeconventions-142311.html) 을 준수.

### 들여쓰기

* `Tab` 이 아닌 `Space 4칸` 을 사용합니다.
* `Tab` 을 이용할 경우 사용자 환경등에 따라 들여쓰기 위치가 다르게 표시되거나, 버젼 관리시스템에서 변경된 것으로 잘못 인식할 수 있는 오류를 방지하기 위함이다.

### 로깅 프레임웍 사용

* `System.out.print` 대신 `Logger` 를 이용한다.

  >```java
  > public class ExampleClass {
  >
  >     private static final Logger LOGGER = LoggerFactory.getLogger(ExampleClass.class);
  > }
  >```

* IO 로 인한 시스템 성능 저하를 방지하기 위해 로깅 프레임웍 (Logback) 을 사용하며, 각 로거 레벨은 다음과 같이 사용한다.

|레벨|설명|적용 환경|
|--|--|-----|
|debug|개발시 데이터 디버깅을 위해 사용|개발자 로컬, 개발서버|
|info |어플리케이션이 정상작동임을 알기 위한 단순 알림을 위해 사용|모든 환경|
|warn |발생이 예상되나 어플리케이션 동작에 지장을 주지 않는 내용 확인을 위해 사용|모든 환경|
|error|발생될 확률이 희박하나 발생시 즉각적인 조치가 필요한 코드 확인을 위해 사용|모든 환경|

### 불필요한 코드 삭제

* 소스 형상 관리 시스템을 이용하여 변경이력이 관리되고 있으며, 차후에 불필요한 코드 삭제를 위해서는 매우 많은 비용이 필요하므로 사용하지 않는 코드는 주석처리하지 말고 즉시 삭제한다.

### 패키지

* **_기본 패키지명은_** `com.study.board` 로 한다.

* MVC 의 Controller 에 해당 하는 클래스는 `com.study.board.controller` 패키지 또는 그 하위에 위치한다.

  > ```java
  > package com.fast.admin.controller.ExampleController;
  > ```

### 클래스

* **_클래스명은_** `대문자로 시작하는 CamelCase` 로 한다.

* MVC 의 Controller 에 해당 하는 클래스는 `Controller` 접미사를 붙인다.
  > ex) IndexController, EventController

### 메서드

* **_메서드명은_** `소문자로 시작하는 CamelCase` 로 한다.

* 메서드에 전달되는 파라메터 또는 반환되는 값에서의 Map 클래스 사용을 지양한다.
  * Model (VO) 클래스가 아닌 단순 Map 클래스 사용은 개발자의 오타 등 파악하기 힘든 오류를 유발할 가능성이 크기 때문.

### 변수

* **_변수명은_** `소문자로 시작하는 CamelCase` 로 한다.

* **_상수는_** `대문자와 밑줄(_)로 이루어진 MacroCase` 로 한다.

  > ```java
  > public static final String PROJECT_NAME = "board";
  > ```

* **_List_** 클래스의 인스턴스 변수는 코드 가독성 증대 및 변수명 지정을 쉽게 하기 위하여 `List` 라는 접미사를 붙인다.

  > ```java
  > List<String> userList = new ArrayList<>();
  > ```

* **_Map_** 클래스의 인스턴스 변수는 코드 가독성 증대 및 변수명 지정을 쉽게 하기 위하여 `Map` 이라는 접미사를 붙인다.

  > ```java
  > Map<String, String> userNameMap = new HashMap<>();
  > ```

### 분기문

* **_if_** , **_else_** 뒤에 한칸을 뛰우며, 조건절 다음에 한칸을 뛰우고 다음의 형태를 가진다.

  > ```java
  > if (condition) {
  >     // if 와 ( 사이에 공백, ) 와 { 사이에 공백
  > } else if (anotherCondition) {
  >     // } 와 else 사이에 공백
  > } else {
  >     // TODO
  > }
  > ```

* 코드 가독성 및 의도치 않은 오류 발생을 막기 위해 조건문 다음에 `{ }` 를 필수로 사용한다.

* **_for_** 뒤에 한칸을 뛰우며, 조건절 다음에 한칸을 뛰우고 다음의 형태를 가진다.

  > ```java
  > for (initialization; condition; update) {
  >     // TODO
  > }
  > ```

### 예외

* `Exception` 은 불필요한 예외처리 코드 감소를 위해 `RuntimeException` 을 이용한다.

* 예외 처리가 필요한 경우 예외 처리 코드는 다음의 형태를 가진다.

  > ```java
  > try {
  >     // TODO Process Code
  > } catch (ExceptionClass exception) {
  >     LOGGER.error(exception.getMessage(), exception);
  >     // TODO Exception Handling
  > } finally {
  >     // TODO Finally Handling
  > }
  > ```
