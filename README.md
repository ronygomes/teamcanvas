# Team Canvas

A project management application build using Jakarta Server Faces (JSF), Jakarta Enterprise Beans (EJB). Initial project
was built using Java 7 and Glassfish Server 3 in 2013. Later migrated to Java 11, Jakarta 9 and Payara Server 5. 

# Docker Compose
Assuming Java Development Kit (JDK) version 11 and Docker is installed, run the following command to deploy the application:
```sh
$ ./mvnw clean package
$ docker-compose up
```

Run the following commands to stop the server, prune created volumes and remove downloaded docker images
```sh
$ docker-compose down --volumes --rmi all
```
# Standalone Server
## Payara Server Configuration

Download 'Payara Server 5.x.x (Full)' from [here](https://www.payara.fish/downloads/payara-platform-community-edition/).
Assuming downloaded archive is extracted and moved as `/opt/payara5/`. This location is denoted as '$PAYARA_HOME' in this guide.

For running any application, a domain needs to be created in 'Payara Server'. By default `domain1` is present in fresh install.
Custom domain can be created using following commands. Domain name is denoted as '$DOMAIN_NAME' in this guide.
```sh
$ $PAYARA_HOME/bin/asadmin create-domain simple
Enter admin user name [Enter to accept default "admin" / no password]> admin
Enter the admin password [Enter to accept default of no password]> 12345
Enter the admin password again> 12345
```

Assuming Java 11 is installed and set as `$JAVA_HOME`, run the following command for starting the server:
```sh
$ $PAYARA_HOME/bin/asadmin start-domain $DOMAIN_NAME
```

If multiple Java Development Kit (JDK) is installed, can run like below in Linux. Assuming Java 11 Path is `/usr/lib/jvm/jdk-11.0.15.1/`
```sh
$ JAVA_HOME=/usr/lib/jvm/jdk-11.0.15.1/ $PAYARA_HOME/bin/asadmin start-domain $DOMAIN_NAME

```
or in macOS using `java_home`
```sh
$ JAVA_HOME=$( /usr/libexec/java_home -v 11 ) $PAYARA_HOME/bin/asadmin start-domain $DOMAIN_NAME
```

After running the domain, JNDI resources needs to be configure for connecting to database. Run the following scripts to
create the jdbc resource pool. This script will also install `postgresql-42.3.3.jar` module after downloading from internet.
See details in [jdbc_resource_setup.sh](scripts/jdbc_resource_setup.sh) script.

Java Development Kit (JDK) version 11 needs to be installed and domain must be running.
```sh
$ scripts/jdbc_resource_setup.sh
```

It is possible to override following environment variable in the script:
* **PAYARA_HOME:** Payara Server Installation path (Default `/opt/payara5`)
* **POSTGRES_POOL_NAME:** JDBC Connection Pool name (Default `postgres-pool`)
* **POSTGRES_RESOURCE_NAME:** JDBC Resource name. Changing it will require code change (Default `jdbc/teamcanvas`)
* **POSTGRES_SERVER:** Database Server Name (Default: `localhost`)
* **POSTGRES_DB_NAME:** Database name (Default: `teamcanvas`)
* **POSTGRES_JDBC_USER:** Database user name (Default: `postgres`) 
* **POSTGRES_JDBC_PASSWORD:** Database password (Default: `secret`) 
* **POSTGRES_JDBC_PORT:** Database port (Default: `5432` which is default postgresql port)

Run like below to override variables:
```sh
$ POSTGRES_POOL_NAME=demo-pool POSTGRES_JDBC_PASSWORD=supersecret scripts/jdbc_resource_setup.sh
```

## Database Configuration

This project was developed using `PostgreSQL 14.4` using docker container. You can install PostgreSQL natively but this
guide installs as docker container. Run following command for creating a postgres container with password 'secret'
and database name 'teamcanvas'.

```sh
$ docker container run -d --name teamcanvas -p 5432:5432 -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=teamcanvas -e POSTGRES_USER=postgres postgres:14.4
```

You can access the database from terminal using following command:
```sh
$ POSTGRES_SERVER_IP=$( docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' teamcanvas )
$ docker run -it --rm postgres:14.4 psql -h $POSTGRES_SERVER_IP -U postgres
```

JPA configuration is stored in following file `$PROJECT_ROOT/teamcanvas-ejb/src/main/resources/META-INF/persistence.xml`.
It is configured to automatically execute following files from `$PROJECT_ROOT/teamcanvas-ejb/src/main/resources/META-INF/`:

* **postgres-drop-ddl.sql** - Executes for cleaning up database
* **postgres-create-ddl.sql** - Creates requires tables
* **postgres-data.sql** - Populate initial data

## Build

Clone this project and change directory to project root. It is a standard Maven multi module project with wrapper configured.
You build the project using following command. No need to install Maven but Java 11 needs to be installed:

```$sh
$ ./mvnw clean package
```

If multiple Java Development Kit (JDK) is installed, run like below:
```$sh
$ JAVA_HOME=<Path to Java 11> ./mvnw clean package
```
Final artifact will be generated as `$PROJECT_ROOT/teamcanvas-app/target/teamcanvas.ear`

## Deploy

Use following commands for deploying the generated artifact.
```sh
$ bin/asadmin deploy --name teamcanvas $PROJECT_ROOT/teamcanvas-app/target/teamcanvas.ear
```

It is possible to undeploy and redeploy using following commands:
```sh
$ bin/asadmin undeploy --name teamcanvas
$ bin/asadmin redeploy --name teamcanvas $PROJECT_ROOT/teamcanvas-app/target/teamcanvas.ear
```
## Log
* **Server Log Path:** $PAYARA_HOME/glassfish/domains/$DOMAIN_NAME/logs/server.log
* **Application Log:** Application logs in both console and `/tmp/teamcanvas.log` file

# Run
Successful deployment will make this application available at `http;//localhost:8080`. By default an user is created with following credential:

* **Email:** john@example.com
* **Password:** 1

# Resource
* [YouTube - Getting Started with Jakarta EE 9 Beginners Series](https://www.youtube.com/watch?v=dl30p1j-Wbw&list=PLFMhxiCgmMR9Yo4p20k4lAJniEYqPsjNA)
* [YouTube - Payara Datasource Video](https://www.youtube.com/watch?v=dl30p1j-Wbw&list=PLFMhxiCgmMR9Yo4p20k4lAJniEYqPsjNA)
