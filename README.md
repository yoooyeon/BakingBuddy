# Baking Buddy

- 운영: https://baking-buddy-image-6q5ymuc2ha-du.a.run.app/
- 로컬 스웨거: http://localhost:8080/swagger-ui/index.html#/
- 로컬 Actuator: http://localhost:8080/manage
- 운영 스웨거: https://baking-buddy-image-6q5ymuc2ha-du.a.run.app/swagger-ui/index.html#/

### 기술적 TODO
- 테스트코드
- 태그 검색, 재료 검색, 필터 등 - 디비, 검색 관련 속도 고민
- Batch 알림
- es 구현 후 redis 와 비교해보기
- 모니터링 네이버 툴 (오픈소스)
- 운영/검증 환경 따로 구성 (profile 활용, 테스트 환경 구축)
- 알림 전송, (Spring Batch)
- 성능 테스트, 부하 테스트
- 검색, 클릭 등 이벤트 수집 -> 취향 파악, 쿠폰 발급
- 좋아요, 게시글을 보고있는 회원 수 등 (Socket)


### 기능적 TODO
- 친구, 채팅 기능 고려
- 배율 기능
- 레시피 순서 별 체크박스 기능, 멀티 타이머 기능
- 시간 배율 적용/미적용
- 재료 정량 구체화
- 개인화 추천: (고민 필요, 실시간 추천 or AI 학습)
- 
# 주요 기능
- 검색 자동완성 기능: Redis로 구현 
  - 상품 id와 이미지 url을 같이 캐싱하여 미리보기 가능하도록 함
- 인기 검색어 기능: 1시간마다 인기 검색어 배치로 캐싱 
- 최근 검색어 기능: 이미 저장된 검색어는 timestamp만 갱신하고 중복되지 않도록 처리
- 특정 API 호출 등 이벤트 발행 - ApplicationEventPublisher 활용
- JPA, QueryDSL: 자바 코드로 동적 쿼리 작성 (조인, 검색, 페이징 등)
- CI/CD: 깃허브 & Cloud Run으로 자동 배포
- Spring Security를 활용한 로그인 기능
- JWT 토큰 적용
- Spring Actuator, Prometheus 모니터링

## 부수적인 것
- validation (중복 체크, 프론트 메세지 전달)

# Library
- Spring Boot: 3.2.5
  - Spring Boot Starter Data JPA: 3.2.5
  - Spring Boot Starter Web: 3.2.5
  - Spring Boot Starter Validation: 3.2.5
  - Spring Boot Starter Actuator: 3.2.5
  - Spring Boot Starter AOP: 3.2.5
  - Spring Boot Starter Thymeleaf: 3.2.5
- Springdoc OpenAPI: 2.0.2
- Spring Cloud GCP Starter: 1.2.5.RELEASE
- Spring Cloud GCP Storage: 1.2.5.RELEASE
- Spring Cloud GCP Starter SQL MySQL: 1.2.5.RELEASE
- Spring Cloud GCP Starter Redis: 1.2.5.RELEASE
- Querydsl JPA: 5.0.0
- Querydsl APT: 5.0.0
- Lombok: latest 
- Bootstrap: 3.3.7-1
- Thymeleaf Layout Dialect: latest
- P6Spy Spring Boot Starter: 1.5.8
- Micrometer Registry Prometheus: latest
- Redis Clients (Jedis): latest

# DB
- Google Cloud SQL MySQL 8.0.31
- Google Memory Store Redis 
- Google Cloud Storage Bucket (이미지 저장)


# CI/CD
- Github
- Docker
- Cloud Build
- Cloud Run
- Cloud Deploy


# Tools
- Intellij
- MacOS


# 기술 선택 시 고민한 내 
## JWT, Session
JWT는 속도 측면, 비용 측면에서 유리하다.
반면 세션은 데이터베이스에서 관리하니 여러 기기 로그인 등 관리 측면, 안정성 측면에서 유리하다.
JWT로 개발하다보니 불편한 점이 생겼다. thymeleaf에서 `recipe/{id}` 와 같이 링크로 태워 보낼 때 헤더에 토큰을 보내기가 번거롭다.
매번 api 요청, 화면 변경 시 토큰을 보내야한다. 그래서 쿠키를 사용할 수 있다. 쿠키를 사용하면 매번 토큰을 보내지 않아도 된다.
하지만 트렌드가 쿠키리스로 나아가는 만큼 대안이 필요할 것 같다.
생각해본 대안은 다음과 같다.
1. 세션 사용
2. requestParam으로 토큰 전송
3. Authorization 헤더로 토큰 전송
4. Auth0, Firebase Authentication, AWS Cognito 같은 클라우드 서비스 사용

## Socket, Polling, ServerSent
## Redis, ElasticSearch

# 참고 문서
- ![스프링 레디스](https://googlecloudplatform.github.io/spring-cloud-gcp/reference/html/#cloud-memorystore-for-redis)
- ![Spring Security](https://velog.io/@suhyun_zip/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8%EB%A1%9C%EA%B7%B8%EC%95%84%EC%9B%83-%ED%9A%8C%EC%9B%90-%EA%B0%80%EC%9E%85-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0)