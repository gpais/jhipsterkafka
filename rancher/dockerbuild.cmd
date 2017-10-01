@echo off

setlocal enabledelayedexpansion
set machine=%1
if "%machine%" == "" (
    set machine=default
)
set version=%2
if "%version%" == "" (
    set version=:latest
)
echo Setting env
@FOR /f "tokens=*" %%i IN ('docker-machine env %machine%') DO @%%i

cd ../gateway
CALL mvnw -Pprod -DskipTests package dockerfile:build
cd ../cards
CALL mvnw -Pprod -DskipTests package dockerfile:build
cd ../transaction
CALL mvnw -Pprod -DskipTests package dockerfile:build
cd ../uaa
CALL mvnw -Pprod -DskipTests package dockerfile:build
pause

