# wanted-pre-onboarding-backend

```
요구사항을 만족하는 API 서버를 구현합니다
```

## 🚀 필독 : 제출한 시기가 총 2개로 나뉘어 집니다.
- 
- main branch
  - 10/9 제출
- feature/crud-fix
  - 10/18 제출

위와같이 분할하여 제출한 이유는 제출 이후 더 나은 수정사항들이 생겨 추가해보았습니다.

제출 시기 점수(1점) 를 놓칠 수 없기에... 만약 가능하다면 main branch 와 feature/crud-fix 중 높은 점수를 얻는 과제 점수를 얻고자
2개로 나누어 과제 제출을 마무리 하게 되었습니다.

✔️ 협업을 위한 branch 전략과 commit 컨벤션에 대해 고민해봅니다.

✔️ 가독성을 위해 15줄이 넘지 않도록 노력합니다.

## Directory Architecture

- src
  - main
    - apply
    - common
    - company
    - config
    - exception
    - post
      - api
      - application
      - dao
      - domain
      - dto
    - user
  - test
    - apply
    - post

## Commit Convention

```
Feat :	   새로운 기능 추가
Fix : 	   버그 수정
Docs : 	   문서 수정
Style :    코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
Refactor : 코드 리펙토링
Test : 	   테스트(테스트 코드 추가, 수정, 삭제, 비즈니스 로직에 변경이 없는 경우)
Chore :    위에 걸리지 않는 기타 변경사항 (빌드 스크립트 수정, assets image, 패키지 매니저 등)
Design :   CSS 등 사용자 UI 디자인 변경
Comment :  필요한 주석 추가 및 변경
Init :     프로젝트 초기 생성
Rename :   파일 혹은 폴더명 수정하거나 옮기는 경우
Remove :   파일을 삭제하는 작업만 수행하는 경우
```

## 모델링

![img](https://user-images.githubusercontent.com/109144975/273425964-802bceae-a378-4f42-b9f2-60b90c769166.png)

- 모델은 User, Post, Company, Apply 가 존재한다
- Post는 company_id를 가지며 Company는 공고를 생성할 수 있다
  - 따라서 company와 post는 1 : N 관계를 가진다
- User는 Post를 통해 지원할 수 있다
  - Post와 User 관계를 이어주는 Apply table을 가진다
  - Apply table은 user_id와 post_id를 FK로 가진다
  - 따라서 Post와 User은 Apply 를 통해 N : M 관계를 가진다


## 🎯 프로그래밍 환경

- IDE : IntelliJ
- JDK : 11
- DB : MYSQL 8.0
- test : junit
- SpringBoot : 2.7.16

## 시작가이드

### Installation

```
$ git clone https://github.com/rivkode/wanted-pre-onboarding-backend.git
$ cd wanted-pre-onboarding-backend
```




## 요구사항 분석 및 구현과정

본 서비스는 기업이 채용을 하기 위해 채용 홈페이지에 공고를 등록하고 이 공고를 통해 지원자들은 지원을 한다.

### 구현 기능

- 채용공고 등록
- 채용공고 수정
- 채용공고 삭제
- 전체 채용공고 보기
- 단건 채용공고 상세 보기
- 채용공고에 지원 하기

Swagger API

![image](https://github.com/rivkode/tech-for-developer/assets/109144975/3e216959-780c-483c-8448-e04496899f6c)





### 1. 채용공고 등록

요구사항 분석
- 공고 등록을 하기 위해 클라이언트로부터 데이터를 json 형식으로 전달받는다
- 전달받은 json 형식의 데이터를 PostRepository에 저장(save)한다

api
- **Post : /api/v1/post**

구현과정
- Controller에서 클라이언트로부터 post 생성을 위한 객체를 받기 위해 해당하는 column을 가진 CreatePostReqeust 를 생성합니다
- 객체 저장을 하기 위해 Service의 createPost()로 request객체를 넘겨줍니다
- 전달받은 request객체에서 toEntity 메서드를 통해 Post 엔티티로 변환 후 postRepository에 save 합니다
  - 이때 try catch 문을 통해 예외처리를 합니다 (DB와 데이터 처리를 하는 경우에는 다양한 예외가 발생할 수 있으므로)
- 정상적으로 성공하게되면 controller로 PostResponse 를 리턴합니다
- Controller에서는 result를 통해 정상 처리 되었을 경우 201 Created 상태코드와 PostResponse 를 반환합니다

### Request

![image](https://github.com/rivkode/wanted-pre-onboarding-backend/assets/109144975/6bcfa135-cf00-4d75-8816-0ce3f3c7b961)

### Response

![image](https://github.com/rivkode/wanted-pre-onboarding-backend/assets/109144975/8fedcc36-15d2-45eb-bc7c-cd63a46889b6)



### 2. 채용공고 수정

요구사항 분석
- 등록된 공고의 정보를 수정하기위해 클라이언트로부터 데이터를 json 형식으로 전달받는다
- 전달받은 json 형식의 데이터를 PostRepository에 저장(save)한다

api
- **PUT : /api/v1/post**

구현과정
- Controller에서 클라이언트로부터 post 수정을 위한 객체를 받기 위해 UpdatePostRequest를 생성합니다
- Service에서 기존에 존재하는 post를 postId로 찾습니다
  - 이때 try catch 문을 통해 예외처리를 합니다
- Optional을 통해 post객체가 존재하지 않을 경우에는 INFO_NOT_EXIST 를 반환하도록 합니다
- 정상적으로 수정이 완료되면 Controller 로 변경된 PostResponse 를 반환합니다
- Controller에서 result를 통해 정상처리 되었을 경우 200 OK 상태코드와 변경된 PostResponse 를 반환합니다


### Request

![image](https://github.com/rivkode/wanted-pre-onboarding-backend/assets/109144975/4bebc397-d013-42c2-9186-0d2ea07875cd)

### Response

![image](https://github.com/rivkode/wanted-pre-onboarding-backend/assets/109144975/a7f9f596-7a1d-4224-99b0-4eac016475d8)


### 3. 채용공고 삭제

요구사항 분석
- 등록된 공고를 삭제하기 위해 postId를 클라이언트로부터 PathVariable을 통해 전달받습니다
- postRepository에서 삭제(delete) 합니다
- 삭제한 데이터를 보존하기 위해 soft delete를 수행합니다

api
- **DELETE : /api/v1/post**

구현과정
- Controller에서 클라이언트로부터 post 삭제를 위해 postId를 전달받습니다
- Service에서 기존에 존재하는 post를 postId로 찾습니다
- 찾은 post 객체를 삭제합니다
  - post의 isDeleted 컬럼을 true로 변경합니다
- Controller에서 result를 통해 정상 삭제가 되었을 경우 204 NO_CONTENT 상태코드를 반환합니다

### Request

![image](https://github.com/rivkode/wanted-pre-onboarding-backend/assets/109144975/04bcd40d-79dd-4d92-b938-5a2ccdc0201c)


### Response

![image](https://github.com/rivkode/wanted-pre-onboarding-backend/assets/109144975/a78290c0-de4e-4b11-93ee-9a6634e1a453)



### 4. 채용공고 읽기

요구사항 분석
- 등록된 공고목록을 읽어들입니다
- postRepository에서 post객체 데이터들을 load(get) 합니다

api
- **GET : /api/v1/post/list**

구현과정
- 모든 post 객체를 읽어들이므로 클라이언트로부터 전달받을 내용은 업습니다
- Service에서 postRepository.findAll()를 통해 모든 post 객체 데이터를 리스트에 담아 전달받습니다
  - 이때 PostResponse.from() 메서드를 통해 클라이언트에서 원하는 데이터형식에 맞도록 Post -> PostResponse 객체로 변환합니다
- 변환된 리스트값을 다시 Controller로 전달합니다
- Controller는 정상 처리가 되었을 경우 200 OK 상태코드와 전달받은 리스트 값을 Body에 담아 함께 반환합니다

### Request

![image](https://github.com/rivkode/tech-for-developer/assets/109144975/521c12e0-59ad-4e81-a950-6e10bf211a42)


### Response

![image](https://github.com/rivkode/tech-for-developer/assets/109144975/13acdc4b-5f3f-475d-8ae5-4d019c151a69)



### 5. 채용공고 검색

요구사항 분석
- Parameter을 통해 검색할 keyword를 전달받습니다
- 전달받은 keyword를 포함하는 모든 post 객체를 반환합니다
- 이때 keyword는 모든(post_id 제외) column에 해당할 수 있습니다
- 데이터 무결성을 위해 Post 컬럼 중 position, country, region, skills 에 대해 enum 사용하여 정해진 값을 전달받습니다
  - 프론트엔드에서 position, country, region, skills 에 대해서는 정해진 값을 전달받는다고 가정합니다.
  - ex) country에서 한국을 검색하고 싶을 경우 [한국, 대한민국, korea, south of korea] 등 모든 string 값을 받게 되면 검색의 의미가 없으므로

api
- **GET : /api/v1/post?search={keyword}**

구현과정
- 검색을 위해 keyword를 클라이언트로부터 @RequestParam 을 통해 전달받습니다
- 전달받은 keyword는 Post의 모든 column에 해당될 수 있으므로 Specification 인터페이스와 CriteriaBuilder 를 사용하여 구현합니다
- Service의 searchAllPosts() 메서드에서 search() 메서드를 통해 query를 동적으로 생성합니다
  - search() 메서드는 검색어를 입력받아 query의 join문과 where 문을 생성하여 리턴하는 메서드입니다
  - 검색 결과에는 Company의 이름도 포함되어야 합니다
  - post table 에는 company_id값을 저장하므로 company table과 join하여 company_name을 찾아야합니다
  - 이를 위해 post와 company를 outerJoin(JoinType.LEFT) 합니다
  - join한 결과에서 name 필드에 대해 like 문을 적용하여 name 내에 전달받은 keyword를 앞 뒤로 포함하는지 검색합니다
  - 나머지 column에 대해서는 동일하게 get()을 통해 원하는 필드값에 대해 keyword 검색을 수행합니다
    - 이때 reward는 숫자이므로 try catch문을 통해 예외처리를 수행하였습니다
    - 컬럼 중 enum으로 설정한 컬럼에 대해 .as(String.class) 을 통해 string 값으로 검색을 가능하게 합니다 
  - Post table을 root로 하는 Specification 형식의 query를 postRepository.findAll()로 전달합니다
  - postRepository는 전달받은 데이터를 바탕으로 DB 에서 검색을 수행하며 결과를 리스트형식으로 반환합니다
  - 반환된 객체를 클라이언트에서 원하는 타입으로 변환하기 위해 PostResponse.from() 을 통해 수행합니다
  - 이 결과를 Controller로 반환합니다
  - Controller는 정상 처리가 되었을 경우 200 OK 상태코드와 전달받은 리스트 값을 Body에 담아 함께 반환합니다 

### Request

![image](https://github.com/rivkode/tech-for-developer/assets/109144975/96877d5f-494b-44c8-9e69-583273925f99)


### Response

![image](https://github.com/rivkode/wanted-pre-onboarding-backend/assets/109144975/46f51aec-2058-42db-829a-eb9b5e6fdd7b)


### 6. 채용공고 상세페이지

요구사항 분석
- PathVariable을 통해 postId를 전달받습니다
- 전달받은 Post를 읽어들입니다

api
- **GET : /api/v1/post/{postId}**

구현과정
- postId를 클라이언트로부터 전달받습니다
- Service에서 Company가 작성한 다른 postIds 들도 필요하므로 postRepository에서 조회합니다
  - 이때 query문은 "SELECT post_id FROM post WHERE company_id" 과 같습니다
- 조회한 내용들을 클라이언트가 원하는 타입으로 반환하기 위해 PostResponse.detailFrom() 을 통해 변환합니다
- 정상적으로 처리가 된다면 Controller로 객체를 전달합니다
  - 이때 Post가 존재하지 않는다면 DATA_NOT_EXIST를 반환합니다
- Controller는 정상 처리가 되었을 경우 200 OK 상태코드와 전달받은 값을 Body에 담아 함께 반환합니다

### Request

![image](https://github.com/rivkode/tech-for-developer/assets/109144975/2aa3595c-7019-49aa-9251-3ff3f63594a2)


### Response

![image](https://github.com/rivkode/tech-for-developer/assets/109144975/3d66509f-f8fa-4ffe-bae3-45fa93549d59)


### 7. 채용공고 지원

요구사항 분석
- RequestBody를 통해 postId와 userId를 전달받습니다
- 전달받은 두 엔티티를 연결시킵니다
- 동시성 이슈를 해결하기 위해 @Transactional, for update, synchronized 키워드를 사용합니다

api
- **POST : /api/v1/apply**

구현과정
- postId와 userId를 클라이언트로부터 전달받습니다
- AppplyController에서 ApplyService의 createApply() 메서드를 실행합니다
- createApply() 메서드는 @Transactional 로 proxy 객체를 통해 메서드 앞뒤로 Transaction이 생성되고 종료됩니다
- applySave() 메서드에서 synchronized 를 통해 여러개의 스레드가 한개의 자원을 사용하고자 할 때
- 현재 데이터를 사용하고 있는 해당 스레드를 제외하고 나머지 스레드들은 데이터에 접근 할 수 없도록 막는 개념입니다.
  - 여기서 한개의 자원은 applySave() 입니다
- 지원은 1 회만 가능하므로 Service에서 동일한 postId와 userId로 생성된 Apply가 applyRepository를 통해 존재하는지 찾습니다
- 만약 존재한다면 지원을 실행하지 않습니다
- 만약 존재하지 않는다면 user 와 post를 조회하여 저장을 진행합니다
  - 이때 query문은 "SELECT * FROM apply WHERE user_id = :userId and post_id = :postId for update" 와 같습니다
  - for update를 사용하여 조회하려는 row에 대해 LOCK 을 얻습니다
  - 조회 후 LOCK을 다시 반환합니다
- postId와 userId를 각 repository를 통해 조회한 뒤 ApplyDto.toEntity() 를 통해 Apply객체를 생성합니다
- 생성한 Apply 객체를 저장(save) 합니다
- 정상적으로 처리가 되었을 경우 200 OK 상태코드와 ApplyDto 를 반환합니다

### Request

![image](https://github.com/rivkode/tech-for-developer/assets/109144975/d37c8fef-0c1c-4175-bade-3067df3a81c4)

### Response

![image](https://github.com/rivkode/tech-for-developer/assets/109144975/e70ba63c-7c7e-4b1b-9629-dd799e8a357e)

## 배운 점 & 아쉬운 점

### 배웠던 점

이번 사이드 프로젝트를 통해서 배운점이 많습니다. 기본적인 CRUD 기능들은 쉽다고 생각하였지만 더 높은 가독성을 위해 메서드 내에서의 상수 사용과 예외처리를 하는 법을
알 수 있었습니다. 그리고 Specification 과 CriteriaBuilder를 통해 비교적 쉽게 동적 query를 만들고 사용해볼 수 있었습니다.

데이터의 무결성을 고려하여 Enum을 사용하였습니다. Post의 컬럼인 Country, Region 에 대해 정해진 값들을 받으며 사용자가 검색 시 올바른 검색결과를
얻기 위해 enum을 사용하였습니다.
- 이때 프론트엔드에서는 사전에 정의한 값들을 요청한다고 가정하였습니다.


동시성 이슈와 관련하여 @Transactional 어노테이션의 동작원리에 대해 찾아보게 되었고 Transaction 생성이 어느시점에 발생하는지와 공유자원 관리가 중요하다는 점을 알게 되었습니다.

메서드에서 parameter를 전달받을때 예상치 못한 입력에 대해 예외처리를 어떻게 할지 고민해보게 되었습니다.

검색 기능을 위해 최초에는 모든 Column 들을 String 으로 설정하였지만 그렇게 되어버리면 사용자들이  원하는 검색결과를 알기 어려울 것 같았습니다. 그래서 enum을 사용하여 정해진 값들을 받아 처리하였습니다.


### 아쉬운점

아쉬운 점은 테스트코드를 작성시 Mockmvc에 대한 지식이 부족하다보니 에러를 해결하는데에 어려움이 있었습니다. Request는 잘 도착하는 반면 Response에서 지속적으로
200 OK 와 null 이 반환되어 이유를 찾지 못했습니다.. 

검색기능을 개발하며 like 키워드를 사용하였습니다. 간단한 검색 기능을 위해 like 사용도 적절할 수 있지만 비용이 많이 발생할 수 있습니다. [LIKE % 위치에 따른 인덱스](https://velog.io/@rivkode/MySQL-LIKE-%EC%9C%84%EC%B9%98%EC%97%90-%EB%94%B0%EB%A5%B8-%EC%9D%B8%EB%8D%B1%EC%8A%A4) 
최적화를 위해서는 어떤 방법이 있을지에 대해서 고민해보며 부족한 부분을 채워가겠습니다.

정규화

채용 공고에서는 보통 여러개의 Skills를 요구하므로 Post에서 Skills를 입력할때 요구는 Skills는 여러개가 될 수 있습니다
이에 따라 Skills 와 PostSkills 라는 엔티티를 정의하며 정규화를 통해 중복제거를 하고자 하였고 Post 조회시 아래 query를 통해 조회하고자 하였습니다.

```sql
SELECT p.*
FROM Post p
JOIN PostSkills ps ON p.post_id = ps.post_id
JOIN Skills s ON ps.skill_id = s.skill_id
WHERE p.post_id = ?;

```

하지만 Skills를 Controller에서 여러개의 값을 전달할때 어떻게 전달받고 service에서 처리해야할지 확신이 서지 않았습니다.

문자열 형식으로 입력을 받아 parsing 을 하여 다시 리스트에 넣어서 처리해야하는지 혹은 다른 방법이 있을지에 대해 답이 나오지 못했습니다.

그리고 Company와 Post 엔티티에서 Country와 Region에 대해 중복이 발생하지 않을까? 고민을 하였지만
만약 Company가 가지는 Country, Region에 대해 지역을 한정하게 되면 안될 것 같아 Company가 Post를 작성할때 추가하도록 하는 방법으로 진행하였습니다.


## 🚀 필독 : 제출한 시기가 총 2개로 나뉘어 집니다.
- 
- main branch
  - 10/9 제출
- feature/crud-fix
  - 10/18 제출

위와같이 분할하여 제출한 이유는 제출 이후 더 나은 수정사항들이 생겨 추가해보았습니다.

제출 시기 점수(1점) 를 놓칠 수 없기에... 만약 가능하다면 main branch 와 feature/crud-fix 중 높은 점수를 얻는 과제 점수를 얻고자
2개로 나누어 과제 제출을 마무리 하게 되었습니다.