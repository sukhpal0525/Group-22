#!/bin/bash
# SCP Command: (make sure to mvn clean package install)
# scp ./target/ecommerce-0.0.1-SNAPSHOT.jar u-200045962@cs2410-web01pvm.aston.ac.uk:./

java -jar -Dspring.profiles.active=aston ecommerce-0.0.1-SNAPSHOT.jar

java -jar -Dspring.profiles.active=aston -Dserver.port=10293 ecommerce-0.0.1-SNAPSHOT.jar