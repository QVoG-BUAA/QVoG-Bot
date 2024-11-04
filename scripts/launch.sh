#!/bin/bash

bash /root/apache-tinkerpop-gremlin-server-3.7.0/bin/gremlin-server.sh start
java -Duser.timezone=Asia/Shanghai -jar /bot/application.jar --spring.profiles.active=prod
