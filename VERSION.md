# Version Details

This project was run in 2022 using following versions.

```shell
$ java -version

java version "11.0.15.1" 2022-04-22 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.15.1+2-LTS-10)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.15.1+2-LTS-10, mixed mode)
```


```shell
$ bin/asadmin version

Version = Payara Server  5.2022.2 #badassfish (build 306)
```

```shell
$ docker version

Client:
 Version:           20.10.14
 API version:       1.41
 Go version:        go1.16.15
 Git commit:        a224086349
 Built:             Thu Mar 24 17:14:32 2022
 OS/Arch:           linux/amd64
 Context:           default
 Experimental:      true

Server:
 Engine:
  Version:          20.10.14
  API version:      1.41 (minimum version 1.12)
  Go version:       go1.16.15
  Git commit:       87a90dc
  Built:            Thu Mar 24 17:15:03 2022
  OS/Arch:          linux/amd64
  Experimental:     false
 containerd:
  Version:          v1.5.11
  GitCommit:        3df54a852345ae127d1fa3092b95168e4a88e2f8
 runc:
  Version:          1.0.3
  GitCommit:
 docker-init:
  Version:          0.19.0
  GitCommit:        de40ad0


```


```shell
$ docker container run --name teamcanvas -p 5432:5432 -d -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=teamcanvas postgres:14.4

$ export POSTGRES_IP=$(docker container inspect teamcanvas -f '{{ .NetworkSettings.IPAddress }}')
$ docker run -it --rm postgres:14.4 psql -h $POSTGRES_IP -U postgres -d teamcanvas
teamcanvas=# SHOW server_version;
         server_version
--------------------------------
 14.4 (Debian 14.4-1.pgdg110+1)
```