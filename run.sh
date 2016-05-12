#!/bin/sh

mvn clean install docker:build -Dmaven.test.skip=true

docker-compose -f docker-compose.dev.yml up