# 도서 주문 서비스

## 요구사항

- **도서 카테고리**
    - 대분류로 국내도서, 외국도서로 나눔
    - 각 대분류 안에는 전체, 소설, 시/에세이, 예술, 역사/문화, 교육으로 소분류됨
    - 각 분류 클릭 시 해당 분류에 맞는 도서 목록이 화면에 그려짐
- **도서 목록**
    - 분류된 도서들의 목록이 그려짐
    - 도서 사진, 제목, 저자, 출판사, 출간일, 가격 정보 제공
    - 장바구니 버튼 장바구니로 이동 버튼 제공
- **도서 상세페이지**
    - 도서 사진, 이름, 가격, 저자, 출판사, 출시년도, 줄거리 정보 제공
- **구매 상세 페이지**
    - 배송지 정보 입력
    - 주문 상품들 나열
        - 도서 표지, 제목, 수량, 가격 정보 제공
    - 총 금액 정보 제공
    - 주문 완료, 취소 버튼 제공
- **장바구니에 담기**
    - 도서를 장바구니에 담을 수 있어야 한다.
- **장바구니 페이지**
    - 장바구니에 담긴 도서 목록 나열
        - 표지, 제목, 가격, 수량, 총 가격 정보 제공
        - 수량 추가, 감소 버튼 제공
    - 총 금액 정보 제공
    - 주문하기 버튼 제공
- ********************************장바구니에 담긴 도서 삭제********************************
- **주문 목록 확인 페이지**
    - 주문 ID, email, 주소, 주문 날짜, 총 금액, 주문한 도서 정보 제공

<br>

## 도메인

- **도서 (상품)**
    - 표지, 제목, 가격, 저자, 출판사, 출시일, 줄거리
- ****************카테고리****************
    - 대분류 : 국내도서, 외국도서
    - 소분류 : 소설, 시/에세이, 예술, 역사/문화
- **주문**
    - 주문자, 배송지, 상품
- **주소**
    - 시/도, 시/군/구, 도로명, 주소상세

<br>

## 테이블

### 도서 (Book)

| 도서ID (PK) | 제목 | 가격 | 출판사 | 저자 | 출시일 | 줄거리 | 수량 | 카테고리ID (FK) |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
- 도서ID는 정수형으로 1부터 시작된다.
- BCNF 정규형까지 만족한다.

### 카테고리 (Category)

| 카테고리ID | 대분류 | 소분류 |
| --- | --- | --- |
- 카테고리ID는 정수형으로 1부터 시작된다.
- 소분류가 대분류를 결정하는 이행함수적 종속성을 가지지만 함께 사용되는 경우가 많기 때문에 조회 성능을 위해 정규화를 하지 않는다.

### 주문자 (Customer)

| 주문자ID | 이메일 | 주소 |
| --- | --- | --- |
- 주문자ID는 정수형으로 1부터 시작된다.

### 주문 (Orders)

| 주문 ID | 주문자ID(FK) | 주문날짜 | 가격 | 주문 상태 |
| --- | --- | --- | --- | --- |
- 주문ID는 정수형으로 1부터 시작된다.
- 주문자 ID가 외래키이다.

### 주문상품(OrderBook)

| 주문상품ID | 주문ID(FK) | 도서ID(FK) | 수량 |
| --- | --- | --- | --- |
- 주문상품ID는 정수형으로 1부터 시작된다.
- 주문ID, 도서ID를 외래키로 가진다.

<br>


## Spring Rest Docs

API를 문서화 하기 위해 Spring Rest Docs를 사용하기로 했습니다. 

<br>



## 느낀점

- **JPQL은 상당히 불편하다.**
    - 쿼리를 문자열로 작성하기 때문에 컴파일 타임에 오류를 잡을 수 없어 오타로 인한 문제가 많이 발생함
    - SQL 작업 후 id를 반환하는 것이 번거로움
        - `GeneratedKeyHolder`를 이용한다.
    - 간단한 데이터 수정 작업(더하기, 빼기)에도 update문을 직접 작성해서 넣어줘야함 (JPA는 엔티티 객체를 수정하고 커밋하면 알아서 SQL 쏴줌)
    - 동적 쿼리 작성 시 정말 불편함

- **GraphQL이 왜 나왔는지 알 것 같다.**
    - DTO를 너무 포괄적으로 만들어서 사용하자니 낭비되는 리소스가 너무 많음
    - 그렇다고 DTO를 잘게 쪼개자니 클래스가 너무 많이 생성되고 그로인해 네이밍도 너무 힘듦.. 하나의 필드 차이로 인해 새로운 클래스를 만드는게 탐탁지 않음.

- JPQL로 값 조회 시 가공하려면 → *`ResultSetExtractor`* 을 사용한다

[Spring ResultSetExtractor Example - javatpoint](https://www.javatpoint.com/ResultSetExtractor-example)

- 혼자서 프론트까지 하는건 너무 벅차다. 백엔드보다 프론트엔드 로직을 작성하는데 더 많은 시간이 들었다. (HTML, CSS, Vue)

<br>


## 개선해야 할 점

- **예외 처리 및 검증 로직**
    - 예외 처리가 꼼꼼하지 못했다.
    - 입력값을 검증하는 로직이 없다.
- **기능이 부족하다.**
    - 검색 기능, 도서 등록 기능(이미지 등록), 로그인 기능 등을 추가하고 싶다.
- **리팩토링**
    - 코드가 지저분하고 일관성이 없다.
- ‘주문 페이지에 두 고객이 있고, 주문하려는 도서의 수량이 한 개일 때 나중에 주문하는 사람은 주문이 실패해야 한다.’는 예외 조건을 만족하기 위해 주문 완료 버튼을 누를 때 트랜잭션을 이용해서 주문하려는 책의 수량이 부족하면 주문, 회원 테이블에 삽입된 데이터가 롤백되도록 했는데 여러 사람이 동시에 눌렀을때 과연 의도한대로 처리되는지 모르겠다. (동시성 공부를 해야겠다.)
