# Team Canvas

A project management application

### Run
This project was created using Java 7 in 2013. Bulding with Java 7 requires https.protocols=TLSv1.2

```sh
$ mvn -Dhttps.protocols=TLSv1.2 package
```

* https://stackoverflow.com/questions/50824789why-am-i-getting-received-fatal-alert-protocol-version-or-peer-not-authentic
* Deploy & Undeploy https://www.youtube.com/watch?v=rdft-BpN_t4
* MySQL Resouce - https://www.youtube.com/watch?v=f1z-3zlkXj8

Maven configuration expects glassfish5 is installed in /opt/glassfish5. With user admin and password 12345 and domain name simple
```sh
$ /opt/glassfish5/bin/asadmin create-domain simple
Enter admin user name [Enter to accept default "admin" / no password]> admin
Enter the admin password [Enter to accept default of no password]> 12345
Enter the admin password again> 12345
```

bin/asadmin deploy /Users/manuel/Documents/GitHub/teamcanvas/teamcanvas-app/target/teamcanvas.ear

$ docker run --name teamcanvas -p 3306:3306 -e MYSQL_ROOT_PASSWORD=12345 -e MYSQL_DATABASE=teamcanvas -d mysql:5.7.38

$ docker run --name teamcanvas -P -e MYSQL_ROOT_PASSWORD=12345 -e MYSQL_DATABASE=teamcanvas -d vsamov/mysql-5.1.73

$ docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' teamcanvas
// 172.17.0.2
$ docker run -it --rm mysql:5.7.38 mysql -hdocker.for.mac.localhost -uroot -p
$ docker run -it --rm vsamov/mysql-5.1.73 mysql -hdocker.for.mac.localhost -uroot -p