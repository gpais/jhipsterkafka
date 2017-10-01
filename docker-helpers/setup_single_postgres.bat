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
docker run --name postgresDB -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -d postgres%version%

docker exec -it -e PGPASSWORD=password postgresDB psql -U postgres  -c "CREATE DATABASE GATEWAY"
docker exec -it -e PGPASSWORD=password postgresDB psql -U postgres -c "CREATE USER GATEWAY; GRANT ALL PRIVILEGES ON DATABASE GATEWAY TO GATEWAY; ALTER USER GATEWAY WITH PASSWORD 'password'; "

docker exec -it -e PGPASSWORD=password postgresDB psql -U postgres  -c "CREATE DATABASE transactionreceiver"
docker exec -it -e PGPASSWORD=password postgresDB psql -U postgres -c "CREATE USER transactionreceiver; GRANT ALL PRIVILEGES ON DATABASE transactionreceiver TO transactionreceiver; ALTER USER transactionreceiver WITH PASSWORD 'password'; "


docker exec -it -e PGPASSWORD=password postgresDB psql -U postgres  -c "CREATE DATABASE transactionsender"
docker exec -it -e PGPASSWORD=password postgresDB psql -U postgres -c "CREATE USER transactionsender; GRANT ALL PRIVILEGES ON DATABASE transactionsender TO transactionsender; ALTER USER transactionsender WITH PASSWORD 'password'; "


pause