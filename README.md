# insta-backend
- 반려동물 전용 SNS

## 리팩토링
팀 프로젝트 종료 후 리팩토링을 통해 프로젝트 개선 작업을 진행합니다. 
- 컨벤션, 깃 브랜치 전략 등을 정하며 앞으로 진행할 리팩토링 기본 규칙을 세운다.
  - [컨벤션 정리](https://github.com/wisdom08/insta-backend/wiki/my-conventions)
- TDD 클린코드 과정에서 학습한 내용을 프로젝트에 반영한다. 
- SonarCloud 적용
- 기간
  - 2022.09.24 ~ 


--- 

## 팀 프로젝트
- 백엔드
  - 곽대우 https://github.com/dae-woo
  - 변지혜 https://github.com/wisdom08
  - 조상우 https://github.com/csw96
- 개발 기간
  - 2022.08.19 ~ 2022.08.25
## 트러블 슈팅 정리
[트러블슈팅 정리용 위키](https://github.com/wisdom08/insta-backend/wiki)

## 전체 기능
> 구현한 기능에 대한 좀 더 상세한 내용은 [이슈 탭](https://github.com/wisdom08/insta-backend/issues)을 확인해주세요.
- 인증
- 게시판 CRUD
- AWS S3 이용 이미지 처리
- 댓글/대댓글 CRD
- 해시태그 기능
- 좋아요 기능
- 무한 스크롤(slice)
- 유저 아이디 별 작성한 글 조회
- 로그인한 유저가 좋아요 한 글 조회
- 내 정보 수정
  - 소개, 프로필사진

## API
https://insta-backend5.herokuapp.com/swagger-ui/

## 백엔드 배포
~~https://insta-backend5.herokuapp.com/~~
- 비용 문제로 서버는 2022년 11월 5일에 내렸습니다.

## 개발 환경
- Intellij IDEA Ultimate
- Spring Boot 2.7.2
- Java 17
- Gradle 7.5
