# multiBoard
### 프로젝트 설명
- 멀티 게시판 사이트  
  : 6개월의 훈련 수료 기간 동안 배운 것을 잊지 않기 위해 복습 겸 웹 개발의 기본이라고 생각하는 게시판을 만들었습니다.  
  : 게시판을 필요할 때마다 생성하기에는 불편할 거라 판단하여 설정 게시판을 만들었습니다.
### 제작과정
- 프로젝트명: multiBoard
- 제작 기간: 2020년 04월~ 06월
- 프로젝트 인원: 1명
### 개발환경
- __언어__ JAVA 8 {JSP, MVC2}
- __프레임워크__ Spring Tool Suite 4.3.23
- __Data Base__ Oracle
- __View&Event__ HTML / CSS / JavaScript / JQuery / bootstrap
### DB 설계
- 테이블 마다 기본 키 설정(Sequence: tableName_keyField_Seq)
<img width="900" alt="db" src="https://user-images.githubusercontent.com/59866253/83393192-5551d300-a431-11ea-82b7-bcf9e5985105.png">

<details>
  <summary>기능 설명</summary>
  <div>
    
 ##### 회원 가입, 수정, 탈퇴
      form으로 받은 데이터를 컨트롤러 전송 후 db에 추가하였습니다.
      id와 email에는 keyup 이벤트로 AJAX를 호출해 id와 email 입력 시 db에 중복이 있는지 판단하도록 구현하였습니다.
      주소에는 daum 우편 서비스, 생일에는 datepicker를 사용하였습니다.
      hibernate-validator를 사용하여 필수 항목을 입력하지 않으면 오류 메세지를 옆에 띄우도록 하였습니다.
      
      회원 가입과 수정은 같은 URL을 사용합니다. idx의 유무에 따라 동작합니다.
      탈퇴는 매일 탈퇴일이 7일이 넘은 유저를 삭제하는 Scheduler에 의해 7일 후 삭제됩니다.
      재접속 시 login후 이동하는 URL("/")로 이동해 탈퇴일이 !null이면 null을 집어넣음으로써 탈퇴 취소가 됩니다.
##### Security
      Security의 URL 접근 제한과 인증만을 사용했습니다. 
      
      게시판 설정(level)에 따라 security의 tags를 이용해 권한이 없으면 해당 내용이 보이지 않도록 설정했습니다.
      (글/댓글쓰기 버튼, 게시판 보기)
      
      "/admin/.."으로 시작하는 URL은 ROLE_ADMIN을 가진 유저만 접근 가능합니다.
      (id가 admin이면 Role_ADMIN을 부여했습니다. 게시판의 관리자 이름은 아무 기능도 없습니다.)
##### 회원 정보 찾기
      아이디/비밀번호 찾기 버튼을 누르면 AJAX로 db의 정보와 비교합니다.
      정보가 일치한다면 id 찾기의 경우 alert로 바로 알리고 
      pwd는 임시 비밀번호를 만들어 db의 비밀번호를 업데이트하고 메일을 보냅니다.
##### 게시판 관리
      게시판을 추가할 때 카테고리 지정을 할 수 있고 목록 읽기, 글 읽기, 쓰기, 댓글 작성, 비밀 글 사용 여부, 
      새 글/추천 글에 제한을 줄 수 있습니다.
      회원관리 페이지에서 모든 회원의 정보를 볼 수 있습니다.
##### 게시판
      게시글 객체인 boardVO에 commentList와 fileList를 주입해서 사용했습니다.
      
  </div>
</details>  

### 작동 설정
- 보안이 취약한 사이트니, 중요한 정보를 넣지 마세요! 
- manage3/src/main/resources/ 경로의 db.properties / email.properties을 수정해주세요.
  db.properties의 oracle 계정 id와 pwd, email.properties의 address, pwd를 수정해주시고 gmail이면 host도 gmail 주석으로 변경해주세요.
- manage3/src/main/webapp/WEB-INF/config_sql.sql의 테이블과 시퀀스를 생성해주세요.
- 관리자 아이디는 id: admin / pwd: 1234 입니다.
### contact
- __name__ :윤혜진
- __hp__ :010-2340-6919
- __e-mail__ :nellayur@gmail.com
