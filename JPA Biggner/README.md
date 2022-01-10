# ORM 기반 JPA 정리
 
## ORM (Objet-relational mapping)
 - 객체 관계 매핑이라고 불리며 객체와 데이터베이스의 연관 관계로 DB를 설계한다.
## JPA (Java Persistence API)
 - 자바 ORM 표준 기술로, 인터페이스의 모음이다.
 - JPA 인터페이스를 구현한 Hibernate, EclipseLink, DataNucleus를 사용한다.

<br/>

#### JPA 동작과정
```` 
 JAVA Application <-> JPA <-> JDBC API <-> DB 
````

### JPA 특징
1. 동일한 트랜잭션에서 조회한 엔티티는 같음을 보장한다.
2. 지연로딩(객체 사용시 로딩), 즉시로딩(연관된 객체까지 미리 로딩)
3. 모든 작업은 Transcation 안에서 실행

### JPA 구동방식
 1. META-INF/persistence.xml 설정정보 조회
 2. persistence 에서 EntityManagerFactory 생성
 3. EntityManagerFactory 에서 EntityManager 생성
 4. EntityManager 에서 connection 풀을 통한 DB 연결

## 영속성 컨텍스트
 - 엔티티를 영구 저장하는 환경, 논리적 개념
 - EntityManager.persist(entity) -> entity를 영속성 컨텍스트에 저장
 - DB에 바로 저장되는것이 아닌 영속성 컨텍스트에 저장되며 transcation commit 단게에서 DB에 저장된다.
 - EntityManager.flush를 이용한 컨텍스트 변경 내용을 강제로 DB에 반영해 줄 수 있다.

### 준영속 상태
 - DB에서 조회 시에 영속성 컨테스트에 데이터가 없을 경우 영속성 컨테이너로 올리지 않고 때낸다
 - 영속성 컨텍스트에서 빠져나오게 한다(분리)
 - EntityManager.detach, clear, close

# 객체와 테이블 매핑
### 데이터베이스 스키마 자동 생성
- 애플리케이션 실행 시 entity와 동일한 테이블이 생성된다
- config 설정을 통한 create,create-drop,update 등 생성 방식을 설정 할 수 있다. 

### @Entity
 - JPA가 관리하는 Class, 해당 어노테이션이 붙으면 JPA가 해당 객체가 Entity임을 인지
 - 기본 생성자가 필수이며, final 키워드 금지

## 필드와 컬럼 매핑 
### @Id  
  - DB의 PrimaryKey 와 매핑한다.
  
### @Column(name = "컬럼명",nullable, unique,length,columnDefinition)
   - 필드에 컬럼 정보 및 속성을 설정한다, 컬럼명을 따로 설정하지 않으면 필드명 그대로 생성된다
   - nullable : null 값 허용 여부, boolean 값 선언
   - unique : 유일한 컬럼
   - length : 문자 길이의 제약을 검, varchar
   - columnDefinition(DDL 문구) : 문자열 그대로 column 속성명을 설정한다.
 
### @Enumerated
   - Enum 타입 설정
   - EnumType.ORIDINAL : enum 순서를 DB에 저장한다, Default
   - EnumType.STRING : enum 문자열 그대로 저장 (권장)

### @GenerateValue(strategy = GENERATION.TYPE)
 - 기본키 생성 전략

 #### GENERATION.TYPE(IDENTITY,SEQUENCE,TABLE,AUTO)
 - IDENTITY : 기본키 생성을 DB에 위임한다. DB 방언에 따라 DB 기본키 생성, IDENTITY 전략에서만 예외적으로 entityManager.persist()가 호출되는 시점에 바로 DB에 INSERT 쿼리를 날린다
 - SEQUENCE : 데이터베이스 Sequence Object 사용, Sequence 는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트
     @SequenceGenerator 필수로 사용
 - TABLE : 키 생성 전용 테이블을 만들어 기본키 설정
 - AUTO : DB 방언에 따라 위 세가지 전략을 자동으로 지정, Default

```
권장 방식 : Long형 + 대체키 + 키 생성 전략 
```

## 연관관계 매핑
 - 연관관계를 설정할 때 관점을 DB 관점에서 봐야한다.
 - 고려사항 3가지 (다중성, 단뱡향•양방향, 연관관계 주입)


 * 다대일 (N:1, @ManyToOne)
   - 다(N) 쪽에 외래키를 설정하라
   - 양쪽 다 연관을 짓더라도 1 쪽의 테이블의 변경은 없다
 
 * 일대다 (1:N, @OneToMany(mappedBy = "반대쪽 필드 이름")) ?? => 확인해바 mappedBy
   - 엔티티가 관리하는 외래키가 다른 테이블에 있다, 추천하지 않음
   - 1 이 연관관계 주인이 된다.
   - mappedBy : ~에 의해 참조된다, 양방향 연관관게일 경우 반대쪽(주인) 객체 지정, 읽기전용

 * 일대일 (1:1, @OneToOne) 
   - 주 대상 테이블 중에 외래키 선택 가능
   - 주 테이블에 외래키 설정을 권유 

 * 다대다 (N:M, @ManyToMany) 
   - 쓰지마라, 연결 테이블을 추가해서 1:N, N:1 관게로 풀어나가자 

#### @JoinColumn(name = 외래키컬럼명)
 - 외래키를 매핑할때 사용


## 상속관계 매핑
 - 객체의 상속구조와 DB의 슈퍼타입 서브타입 관계를 매핑

### 조인전략
 - @Inheritance(Strategy = InheritanceType.JOINED)
 - 객체 각각의 PK를 가지고 조인하여 사용
 - @DiscriminatorValue(name = "이름") 을 통한 DTYPE 컬럼 값 추가로 객체 구분

### 단일 테이블 전략
 - @Inheritance(Strategy = InheritanceType.SINGLE_TABLE)
 - 한 테이블에 모든 컬럼으로 들어간다.
 - 모든 컬럼이 들어가 테이블이 너무 커질 수 있다.

### @MappedSuperClass
 - 공통 매핑정보가 필요할때 사용
 - 해당 어노테이션이 설정된 클래스를 상속받아 사용
 - 엔티티가 아니여서 조회,검색 불가
 - 추상 클래스로 선언

## 즉시로딩과 지연로딩
- 조회 시 사용하지 않은 Join Table을 조회를 해야하는가?!

 ### 즉시로딩 (Eager Loading)
  - 조회 시 JoinTable이 사용되지 않아도 무조건 다 가져온다.
  - fetch(FetchType.EAGER)
  - JPQL에서 N+1 

 ### 지연로딩 (Lazy Loading ,*추천)
  - Proxy를 통한 실제 Join Table이 사용될 떄 해당 DB를 저장
  - fetch(FetchType.LAZY)
  - @OneToMany, @ManyToOne 은 기본 전략이 즉시로딩이므로 지연로딩으로 바꿔줘야한다.
  
### 영속성 전이(CASCADE)
- 특정 엔티티를 영속 상태로 만들 떄 연관된 엔티티도 함께 영속 상태로 만든다.
- 하위 엔티티까지 함께 저장되도록 설정
- 영속성 전이를 하지 않으면 하위 엔티티를 persist를 계속 해줘야 한다.
- 부모와 하위 라이프사이클이 같을 떄, 단일 소유자 일 경우 사용
- cascade = CascadeTYPE.Types(ALL, PERSIST,REMOVE)

### 고아객체제거(orphanRemoval)
 - 부모엔티티와 연관관계가 끊어진 자식을 제거
 - 참조하는곳이 하나일 경우 사용
 - @OneToOne, @OneToMany만 가능,
 - CascadeType.Remove와 동일

```
영속성 전이 + 고아객체, 생명주기 
 - 두 옵션 모두 활성화 시 부모 엔티티를 통한 자식의 생명주기 관리가 가능하다
```

## 값 타입

### 임베디드 타입(내장 타입)
 - 기본 값 타입을 모아서 복합 값 타입을 만든다.
 - 재사용, 높은 응집도
 - @Embeddable 선언, 필드에는 @Embedded 사용

### 값 타입 복사
 - 값 타입의 실제 인서턴스인 값을 공유하는것은 위험하다
 - 따라서 불변객체러 선언해서 사용하여야 한다(Setter 제거, 생성자로 선언하여 사용)

### 값 타입 비교
 - equals and hashcode 선언으로 비교해주어야 한다.

### 값 타입 컬렉션
 - 값 타입을 하나 이상 지정할 떄 사용
 - 사용법 인지 필요

## 객체지향 쿼리 언어
 ### JPQL
 ### JPA Criteria
 ### QueryDSL
 ### NativeQuery
