#!/bin/bash

docker run -it --rm \
    -v $(pwd)/bot:/bot \
    -v $(pwd)/conf.d:/etc/nginx/conf.d \
    -e MYSQL_USERNAME=*** \
    -e MYSQL_PASSWORD=*** \
    -e MYSQL_HOST=*** \
    -e MYSQL_PORT=3306 \
    -e WEBHOOK_ENABLED=true \
    -e WORKING_DIR=/bot/sandbox \
    -e SCRIPT_DIR=/bot/scripts \
    -e TEMPLATE_DIR=/bot/templates \
    -e EMAIL_FROM=*** \
    -e MAILTRAP_API_KEY=*** \
    -e BASE_URL=*** \
    -e SWAGGER_ENABLED=true \
    -e PROFILE=prod \
    -p 5050:80 \
    -p 5051:8080 \
    -p 5052:8182 \
    qvog-bot:1.0
