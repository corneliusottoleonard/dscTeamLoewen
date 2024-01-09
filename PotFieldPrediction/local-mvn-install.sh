#! /bin/bash

mvn install:install-file \
    -Dfile=target/PotFieldPrediction-1.0.jar \
    -DpomFile=./pom.xml