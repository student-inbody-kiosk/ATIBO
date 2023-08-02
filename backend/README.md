# backend

This template should help get you started running Django 4.2 server.

## How to run Django Server

1. Django 프로젝트 루트 경로 이동

   ```bash
   cd /backend
   ```

2. 가상환경 생성 및 실행

   ```bash
   # Windows
   python -m venv venv
   source venv/Scripts/activate

   # MacOS
   python3 -m venv venv
   source venv/bin/activate
   ```

3. 파이썬 필요 패키지 설치

   ```bash
   pip install -r requirements.txt

   # MacOS 필요 명령어
   brew install pkg-config
   ```

4. `.env` 파일 생성

   파일 경로: `/backend`

   ```
   DEBUG=
   SECRET_KEY=
   PRIMARY_PASSWORD_HASHER=

   EMAIL_HOST=
   EMAIL_PORT=
   EMAIL_HOST_USER=
   EMAIL_HOST_PASSWORD=
   ```

5. `mysql.cnf` 파일 생성 (backend/atibo)

   파일 경로: `/backend/atibo`

   ```
   [client]
   database = atibo
   user = {MySQL 아이디}
   password = {mysql 패스워드}
   host = {MySQL IP주소}
   port = {MySQL port번호}
   default-character-set = utf8
   ```

6. 데이터베이스 생성

   ```sql
   CREATE DATABASE atibo CHARACTER SET utf8;
   ```

7. 데이터베이스 테이블 생성

   ```bash
   # Windows
   python manage.py migrate

   # MacOS
   python3 manage.py migrate
   ```

8. 기본 계정 데이터 레코드 생성

   ```bash
   # Windows
   python manage.py loaddata accounts.json

   # MacOS
   python3 manage.py loaddata accounts.json
   ```

9. 개발서버 실행

   ```bash
   # Windows
   python manage.py runserver

   # MacOS
   python3 manage.py runserver
   ```

10. 관리자 계정 정보

    ```
    admin
    - username: admin
    - email : ''
    - password: 1q2w3e4r!
    ```
