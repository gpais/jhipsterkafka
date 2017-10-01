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
CALL mvnw -Pprod -DskipTests clean compile install dockerfile:build
CALL docker push gpaishubid/gateway
cd ../transactionreceiver
CALL mvnw -Pprod -DskipTests clean compile install dockerfile:build
CALL docker push gpaishubid/transactionreceiver
CALL docker push gpaishubid/transactionreceiver
cd ../transactionsender
CALL mvnw -Pprod -DskipTests clean compile install dockerfile:build
CALL docker push gpaishubid/transactionsender
cd ../uaa
CALL mvnw -Pprod -DskipTests clean compile install dockerfile:build
CALL docker push gpaishubid/uaa
pause

