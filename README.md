# Baking Buddy

- 운영: https://baking-buddy-image-6q5ymuc2ha-du.a.run.app/
- 로컬 스웨거: http://localhost:8080/swagger-ui/index.html#/
- 로컬 Actuator: http://localhost:8080/manage
- 운영 스웨거: https://baking-buddy-image-6q5ymuc2ha-du.a.run.app/swagger-ui/index.html#/

### 기술적 TODO
- 테스트코드
- 태그 검색, 재료 검색, 필터 등 - 디비, 검색 관련 속도 고민
- validation (중복 체크, 프론트 메세지 전달)
- Batch 알림
- es 구현 후 redis 와 비교해보기
- 모니터링 네이버 툴 (오픈소스)
- 운영/검증 환경 따로 구성 (profile 활용, 테스트 환경 구축)
- 알림 전송 (Spring Batch, Socket)

- 
### 기능적 TODO
- 친구, 채팅 기능 고려
- 배율 기능
- 레시피 순서 별 체크박스 기능, 멀티 타이머 기능
- 시간 배율 적용/미적용
- 재료 정량 구체화
- 개인화 추천: (고민 필요, 실시간 추천 or AI 학습)
- 
# 주요 기능
- 검색 자동완성 기능: Redis 구현
- JPA, QueryDSL: 자바 코드로 동적 쿼리 작성 (조인, 검색, 페이징 등)
- CI/CD: 깃허브 & Cloud Run으로 자동 배포
- Spring Security를 활용한 로그인 기능
- 
- Spring Actuator, Prometheus 모니터링


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


# 참고 문서
- ![스프링 레디스](https://googlecloudplatform.github.io/spring-cloud-gcp/reference/html/#cloud-memorystore-for-redis)
- ![Spring Security](https://velog.io/@suhyun_zip/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8%EB%A1%9C%EA%B7%B8%EC%95%84%EC%9B%83-%ED%9A%8C%EC%9B%90-%EA%B0%80%EC%9E%85-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0)