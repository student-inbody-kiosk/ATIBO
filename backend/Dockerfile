# 1st Step: Build django project
# Using official python runtime base image
FROM python:3.9-slim

# Add curl for healthcheck
# pkg-config is required for mysqlclient in Linux OS
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
    curl \
    pkg-config \
    default-mysql-client \
    default-libmysqlclient-dev \
    gcc \
    && rm -rf /var/lib/apt/lists/*

# Set the application directory
WORKDIR /app

# Install requirements.txt
COPY requirements.txt /app/requirements.txt
RUN pip install -r requirements.txt
RUN pip install gunicorn

# Copy source code
COPY . .

# Environmental varables
ENV DB_NAME $DB_NAME
ENV DB_USER $DB_USER
ENV DB_PASSWORD $DB_PASSWORD
ENV DB_HOST $DB_HOST
ENV DB_PORT $DB_PORT
ENV EMAIL_HOST $EMAIL_HOST
ENV EMAIL_PORT $EMAIL_PORT
ENV EMAIL_HOST_USER $EMAIL_HOST_USER
ENV EMAIL_HOST_PASSWORD $DB_HOST_PASSWORD

EXPOSE 8000

# Run startup.sh
CMD ["/bin/bash","-c","./startup.sh"]