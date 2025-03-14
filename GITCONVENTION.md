## gitConvention

### 기본적인 커밋 메시지 구조
각 파트는 빈줄로 구분한다.
> 제목 (Type: Subject)  
> 
> 본문 (Body)  
> 
> 꼬리말 (Footer)

### Commit Type
| 타입 이름 | 내용                                    |
|-----------|-----------------------------------------|
| feat      | 새로운 기능에 대한 커밋                 |
| fix       | 버그 수정에 대한 커밋                   |
| build     | 빌드 관련 파일 수정 / 모듈 설치 또는 삭제에 대한 커밋 |
| chore     | 그 외 자잘한 수정에 대한 커밋           |
| ci        | CI 관련 설정 수정에 대한 커밋           |
| docs      | 문서 수정에 대한 커밋                   |
| style     | 코드 스타일 혹은 포맷 등에 관한 커밋    |
| refactor  | 코드 리팩토링에 대한 커밋               |
| test      | 테스트 코드 수정에 대한 커밋            |
| perf      | 성능 개선에 대한 커밋                   |

### Subject
- 제목은 50글자 이내로 작성한다.
- 첫글자는 대문자로 작성한다.
- 마침표 및 특수기호는 사용하지 않는다.
- 영문으로 작성하는 경우 동사(원형)을 가장 앞에 명령어로 작성한다.
- 과거시제는 사용하지 않는다.
- 간결하고 요점적으로 즉, 개조식 구문으로 작성한다.

### Body
- 72이내로 작성한다.
- 최대한 상세히 작성한다. (코드 변경의 이유를 명확히 작성할수록 좋다)
- 어떻게 변경했는지보다 무엇을, 왜 변경했는지 작성한다.

### Footer ``` 선택사항 ```

- issue tracker ID 명시하고 싶은 경우에 작성한다.
- 유형: #이슈 번호 형식으로 작성한다.
- 여러 개의 이슈번호는 쉼표(,)로 구분한다.

이슈 트래커 유형은 다음 중 하나를 사용한다.  
1. Fixes: 이슈 수정중 (아직 해결되지 않은 경우)  
2. Resolves: 이슈를 해결했을 때 사용  
3. Ref: 참고할 이슈가 있을 때 사용  
4. Related to: 해당 커밋에 관련된 이슈번호 (아직 해결되지 않은 경우)  

