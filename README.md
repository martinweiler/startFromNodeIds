# Test project to verify the processClient.startProcessFromNodeIds using KieClient API

## Prerequisites:

* Install BAMOE 8.0.3 (kie-server is sufficient)
* Install the BAMOE 8.0.3 maven repo
* Configure the BAMOE instance to use an external DB (eg. postgres)
* Create user rhpamAdmin:password1!
```
$ ./bin/jboss-cli.sh --commands="embed-server --std-out=echo,/subsystem=elytron/filesystem-realm=ApplicationRealm:add-identity(identity=rhpamAdmin),/subsystem=elytron/filesystem-realm=ApplicationRealm:set-password(identity=rhpamAdmin, clear={password=password1\!}),/subsystem=elytron/filesystem-realm=ApplicationRealm:add-identity-attribute(identity=rhpamAdmin, name=role, value=[admin,rest-all,kie-server])"
```
* Start the BAMOE instance

## Build the kjar project
```
$ (cd test-kjar/ && mvn clean install)
```

## Run the kie client project

* Build the project
  ```
  $ (cd kie-server-client/ && mvn clean package)
  ```

* Deploy the KieContainer
  ```
  $ (cd kie-server-client/ && mvn exec:java -Dexec.mainClass=com.sample.CreateContainerTest)
  ```

* Start the HT process test
  ```
  $ (cd kie-server-client/ && mvn exec:java -Dexec.mainClass=com.sample.StartHTProcess)
  ```

This test executes the following steps:
1. Start an instance of the ht_process
2. Claims and completes the first human task (HT1)
3. Aborts the process instance
4. Queries kie-server for the aborted node of this process instance
5. Creates another instance of the process, passing in the node id:
   ```
   About to send POST request to 'http://localhost:8080/kie-server/services/rest/server/containers/test-kjar_1.0.0-SNAPSHOT/processes/ht_process/instances/fromNodes' 
   with payload 
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<process-start-spec>
		<node-id-list>
			<node-id>_44CAB02E-AD59-4FD5-9FCF-776E04BAAC47</node-id>
		</node-id-list>
	</process-start-spec>
   ```
Note: Check the logging output of the kie client project to view all requests sent to the kie-server, including the payload.
