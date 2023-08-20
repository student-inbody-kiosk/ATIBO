<div align="center">

# ATIBO <!-- omit in toc -->

<img src="./assets/images/atibo-1980-1080.png" alt="atibo-1980-1080.png" width="600"/>

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

</div>

## 🤸‍♂️ 소개

## 🌎 다운로드 및 설치

ATIBO 웹 서비스는 총 4개의 Docker 컨테이너로 구성되어 있으며 Docker Compose로 관리할 수 있습니다. ATIBO의 모든 컨테이너는 Linux OS를 바탕으로 설계되었지만, Docker Engine이 실처되어 있다면 Windows / MacOS 환경에서도 실행할 수 있습니다.

<img src="./assets/images/atibo-architecture.png" alt="atibo-architecture.png" width="700"/>

### 사전 준비

- [Docker](https://docs.docker.com/engine/install/) 설치
- [Docker Compose](https://docs.docker.com/compose/install/) 설치
- ATIBO 웹 서비스를 호스팅하기 위해 사용할 포트를 방화벽 설정에서 열어둡니다

### 파일 다운로드

ATIBO의 Docker Compose를 실행하기 위해 필요한 파일들을 다운로드 합니다.

1. `curl`라이브러리가 설치되어 있는지 확인합니다.
   ```bash
   apt-get update
   apt-get install curl
   ```
2. 파일들을 다운로드할 디렉토리로 이동합니다. 추천하는 디렉토리는 `/opt`입니다

   ```bash
   cd /opt
   ```

3. 아래 명령어를 실행시켜 필요한 파일들을 다운로드 할 수 있습니다. 명령어의 상세 코드는 [여기](https://github.com/student-inbody-kiosk/ATIBO/blob/gh-pages/download_atibo_docker_compose.sh)에서 확인할 수 있습니다.

   ```bash
   curl https://student-inbody-kiosk.github.io/ATIBO/download_atibo_docker_compose.sh | bash
   ```

4. 명령어가 성공적으로 실행되었다면 아래와 같은 파일구조가 만들어집니다.
   ```
   /opt/atibo
           ├── docker-compose.yml
           ├── .env
           └── nginx
               ├── Dockerfile
               └── nginx.conf
   ```

### 서비스 설정

ATIBO에 대한 상세 설정은 `.env` 파일의 환경변수로 관리됩니다.

- **반드시 ATIBO 웹서비스를 제공할 컴퓨터의 공인 IP주소(`DOMAIN_OR_PUBLIC_IP`)와 포트 번호(`PORT`)를 입력해주세요**
- 보안을 위해 데이터베이스 비밀번호(`DB_PASSWORD`)를 수정해주세요
- 이메일을 통한 비밀번호 초기화 기능을 이용하기 위해서는 이메일(`EMAIL_HOST_USER`)과 이메일 비밀번호(`EMAIL_HOST_PASSWORD`)를 입력해주세요. 단, 원격 접속이 가능한 이메일이어야 합니다.

```python
# Domain name. If you do not have one, the public IP of the machine.
# For example: 198.51.100.1, or openvidu.example.com
DOMAIN_OR_PUBLIC_IP=127.0.0.1
PORT=9005

# DataBase
# Modify the password for security
DB_PASSWORD=atibo-project-123
DB_NAME=atibo
DB_USER=root
DB_HOST=atibo-db
DB_PORT=3306

# Email host
# To use password initialization, enter remote accessible email information
# https://integer-ji.tistory.com/274
EMAIL_HOST_USER=
EMAIL_HOST_PASSWORD=
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
```

### 서비스 실행

ATIBO를 시작하려면, `docker-compose.yml`파일이 있는 위치에서 아래 명령어를 실행시킵니다

```bash
cd /opt/atibo
docker compose -p atibo up -d
```

### 서비스 중지

- ATIBO를 일시적으로 멈추려면, 아래 명령어를 실행시킵니다.

  ```bash
  docker compose -p atibo stop
  ```

- ATIBO를 삭제하려면, 아래 명령어를 실행시킵니다. 모든 데이터와 저장공간이 백업되지 않고 사라집니다.

  ```bash
  docker compose -p atibo down -v
  ```

## 📌 사용 가이드

## 💻 화면 예시

<img src="./assets/images/atibo-mockup-2.png" alt="atibo-mockup-2.png" width="600"/>

## 😄 컨트리뷰터

<a href="https://github.com/student-inbody-kiosk/ATIBO/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=student-inbody-kiosk/ATIBO" />
</a>
