
# Global Logistics Internet

## Requirements
- JDK ~> 1.8 / 11 
- Spring Boot ~> 2.5.2
- Maven \>= 3.6.3
- IDE of your choice (e.g Intellij/Eclipse/STS etc)

## Database
- N/A
---

### Project structure
This project consists of 1 deploy-able artefacts:

### Deploy-able
- orange-payment-processor


```
+-- com.orange
+-- .idea
+-- .mvn
|   +-- src/
|   +-- .gitignore
|   +-- pom.xml
+-- .gitignore
+-- pom.xml
+-- README.md
```
---

### Local development
Run the below commands from the project root:
1. Build the project
```
mvn -B  clean install --file pom.xml
```

2. Open the target folder after running the above command on 1.
```
java -jar orange-payment-processor
```

3.Run the application 
```
mvn spring-boot:run
```

**Notes:**
- This service can be further improved by removing the logic in the main class and 
exposing endpoints to get different computations
- A database can be implemented so that transactions will be pulled from the DB
and then be computed.
- Application can be deployed as jar in docker file and build an image which can now run as a container



---
