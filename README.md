
# 지자체 협약 지원 API

### :memo: API 명세서

#### 1) 인증 API 

##### :bell: 사용자 등록

###### :pushpin: 기본정보
|Method|요청 URL|출력타입|인증여부
|---|---|---|---|
|POST|http://localhost:8080/api/user/create|JSON|No|

###### :pushpin: 요청변수(Request Body)
```json
{
	"email" : "1000",
	"password" : "222"
}
```
###### :pushpin: 요청예시
```
curl http://localhost:8080/api/user/create 
-d "{\"email\": \"test\", \"password\": \"222\"}" 
-H "Content-Type: application/json"
```
###### :pushpin: 응답(Response)
```json
{
    "timestamp": "2019년 12월 19일 목요일 02시 15분 57초",
    "path": "/api/user/create",
    "status": 200,
    "data": {
        "email": "1000",
        "password": null
    }
}
```
<br />

##### :bell: 사용자 로그인

###### :pushpin: 기본정보
|Method|요청 URL|출력타입|인증여부
|---|---|---|---|
|POST|http://localhost:8080/api/user/login|JSON|No|

###### :pushpin: 요청변수(Request Body)
```json
{
	"email" : "test",
	"password" : "222"
}
```
###### :pushpin: 요청예시
```
curl http://localhost:8080/api/user/login 
-d "{\"email\": \"test\", \"password\": \"222\"}" 
-H "Content-Type: application/json" 
```
###### :pushpin: 응답(Response)

```json
{
    "timestamp": "2019년 12월 19일 목요일 00시 39분 21초",
    "path": "/api/user/login",
    "status": 200,
    "data": {
        "access_token": "eyJ0eXAiOiJKV1QiLCJjcmVhdGVEYXRlIjoxNTc2NjgzNTYxNzU2LCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDAwIiwidXNlciI6eyJlbWFpbCI6IjEwMDAiLCJwYXNzd29yZCI6IjIyMiJ9LCJleHAiOjE1NzY2ODcxNjF9.oJGC0K-i15cC1UAlxJHlxXastVSiKDpsVfn4dJTBL6g",
        "expiration": 1576687161,
        "token_type": "Bearer"
    }
}
```
<br />

##### :bell: Refresh Token

###### :pushpin: 기본정보
|Method|요청 URL|출력타입|인증여부
|---|---|---|---|
|GET|http://localhost:8080/api/user/refresh|JSON|Yes|


###### :pushpin: 요청예시
```
curl -X GET http://localhost:8080/api/user/refresh 
-H "Authorization: Bearer {인증토큰}"
```

###### :pushpin: 응답(Response)
```json
{
    "timestamp": "2019년 12월 19일 목요일 02시 25분 25초",
    "path": "/api/user/refresh",
    "status": 200,
    "data": {
        "refresh_token": "eyJ0eXAiOiJKV1QiLCJjcmVhdGVEYXRlIjoxNTc2Njg5OTI1NTkyLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDAwIiwidXNlciI6eyJlbWFpbCI6IjEwMDAiLCJwYXNzd29yZCI6IjIyMiJ9LCJleHAiOjE1NzY2OTM1MjV9.nGwqx40qfAJFU9aYP8s5u8-va5US98DaW6S9gGiFJHM",
        "expiration": 1576693456,
        "token_type": "Bearer"
    }
}
```
<br />

##### :bell: 사용자 목록

###### :pushpin: 기본정보
|Method|요청 URL|출력타입|인증여부
|---|---|---|---|
|GET|http://localhost:8080/api/user/list|JSON|Yes|


###### :pushpin: 요청예시
```
curl -X GET http://localhost:8080/api/user/list 
-H "Authorization: {인증토큰}"
```

###### :pushpin: 응답(Response)
```json
{
    "timestamp": "2019년 12월 19일 목요일 13시 33분 35초",
    "path": "/api/user/list",
    "status": 200,
    "data": [
        {
            "email": "test",
            "password": null
        }
    ]
}
```
-------------------------------------

#### 2) 지자체 협약 지원 API 
##### :bell: 지원하는 지자체 목록

###### :pushpin: 기본정보
|Method|요청 URL|출력타입|인증여부
|---|---|---|---|
|GET|http://localhost:8080/api/region/list|JSON|Yes|

###### :pushpin: 요청예시
```
curl -X GET http://localhost:8080/api/region/list 
-H "Authorization: {인증토큰}"
```

###### :pushpin: 응답(Response)
```json
{
    "timestamp": "2019년 12월 19일 목요일 13시 40분 43초",
    "path": "/api/region/list",
    "status": 200,
    "data": [
        {
            "region": "강릉시",
            "target": "강릉시 소재 중소기업으로서 강릉시장이 추천한 자",
            "usage": "운전",
            "limit": "추천금액 이내",
            "rate": "3%",
            "institute": "강릉시",
            "mgmt": "강릉지점",
            "reception": "강릉시 소재 영업점"
        },
        {
            "region": "강원도",
            "target": "강원도 소재 중소기업으로서 강원도지사가 추천한 자",
            "usage": "운전",
            "limit": "8억원 이내",
            "rate": "3%~5%",
            "institute": "강원도",
            "mgmt": "춘천지점",
            "reception": "강원도 소재 영업점"
        },
        .
        .
        .
    ]
}
```
<br />

##### :bell: 지자체명 입력에 따른 지자체 정보

###### :pushpin: 기본정보
|Method|요청 URL|출력타입|인증여부
|---|---|---|---|
|GET|http://localhost:8080/api/region|JSON|Yes|

###### :pushpin: 요청예시
```
curl -X GET http://localhost:8080/api/region?region={지역명}
-H "Authorization: {인증토큰}"
```

###### :pushpin: 요청변수(Request Parameter)
|요청변수|타입|필수여부|설명
|---|---|---|---|
|region|string|Y|지자체명|


###### :pushpin: 응답(Response)
```json
{
    "timestamp": "2019년 12월 19일 목요일 13시 49분 09초",
    "path": "/api/region",
    "status": 200,
    "data": {
        "region": "강릉시",
        "target": "강릉시 소재 중소기업으로서 강릉시장이 추천한 자",
        "usage": "운전",
        "limit": "추천금액 이내",
        "rate": "3%",
        "institute": "강릉시",
        "mgmt": "강릉지점",
        "reception": "강릉시 소재 영업점"
    }
}
```
<br />

##### :bell: 지원하는 지자체 정보 수정

###### :pushpin: 기본정보
|Method|요청 URL|출력타입|인증여부
|---|---|---|---|
|POST|http://localhost:8080/api/region|JSON|Yes|

###### :pushpin: 요청 Header
```json
Authorization : {인증토큰}
```

###### :pushpin: 요청 변수(Request Body)
```json
[
    {
        "region": "강릉시",
        "target": "강릉시에 사는사람 중에서",
        "usage": "운전",
        "limit": "50억 이내",
        "rate": "3.7%",
        "institute": "강릉시",
        "mgmt": "강릉지점",
        "reception": "강릉시 소재 영업점"
    }
]
```

###### :pushpin: 응답(Response)
```json
{
    "timestamp": "2019년 12월 19일 목요일 14시 06분 24초",
    "path": "/api/region",
    "status": 200
}
```
<br />

##### :bell: 지원하는 지자체 정보 삭제

###### :pushpin: 기본정보
|Method|요청 URL|출력타입|인증여부
|---|---|---|---|
|DELETE|http://localhost:8080/api/region|JSON|Yes|

###### :pushpin: 요청변수(Request Parameter)
|요청변수|타입|필수여부|설명
|---|---|---|---|
|region|string|Y|지자체명|

###### :pushpin: 요청예시
```
curl -X DELETE http://localhost:8080/api/region?region={지자체명}
-H "Authorization: {인증토큰}"
```

###### :pushpin: 응답(Response)
```json
{
    "timestamp": "2019년 12월 19일 목요일 14시 09분 10초",
    "path": "/api/region",
    "status": 200
}
```
<br />

##### :bell: 지원한도 컬럼에서 지원금액으로 내림차순 정렬

###### :pushpin: 기본정보
|Method|요청 URL|출력타입|인증여부
|---|---|---|---|
|GET|http://localhost:8080/api/region/desc|JSON|Yes|

###### :pushpin: 요청변수(Request Parameter)
|요청변수|타입|필수여부|설명
|---|---|---|---|
|size|string|Y|출력개수|

###### :pushpin: 요청예시
```
curl -X GET http://localhost:8080/api/region/desc?size={출력개수}
-H "Authorization: {인증토큰}"
```

###### :pushpin: 응답(Response)
```json
{
    "timestamp": "2019년 12월 19일 목요일 14시 17분 29초",
    "path": "/api/region/desc",
    "status": 200,
    "data": [
        {
            "region": "경기도"
        },
        {
            "region": "제주도"
        },
        {
            "region": "국토교통부"
        }
    ]
}
```
<br />

##### :bell: 이차보전 컬럼에서 보전 비율이 가장 작은 추천 기관명

###### :pushpin: 기본정보
|Method|요청 URL|출력타입|인증여부
|---|---|---|---|
|GET|http://localhost:8080/api/region/min-rate|JSON|Yes|


###### :pushpin: 요청예시
```
curl -X GET http://localhost:8080/api/region/min-rate
-H "Authorization: {인증토큰}"
```

###### :pushpin: 응답(Response)
```json
{
    "timestamp": "2019년 12월 19일 목요일 14시 18분 53초",
    "path": "/api/region/min-rate",
    "status": 200,
    "data": {
        "institute": "안양상공회의소"
    }
}
```

### :memo: 문제해결방법
##### A.동일한 요청 정보에 따른 캐시 구현
+ 지자체 목록 조회시 리턴된 데이터를 저장하여 캐시
+ 지자체명 입력에 따른 지자체 정보 조회시 리턴된 데이터를 저장하여 캐시
+ 지원금액을 내림차순으로 정렬한 지자체 정보를 저장하여 캐시
+ 이차보전의 비율이 가장 낮은 추천기관 조회시 정보를 저장하여 캐시
+ 지자체 정보를 등록, 수정, 삭제시 캐시되어 있는 정보를 초기화

```java 
@Component
public class RegionCacheMemory {
	
	private HashMap<String, Object> cacheMap = new HashMap<>();
	
	public void insert(String key, Object data) {
		cacheMap.put(key, data);
	}
	
	public Object findByKey(String key) {
		if(cacheMap.containsKey(key)) {
			return cacheMap.get(key);
		}
		return null;
	}
	
	public void clear() {
		cacheMap.clear();
	}
}
```

##### B.저장소를 통해 지역정보를 가져올시 N+1 문제를 패치 조인으로 변경
+ 지자체 정보와 지원정보에 대한 엔티티를 별도로 분리
+ 이 두개의 엔티티의 관계는 일대일 관계의 양방향 맵핑으로 구현
+ 지원정보 저장소에 findAll() 메서드로 조회시 N+1 문제 발생
+ JPQL 패치조인을 사용하여 JOIN 쿼리로 변경

```java 
@Repository
public interface RegionDetailRepository extends JpaRepository<RegionDetail, Long> {

	RegionDetail findByRegion(Region region);
	
	@Query("select r from RegionDetail r join fetch r.region")
	List<RegionDetail> findAllJoinFetch();
}
```

### :memo: 개발 프레임 워크
+ 스프링 부트 : org.springframework.boot
+ 스프링 JPA : org.springframework.boot:spring-boot-starter-data-jpa 
+ Gradle : 오픈 소스 프레임워크 빌드 도구로 사용
+ 스프링 시큐리티 : org.springframework.boot:spring-boot-starter-security
+ 스프링 부트 웹 : org.springframework.boot:spring-boot-starter-web
+ 스프링 부트 테스트 : org.springframework.boot:spring-boot-starter-test
+ 스프링 부트 개발 툴 : org.springframework.boot:spring-boot-devtools
+ junit-juipter : org.junit.jupiter:junit-jupiter:5.5.2
+ json Web Token : io.jsonwebtoken:jjwt:0.9.1
+ lombok : org.projectlombok:lombok
+ h2DataBase : com.h2database:h2
+ openCsv : com.opencsv
### :memo: 빌드 및 실행방법

##### :mega: 다운로드 :

##### :mega: 실행방법 :
```
java -jar -Dfile.encoding=UTF-8 spring-boot-service-app-0.0.1-SNAPSHOT.jar
```
__________________
