FROM mongo

LABEL maintainer="frederic@fabre.io"

ARG mongodb_username
ARG mongodb_password
ARG mongodb_database

COPY script /docker-entrypoint-initdb.d/

RUN sed -i -e "s/MONGODB_USERNAME/$mongodb_username/g" /docker-entrypoint-initdb.d/setup.js
RUN sed -i -e "s/MONGODB_PASSWORD/$mongodb_password/g" /docker-entrypoint-initdb.d/setup.js
RUN sed -i -e "s/MONGODB_DATABASE/$mongodb_database/g" /docker-entrypoint-initdb.d/setup.js

EXPOSE 27017 28017