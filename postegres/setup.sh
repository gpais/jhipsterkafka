#!/bin/bash
# wait-for-postgres.sh

set -o xtrace

host="$1"
shift
cmd="$@"

until psql -h "$host" -U "postgres" -c '\l'; do
  echo "Postgres is unavailable - sleeping"
  sleep 1
done

echo "Postgres is up - executing command"
exec $DOCKER_STARTUP_COMMAND