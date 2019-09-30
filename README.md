# Project Overview
This project is a Rest API using Java/Maven/SpringBoot application as sample solution of [Bank Account Kata Problem](https://gist.github.com/abachar/d20bdcd07dac589feef8ef21b487648c).

# How to Run the Project
After import of project as maven project, we need to launch some basic commands:

```
mvn clean install
mvn spring-boot:run
```

# How to Check the database table

This project use H2 database. 
After running the project, the ** first Acount with a total balance = 1000 ** will be created.

To check the database table go to : [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/)

Database URL is: **jdbc:h2:mem:ACCOUNT_DB** \(You can change the url in application.properties file\).

Database username is: **sa** \(configuration in application.properties \).


# Documentation Swagger

All APIs are documented by Swagger. To check Swagger documentation: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

# How to run unit test

```
mvn clean test
```


