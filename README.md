# Coupon API

### Technique
<pre>
> Java
> spring boot
> spring data jpa
> querydsl
> spring batch
> spring web security and jwt
</pre>

### Usage
> (해당 프로젝트 실행을 위해서는 반드시 아래 DB 스키마가 필요합니다.)
* DB 스키마 및 테이블 생성, 초기 데이터 생성. 
<pre>
- coupon-common module 의 schema.sql 내용을 DB 콘솔에서 실행
> coupon database 생성
> coupon/user/roles/user_roles, batch table 생성
- coupon / user / user_roles/roles/batch 관련 테이블 생성 확인
- application-develop.yml 의 DB 접속 정보 수정.(host / username / password)
</pre>

* Build (project root 경로에서 실)
<pre>
-- include test code
./gradlew clean build -Pprofile=develop

-- test code skip build
./gradlew clean build -Pprofile=develop -x test
</pre> 

* Run Project
<pre>
-- api 모듈 실행
java -jar coupon-api/build/libs/coupon-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=develop

-- web browser 에서 api document 확인
http://localhost:9090/swagger-ui.html
    
-- batch 실행
java -jar coupon-batch/build/libs/coupon-batch-0.0.1-SNAPSHOT.jar --spring.profiles.active=develop --job.name=JOB_COUPON targetDate=20200505 
</pre>

### Additional Info

> 쿠폰 사용 및 취소
<pre>
- 중복 취소 / 사용 불가능 하도록 하였습니다.
- 쿠폰 사용 요청시 쿠폰의 상태가 ASSIGN / CANCELED 인 상태만 가능합니다. (또한 expiredAt 확인)   
- 쿠폰 지급요청은 반드시 사용자 번호 (user_seq)가 필요합니다.
- 쿠폰 사용 / 취소 시 반드시 사용자 번호(user_seq)와 coupon 코드가  필요합니다. 
- 쿠폰 코드 형식은 다음과 같습니다.
> > OYNED-01202-GW424 
</pre>

> 쿠폰의 상태와 만료 쿠폰 알림 관리
<pre>
- 만료된 쿠폰의 상태 업데이트와, 만료 3일 전 쿠폰 알림은 배치로 진행.
(해당 배치는 daily 로 Jenkins 혹은 기타 third-party 에서 실행 한다고 가정.)
- 해당 배치는 만료된 쿠폰 상태 업데이트 와 만료 3일 전 쿠폰에 대해 Noti(콘솔출력) 하는 2 Step 으로 구성.
- 쿠폰 서비스의 구성도와 api 컬렉션 정보는 해당 프로젝트의 document 폴더 참조
</pre>

> 테스트 코드
<pre>
- api test 실행시 web scecurity 와 filter 등을 무시하도록 설정 하였습니다.
</pre>

> 기타
<pre>
</pre>
