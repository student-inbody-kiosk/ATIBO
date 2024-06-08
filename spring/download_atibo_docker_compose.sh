printf '\n'
printf '\n     ======================================='
printf '\n            Start to download files'
printf '\n     ======================================='
printf '\n'

# create root directory
mkdir atibo
cd atibo

# docker-compose.yml & .env
curl -o docker-compose.yml https://student-inbody-kiosk.github.io/ATIBO/spring/docker-compose.yml;
curl -o .env https://student-inbody-kiosk.github.io/ATIBO/spring/env.txt;

# nginx related files
mkdir nginx
curl -o nginx/Dockerfile https://student-inbody-kiosk.github.io/ATIBO/spring/nginx/Dockerfile;
curl -o nginx/nginx.conf https://student-inbody-kiosk.github.io/ATIBO/spring/nginx/nginx.conf;

printf '\n'
printf '\n     ======================================='
printf '\n             Successfully Downloaded'
printf '\n     ======================================='
printf '\n'
printf "\n     1. Configure DOMAIN_OR_PUBLIC_IP and PORT in .env file"
printf "\n     $ cd atibo"
printf "\n     $ vi .env"
printf '\n'
printf "\n     2. Start docker compose"
printf "\n     $ docker compose -p atibo up -d"
printf '\n'
