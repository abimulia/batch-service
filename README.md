# Batch Service
[![Spring Boot v3.3.5](https://img.shields.io/badge/Java-SpringBoot-green)](https://spring.io/)
[![License](http://img.shields.io/:license-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0.html)
[![Spring Batch v5](https://img.shields.io/badge/Spring-Batch-red)](https://docs.spring.io/spring-batch/docs/5.0.x/reference/html/)

This project created by `start.spring.io` contain [Spring Boot](https://spring.io/) version 3.3.5.

Batch Service project, using spring-batch to do batch processing.
This will show how to use spring-batch framework to run batch process.


## Development server

Run `mvn spring-boot:run` for a dev server. 

To run job with parameter need to package the project using maven build with goals clean package
Then run the created jar 
` java -jar .\batch-service-1.0.0.jar "item=shoes" "run.date=2024-12-25"`

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.0/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.0/maven-plugin/build-image.html)
* [Spring Batch](https://docs.spring.io/spring-boot/3.4.0/how-to/batch.html)
* [Spring Data JDBC](https://docs.spring.io/spring-boot/3.4.0/reference/data/sql.html#data.sql.jdbc)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.0/reference/using/devtools.html)
* [Docker Compose Support](https://docs.spring.io/spring-boot/3.4.0/reference/features/dev-services.html#features.dev-services.docker-compose)

### Guides
The following guides illustrate how to use some features concretely:

* [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)
* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Docker Compose support
This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* mysql: [`mysql:latest`](https://hub.docker.com/_/mysql)

Please review the tags of the used images and set them to the same as you're running in production.

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

`maven.compiler.proc` 
Sets whether annotation processing is performed or not

Starting with JDK 21, this option must be set explicitly.
`<maven.compiler.proc>full</maven.compiler.proc>`
