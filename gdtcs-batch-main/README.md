## Spring Batch 
- 차량번호를 기준으로 SOAP API 호출하여 결과 정보를 DB에 저장하는 배치 입니다. 

## Build, Execution, Deployment > Build Tools > Gradle

* Build and Run tab
    * Build and run using: IntelliJ Idea (check)
    * Run tests using:  IntelliJ Idea (check)

## Environments

- Eclipse Temurin OpenJDK 17
  - [AdoptOpenJDK](https://adoptopenjdk.net/)
  - https://adoptium.net/temurin/releases/
- Spring Boot 3.2.5
- Spring Batch Core 5.1
- DB Oracle

## Application Environment
- `gpkiapi.lic` 라이센스 파일은 `conf` 폴더에 위치해야 한다.
  - 개발은 임시 라이센스 발급받아 진행
    - 라이센스 개발받은 일로부터 `2개월` 후에 다시 발급 받아야 한다.
  - 운영은 라이센스 발급받아 사용
    - 설치된 서버 IP가 변경될 경우 라이센스 재발급 받아야 한다.
- application.properties 파일에 공통으로 사용할 변수들을 정의
- application.yaml 파일에 환경별로 사용할 변수들을 정의
  - application-prod.yaml (운영환경)
- 배치실행시에 사용할 환경변수를 설정
  - `-Dspring.profiles.active=prod (운영환경)`
  - `실행 파라미터에 --job.name=jobName 으로 실행할 배치를 지정`
- dll 파일 아래 경로에 복사해야 된다.
  - `C:\Windows\System32`
    - **gpkiapi.dll**
    - **LDAPSDK.DLL**
- jar 파일에 생성시에 인증서 파일은 포함하지 않는다.
- 면제여부 업데이트 우선순위
  - **다자녀 > 유공자 > 장애인 > 전기 > 수소**

<a name="jvm"></a>

## Installing the JVM in Local Environment

* 터미널로 ./gradlew test 와 같은 실행을 위해서 로컬 환경에 idea 환경과 같은 jvm이 필요합니다.
* 이 부분은 개발하시는분들 마다 다르게 하셔도 됩니다.

```bash
> /usr/libexec/java_home -V 로 설치된 java 확인(없을 수 있음)
# 17이 설치되지 않은 경우
> brew tap AdoptOpenJDK/openjdk
> brew install --cask adoptopenjdk17
.bashrc 나 .zshrc 에 경로 추가
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/sbin:$JAVA_HOME"

source .zshrc 후 java 버전 확인
> java --version
```
