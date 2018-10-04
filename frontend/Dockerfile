FROM nginx:alpine

LABEL maintainer="frederic@fabre.io"

COPY nginx.conf /etc/nginx/nginx.conf

WORKDIR /usr/share/nginx/html
COPY weather-dashboard/dist/ .
