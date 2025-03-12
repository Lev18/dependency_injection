#!/bin/bash

dir=./bin
if [ ! -d $dir ]
then
    mkdir $dir
else
    javac -proc:full -cp "/home/levon/.m2/repository/org/ow2/asm/asm/9.7.1/asm-9.7.1.jar:/home/levon/.m2/repository/org/javassist/javassist/3.28.0-GA/javassist-3.28.0-GA.jar:/home/levon/.m2/repository/org/projectlombok/lombok/1.18.36/lombok-1.18.36.jar:/home/levon/.m2/repository/org/reflections/reflections/0.10.2/reflections-0.10.2.jar:/home/levon/.m2/repository/cglib/cglib/3.2.9/cglib-3.2.9.jar:/home/levon/.m2/repository/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar:/home/levon/.m2/repository/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar" -d $dir $(find src/main/java -name "*.java")
    java --add-opens java.base/java.lang=ALL-UNNAMED -cp "bin:/home/levon/.m2/repository/org/ow2/asm/asm/9.7.1/asm-9.7.1.jar:/home/levon/.m2/repository/org/javassist/javassist/3.28.0-GA/javassist-3.28.0-GA.jar:home/levon/.m2/repository/org/projectlombok/lombok/1.18.36/lombok-1.18.36.jar:/home/levon/.m2/repository/org/reflections/reflections/0.10.2/reflections-0.10.2.jar:/home/levon/.m2/repository/cglib/cglib/3.2.9/cglib-3.2.9.jar:/home/levon/.m2/repository/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar:/home/levon/.m2/repository/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar" org.example.app.Main |& tee -a out.log
    # java -jar ./target/java-di-1.0-SNAPSHOT.jar |& tee -a out.log
fi
