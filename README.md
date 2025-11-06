## 📖 명언 앱

### 프로젝트 구조 
- mission1
  - controller
    - AppController.java       # 앱 실행 컨트롤러
    - QuoteHandler.java        # 명령어 처리 및 서비스 연결
  - domain
    - Quote.java               # 명언 객체 생성
    - QuoteRepository.java     # 명언 CRUD
  - service
    - QuoteService.java        # 비즈니스 로직 처리
  - utils
    - QuoteValidator.java      # 명언 유효성 검증
  - view
    - InputView.java           # 사용자 입력 처리
    - OutputView.java          # 화면 출력 처리
- test
  - domain
    - QuoteTest.java
    - QuoteRepositoryTest.java
  - service
    - QuoteServiceTest.java
  - controller
    - QuoteHandlerTest.java

### ⚙️ 기능 구현 목록
#### 🧾 입력
- 명령어를 입력받는다.
  - 지원 명령어: 등록, 목록, 삭제?id=<번호>, 수정?id=<번호>, 종료 
- 명언 등록 시 입력 받는다.
  - 명언 내용과 작가를 입력받는다.   

#### 📝 등록
- 명언 등록 시 고유 번호를 자동 생성한다.
  - 등록 시마다 번호가 증가한다. 

#### 🔍 조회
- 전체 명언을 조회할 수 있다.

#### ✏️ 수정
- ID를 기준으로 명언 내용을 수정할 수 있다.
  - `ERROR` : 존재하지 않는 ID를 입력하면 메시지를 출력한다.
  
#### ❌ 삭제
- ID를 기준으로 명언을 삭제할 수 있다.
  - `ERROR` : 존재하지 않는 ID를 입력하면 메시지를 출력한다.
