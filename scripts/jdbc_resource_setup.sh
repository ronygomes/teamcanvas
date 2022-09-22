#!/bin/bash

export PAYARA_HOME=${PAYARA_HOME:-'/opt/payara5'}
export POSTGRES_DRIVER_JAR_NAME="postgresql-42.3.3.jar"
export POSTGRES_DRIVER_DOWNLOAD_URL="https://repo1.maven.org/maven2/org/postgresql/postgresql/42.3.3/$POSTGRES_DRIVER_JAR_NAME"

export POSTGRES_POOL_NAME=${POSTGRES_POOL_NAME:-postgres-pool}
export POSTGRES_RESOURCE_NAME=${POSTGRES_RESOURCE_NAME:-jdbc/teamcanvas}

export POSTGRES_SERVER=${POSTGRES_SERVER:-localhost}
export POSTGRES_DB_NAME=${POSTGRES_DB_NAME:-teamcanvas}

export POSTGRES_JDBC_USER=${POSTGRES_JDBC_USER:-postgres}
export POSTGRES_JDBC_PASSWORD=${POSTGRES_JDBC_PASSWORD:-secret}
export POSTGRES_JDBC_PORT=${POSTGRES_JDBC_PORT:-5432}

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

ASADMIN_CMD="$PAYARA_HOME/bin/asadmin"

if [ ! -f "$ASADMIN_CMD" ] || ! $ASADMIN_CMD version > /dev/null 2>&1 ; then
  echo "Invalid: $ASADMIN_CMD" >&2
  exit 1
fi

echo "Payara Home Path: $PAYARA_HOME" >&2
$ASADMIN_CMD version

if [ ! -f "$PAYARA_HOME/$POSTGRES_DRIVER_JAR_NAME" ]; then
  echo "Downloading Postgres Driver in $PAYARA_HOME" >&2
  wget --directory-prefix="$PAYARA_HOME" $POSTGRES_DRIVER_DOWNLOAD_URL
fi

if ! command -v envsubst &> /dev/null; then
  echo "Command Not Found: 'envsubst'" >&2
  exit 1
fi

TEMP_FILE=$(mktemp /tmp/create-jdbc-resource-XXX.asadmin)
envsubst < $SCRIPT_DIR/create-jdbc-resource.asadmin.template > $TEMP_FILE

echo "Generated JDBC Resource Configuration from template. Reading $TEMP_FILE" >&2

$ASADMIN_CMD < $TEMP_FILE
rm $TEMP_FILE
