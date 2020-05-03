# Coupon API

### 사용 기술
<pre>
> Java
> spring boot
> spring data jpa
> querydsl
> spring batch
</pre>
### 사용 방법

1. DB 스키마 및 테이블 생성, 초기 데이터 생성. 
<pre>
- 다음 의 내용을 DB 콘솔에서 실행.
> coupon database 생성
> table 생성
- coupon 데이터베이스의 coupon / user / batch 관련 테이블 생성 및 user 샘플 데이터 생성 확인
- application-develop.yml 의 DB 접속 정보 수정.(host / username / password)
</pre>

2. 빌드 (project root)
<pre>
./gradlew clean build -Pprofile=develop
</pre> 

3. 프로젝트 실행
<pre>

-- api 모듈 실행
java -jar coupon-api/build/libs/coupon-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=develop

-- api doc 확인
http://localhost:9090/swagger-ui.html

-- 사용자 아이디:
    위에서 생성한 user_seq 번호를 사용 하도록 합니다. 
    
-- batch 실행
java -jar coupon-batch/build/libs/coupon-batch-0.0.1-SNAPSHOT.jar --spring.profiles.active=develop --job.name=JOB_COUPON targetDate=20200505 
</pre>

### 추가 정보
<pre>
- 만료된 쿠폰의 상태 업데이트와, 만료 3일 전 쿠폰 알림은 배치로 진행.
- 해당 배치는 daily 로 Jenkins 혹은 기타 third-party 에서 실행 한다고 가정.
- 해당 배치는 만료된 쿠폰 상태 업데이트 와 만료 3일 전 쿠폰에 대해 Noti(콘솔출력) 하는 2 Step 으로 구성.
- 쿠폰 서비스의 구성도와 api 컬렉션 정보는 해당 프로젝트의 document 폴더 참조.
</pre>
