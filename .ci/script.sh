#!/bin/bash
 export MAVEN_OPTS="-Xmx1G -Xms128m"
mvn test -Dspring.profiles.active=staging
