# Deployment instructions

ATIBO is deployed in production as a set of **Docker** containers managed with **Docker Compose**.
You can deploy ATIBO in any modern Linux distribution.

<img src="./assets/images/atibo-architecture.png" alt="atibo-architecture.png" width="600"/>

## 1. Prerequisites

- Install [Docker](https://docs.docker.com/engine/install/)

- Install [Docker Compose](https://docs.docker.com/compose/install/)

- Open the port you want to use in the firewall settings

## 2. Download

Download the files for running the Atibo Docker Compose.

1. Make sure the curl library is installed
   ```bash
   apt-get update
   apt-get install curl
   ```
2. Go to the folder where you want to download the files. The recommended folder to install ATIBO is `/opt`

   ```bash
   cd /opt
   ```

3. Execute the following command to download and run the installation script. The full code of the script can be found [Here](https://github.com/student-inbody-kiosk/ATIBO/blob/gh-pages/download_atibo_docker_compose.sh).

   ```bash
   curl https://student-inbody-kiosk.github.io/ATIBO/download_atibo_docker_compose.sh | bash
   ```

4. When the script runs successfully, the following directory structure is created.
   ```
   /opt/atibo
           ├── docker-compose.yml
           ├── .env
           └── nginx
               ├── Dockerfile
               └── nginx.conf
   ```

## 3. Configuration

ATIBO Platform configuration is specified in the .env file with environment variables.

- You must give a value to properties `DOMAIN_OR_PUBLIC_IP` and `PORT`, where you want to host the service.
- You can change `DB_PASSWORD` for security
- If you enter your email information, you can use the password reset service. However, it must be an email with remote access.

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

## 4. Execution

To start ATIBO you can execute this command in the directory where the dockerfile file is located

```bash
docker compose -p atibo up -d
```

## 5. Termination

- To stop ATIBO temporarily.

```bash
docker compose -p atibo stop
```

- To delete ATIBO permanently. All data and storage space will be empty

```bash
docker compose -p atibo down -v
```
