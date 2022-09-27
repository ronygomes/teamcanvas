# Reference Documentation
This document holds my observations, reminders and how-to commands, as this project is a long term project
and not regularly maintained.

## Payara bin/asadmin Commands
Following is some noteworthy commands for working with Payara asadmin

```sh
$ bin/asadmin start-domain domain1
Waiting for domain1 to start ..........
Successfully started the domain : domain1
domain  Location: /opt/payara5/glassfish/domains/domain1
Log File: /opt/payara5/glassfish/domains/domain1/logs/server.log
Admin Port: 4848
Command start-domain executed successfully.

$ bin/asadmin list-applications
Nothing to list.
No applications are deployed to this target server.
Command list-applications executed successfully.

$ bin/asadmin list-libraries       
postgresql-42.3.3.jar
Command list-libraries executed successfully.

$ bin/asadmin restart-domain domain1
Successfully restarted the domain
Command restart-domain executed successfully.

$ bin/asadmin list-domains        
domain1 running
Command list-domains executed successfully.

$ bin/asadmin stop-domain           
Waiting for the domain to stop .
Command stop-domain executed successfully.
```
