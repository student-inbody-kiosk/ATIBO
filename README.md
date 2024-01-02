# 실행 가이드

ATIBO 웹 서비스는 도커 엔진만 설치되어 있다면, 어느 컴퓨터에서든지 간편하게 다운받아 직접 서비스할 수 있습니다.

<img src="./assets/images/atibo-architecture.png" alt="atibo-architecture.png" width="600"/>

## 1. 사전 준비

- [Docker Engine](https://docs.docker.com/engine/install/) 설치

- [Docker Compose](https://docs.docker.com/compose/install/) 설치

## 2. 다운로드

ATIBO를 실행하기 위해 필요한 파일들을 다운로드 합니다.

1. `curl` 명령어가 설치되어 있는지 확인합니다.
   ```bash
   curl -V
   ```
2. `curl` 명령어가 없을 경우 아래와 같이 설치합니다.
   ```bash
   # Linux / MacOS
   apt-get update
   apt-get install curl
   ```
   ```bash
   # Windows, 아래 링크를 통해 설치
   https://curl.se/windows/
   ```
3. 필요한 파일들을 다운로드 할 폴더로 이동합니다. 추천하는 폴더 경로는 `/opt` 입니다.

   ```bash
   cd /opt
   ```

4. 아래 명령어로 필요한 파일들을 다운로드 합니다. 명령어의 상세 내용은 [여기](https://github.com/student-inbody-kiosk/ATIBO/blob/gh-pages/download_atibo_docker_compose.sh)에서 확인할 수 있습니다.

   ```bash
   curl https://student-inbody-kiosk.github.io/ATIBO/download_atibo_docker_compose.sh | bash
   ```

5. 명령어가 성공적으로 실행되었다면 아래와 같은 폴더구조가 만들어집니다.
   ```
   /opt/atibo
           ├── docker-compose.yml
           ├── .env
           └── nginx
               ├── Dockerfile
               └── nginx.conf
   ```

## 3. 환경변수 설정

`.env`파일을 통해 ATIBO 서비스의 환경변수를 설정할 수 있습니다.

- **[필수]** 서비스를 제공할 현재 컴퓨터의 IP주소(`DOMAIN_OR_PUBLIC_IP`)와 포트 번호(`PORT`)를 입력합니다. 사용하려는 포트는 방화벽 설정에서 열어둬야 합니다.
- **[권장]** 보안을 위해 데이터베이스 비밀번호(`DB_PASSWORD`) 수정합니다.
- **[권장]** 비밀번호 초기화 기능을 이용하려면, 사용자에게 새 비밀번호를 전송할 이메일 정보를 기입합니다. 단, SMTP을 통한 원격 연결이 허용된 이메일이어야 합니다

  ```python
  # 웹페이지를 제공할 로컬 서버의 IP 주소와 Port 번호로 수정
  # 예시: 198.51.100.1 혹은 atibo.example.com
  DOMAIN_OR_PUBLIC_IP=127.0.0.1
  PORT=9005

  # 보안을 위해 데이터베이스 비밀번호 변경을 권장
  DB_PASSWORD=atibo-project-123

  DB_NAME=atibo
  DB_USER=root
  DB_HOST=atibo-db
  DB_PORT=3306

  # 비밀번호 초기화 기능을 위한 이메일 정보(EMAIL_HOST_USER, EMAIL_HOST_PASSWORD) 입력을 권장
  # 단, SMTP을 통한 연결이 허용된 이메일 정보
  EMAIL_HOST_USER=
  EMAIL_HOST_PASSWORD=
  EMAIL_HOST=smtp.gmail.com
  EMAIL_PORT=587
  ```

## 4. 서비스 실행

docker-compose.yml 파일이 있는 위치에서 아래 명령어를 실행합니다.

```bash
docker compose -p atibo up -d
```

## 5. 서비스 종료

- 서비스를 일시적으로 멈추려면 아래 명령어를 실행합니다.

```bash
docker compose -p atibo stop
```

- 서비스를 삭제하려면 아래 명령어를 실행합니다. **모든 데이터가 백업되지 않고 사라집니다.**

```bash
docker compose -p atibo down -v
```
