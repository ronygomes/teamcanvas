# FROM maven:3.8.6-openjdk-11-slim AS builder
# WORKDIR /teamcanvas
# COPY . .

# Wasn't able to build using maven wrapper
# RUN mvn package

FROM payara/server-full:5.2022.2-jdk11

USER root
RUN apt update && apt install -y wget \
    && wget https://repo1.maven.org/maven2/org/postgresql/postgresql/42.3.3/postgresql-42.3.3.jar \
    && chown payara:payara postgresql-42.3.3.jar

USER payara
COPY scripts/post-boot-commands.asadmin $CONFIG_DIR
# COPY --from=builder /teamcanvas/teamcanvas-app/target/teamcanvas.ear /tmp
COPY ./teamcanvas-app/target/teamcanvas.ear $DEPLOY_DIR
