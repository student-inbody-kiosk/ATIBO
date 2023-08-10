# Using official nginx base image
FROM nginx:alpine

# Delete default nginx.conf
RUN rm /etc/nginx/conf.d/default.conf

# Add custom nginx.conf
COPY nginx.conf /etc/nginx/conf.d/

CMD ["nginx", "-g", "daemon off;"]