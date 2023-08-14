# Node.js 기반 이미지
FROM node:18 as build-stage

# Add curl for healthcheck
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
    curl \
    && rm -rf /var/lib/apt/lists/*

# 작업 디렉토리 생성 (상대경로 이용)
WORKDIR /ATIBO
COPY package*.json .
RUN npm install

# 소스코드 복사 (현재 위치, 이미지 위치) 및 빌드
COPY . .
RUN npm run build-only

# Nginx 기반 이미지
FROM nginx:alpine

# 이전에 빌드한 애플리케이션 Nginx 웹 서버에 복사
COPY --from=build-stage /ATIBO/dist /usr/share/nginx/html

# 기본 nginx.conf 파일 삭제
RUN rm /etc/nginx/conf.d/default.conf

# 커스텀 nginx.conf 복사
COPY nginx.conf /etc/nginx/conf.d/

# 80번 포트로 노출
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]