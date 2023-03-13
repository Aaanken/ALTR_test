# ALTR_test

This Java application is designed to create a table with 5 columns, including 2 indices, and insert exactly 10 records into the table. It also includes test cases using the JUnit framework to test the functionality of the application.

## Prerequisites
JDK 1.8 or higher

MySQL database server

JUnit 4

Docker

## Setting up a Local MySQL Host Using Docker


1. Install Docker on your local machine.

2. Create a docker-compose.yml file in your preferred directory with the following contents:


```bash
version: '2'

services:
  mariadb:
    image: mariadb:10.1
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: mysecretpassword
```
3. Open a terminal window and navigate to the directory containing the docker-compose.yml file.

4. Run the following command to start the MySQL service:

```bash
docker-compose up -d

```
5. Verify that the MySQL service is running by opening a web browser and navigating to http://localhost:8080. You should see a MySQL login page.

## Usage

1. Clone the repository to your local machine.

2. Open the project in your preferred Java IDE.

3. Update the URL, USER, and PASSWORD variables in the TableTest class to match your local MySQL server.

4. Run the TableTest class to create the table, insert the data, and test the functionality.
5. To view the code coverage, click on the "Run 'TableTest' with Coverage" tab in the "Run" window.
6. To view the result in Docker:
- Open a terminal in Docker
```bash
SHOW DATABASES;
USE <anna_test>;
SHOW TABLES;
SELECT * FROM <mytable>;
```

