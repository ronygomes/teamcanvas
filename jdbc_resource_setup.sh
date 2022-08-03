#!/bin/bash

# /home/$USER/Documents/payara5
PAYARA_HOME=${PAYARA_HOME:-'/opt/payara5'}
POSTGRES_DRIVER_JAR_NAME="postgresql-42.3.3.jar"
POSTGRES_DRIVER_DOWNLOAD_URL="https://repo1.maven.org/maven2/org/postgresql/postgresql/42.3.3/$POSTGRES_DRIVER_JAR_NAME"

POSTGRES_POOL_NAME=${POSTGRES_POOL_NAME:-postgres-pool}
POSTGRES_RESOURCE_NAME=${POSTGRES_RESOURCE_NAME:-jdbc/teamcanvas}

POSTGRES_SERVER=${POSTGRES_SERVER:-localhost}
POSTGRES_DB_NAME=${POSTGRES_DB_NAME:-teamcanvas}

POSTGRES_JDBC_USER=${POSTGRES_JDBC_USER:-postgres}
POSTGRES_JDBC_PASSWORD=${POSTGRES_JDBC_PASSWORD:-secret}
POSTGRES_JDBC_PORT=${POSTGRES_JDBC_PORT:-5432}


if [ ! -d "$PAYARA_HOME" ]; then
  echo "Invalid Directory: $PAYARA_HOME" >&2
  exit 1
fi

ASADMIN_CMD="$PAYARA_HOME/bin/asadmin"

if [ ! -f "$ASADMIN_CMD" ] || ! $ASADMIN_CMD version > /dev/null 2>&1 ; then
  echo "Invalid: $ASADMIN_CMD" >&2
  exit 1
fi

echo $PAYARA_HOME
$ASADMIN_CMD version

if [ ! -f "$PAYARA_HOME/$POSTGRES_DRIVER_JAR_NAME" ]; then
  echo "Downloading Postgres Driver in $PAYARA_HOME" >&2
  wget --directory-prefix="$PAYARA_HOME" $POSTGRES_DRIVER_DOWNLOAD_URL
fi

$ASADMIN_CMD add-library "$PAYARA_HOME/$POSTGRES_DRIVER_JAR_NAME"

# Create docker postgres container using following command
# docker container run --name teamcanvas -p 5432:5432 -d -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=teamcanvas postgres:14.4

$ASADMIN_CMD create-jdbc-connection-pool --datasourceClassname=org.postgresql.ds.PGSimpleDataSource --resType=javax.sql.DataSource "$POSTGRES_POOL_NAME"
$ASADMIN_CMD set resources.jdbc-connection-pool.$POSTGRES_POOL_NAME.property.password=$POSTGRES_JDBC_PASSWORD
$ASADMIN_CMD set resources.jdbc-connection-pool.$POSTGRES_POOL_NAME.property.databaseName=$POSTGRES_DB_NAME
$ASADMIN_CMD set resources.jdbc-connection-pool.$POSTGRES_POOL_NAME.property.serverName=$POSTGRES_SERVER
$ASADMIN_CMD set resources.jdbc-connection-pool.$POSTGRES_POOL_NAME.property.user=$POSTGRES_JDBC_USER
$ASADMIN_CMD set resources.jdbc-connection-pool.$POSTGRES_POOL_NAME.property.portNumber=$POSTGRES_JDBC_PORT

$ASADMIN_CMD ping-connection-pool $POSTGRES_POOL_NAME

$ASADMIN_CMD create-jdbc-resource --enabled=true --poolName=$POSTGRES_POOL_NAME --target=domain "$POSTGRES_RESOURCE_NAME"

# Following lines makes jdbc/local-postgres available in server
$ASADMIN_CMD create-resource-ref --enabled=true --target=server "$POSTGRES_RESOURCE_NAME"
