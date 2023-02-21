

# WorkMotion Employee Management System

REST API  service that allows  to manage  Employee States
## Features
- An Endpoint to add an employee
- An Endpoint to change the state of a given employee according to the state machine rules
- An Endpoint to fetch employee details



## Technologies Used

- Java 11
- Springboot
- Spring State Machine
- Spring Data JPA
- H2 in Memory Database
- Junit , Mockito, Mock MVC, Spring State Machine Test
- Maven
## How to run
There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in
the src/main/java/com/workmotion/ems/EmsApplication.java class from your IDE.

also you can use:
1. The traditional way with `java -jar`
	- Change directory(cd) in to the folder of the application source code
	- Run `mvn clean package`
	- Issue `java -jar ems-1.0.0.jar`
	- Use the requests provided below.
2. From docker container
	- Build the applicaiton `mvn clean package`
	- Build docker image `docker build -t myorg/myapp .`
	- Run the image `docker run -p 8080:8080 myorg/myapp`

## DataBase Info

Url: http://localhost:8080/h2

JDBC URL:  jdbc:h2:mem:maindb

User Name: sa

there is no password



Requests:


## REST Endpoints

http://localhost:8080/api/

### POST

`Add Emmployee` http://localhost:8080/api/employee <br/>

**Request Example**
```
{
  "id":1,
  "firstName": "string",
  "lastName": "string",
  "dob": "2021-12-16",
  "gender": "F",
  "passportNumber": "string",
  "employementTerms": {"id": 2}
}'
```
**Response**
```
201 created
```
### Patch

`update Emmployee Status` http://localhost:8080/api/employee <br/>

**Request  Example**
```
{
"employeeID":1,
"event":"BEGIN_CHECK"
}
```


### Get

`Get Emmployee ` http://localhost:8080/api/employee?employeeID={ID} <br/>

**Request Example**
```
http://localhost:8080/api/employee?employeeID=1
```

***Success response:***
```
{
    "id": 1,
    "firstName": "string",
    "lastName": "string",
    "dob": "2021-12-16",
    "gender": "F",
    "passportNumber": "string",
    "employementTerms": {
        "id": 1,
        "jobTitle": "Java developer",
        "jobDescription": "Job description goes here",
        "skillRequirement": "Skill requirement",
        "educationRequirement": "Education requirement goes here",
        "annualSalary": 50000.0
    },
    "status": [
        "IN_CHECK",
        "SECURITY_CHECK_STARTED",
        "WORK_PERMIT_CHECK_STARTED"
    ],
    "createdAt": "2023-02-21T00:16:41.300788+02:00",
    "modifiedAt": "2023-02-21T00:16:41.300788+02:00"
}
```
**Error response:**
```
{
	"type": "response-employee",
	"success": false,
	"resultCode": 404001,
	"message": "Employee not found.",
	"transactionId": "e295944d-6fb5-49ad-83da-50fd5e74a3e5"
}
```
