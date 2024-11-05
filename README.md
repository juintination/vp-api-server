# Project Overview
- Implementation of a back-end API server that converts videos into spatial images.
- This project uses the [Udacity Nanoderee Style](https://udacity.github.io/git-styleguide/) as a git commit message rule.

## Related Projects
- React Frontend Application: [https://github.com/iseungho/Capeasy](https://github.com/iseungho/Capeasy)
  - The frontend web application that interacts with this backend API server.
- Stitching API Server: [https://github.com/WellshCorgi/stitching-api-server](https://github.com/WellshCorgi/stitching-api-server)
  - The server responsible for image stitching operations.

## Key Features
- Acts as an intermediary between a React Web Application and a Stitching API server.
- Implements user authentication and authorization using Spring Security.
- Utilizes JPA and QueryDSL for efficient database management.

## Main Functions
- Video Processing
  - Receives videos from the React Web Application and forwards them to the Stitching API server.
- Image Conversion
  - Receives converted spatial images from the Stitching API server.
- Result Transmission
  - Returns the converted spatial images to the React Web Application.
- User Management
  - Provides secure user authentication and authorization through Spring Security.
- Data Management
  - Performs efficient database operations using JPA and QueryDSL.

## Technical Stack
- Language
  - JAVA 17
- Framework
  - Spring boot 3.2.4
- Dependency Management
  - Gradle
- Security
  - Spring Security 6.2.3
  - JWT (jjwt 0.11.5)
- Database
  - MariaDB
  - H2 (for testing)
- ORM & Data Access
  - JPA (Java Persistence API)
  - QueryDSL 5.0.0
- Utilities
  - Lombok
  - Gson 2.10.1
  - JavaFaker 1.0.2 (for generating fake data)
  - Thumbnailator 0.4.19
- Testing
  - JUnit

## API Documentation
- The [API documentation](https://documenter.getpostman.com/view/32366655/2sA3kXELHZ) has been created using Postman.
- When the project is running, you can access the documentation through the base URL.

## Deployment
- This project has been deployed using [AWS Elastic Beanstalk](https://aws.amazon.com/ko/elasticbeanstalk/?trk=3d211853-d899-491e-bd5a-fb5f17de6f0f&sc_channel=ps&ef_id=CjwKCAjwg-24BhB_EiwA1ZOx8toyJDcUjiqv9TNAK3Gvkl29AqKEZgORomacVk1wcx8AsY3TYG3M3RoCcqkQAvD_BwE:G:s&s_kwcid=AL!4422!3!651510175878!e!!g!!elasticbeanstalk!19835789747!147297563979).
- Elastic Beanstalk automates the management of application infrastructure, allowing developers to focus on writing code.
- It provides features such as capacity provisioning, load balancing, auto-scaling, and application health monitoring.

## Prerequisites
Before running this project, you need to make the following configuration changes:
- Database Configuration
  - Modify the database-related settings in both application.properties and build.gradle files.
- Image Stitching Server URL
  - In the application.properties file, update the convert.server.url to point to your image stitching server.

These configuration steps are crucial for the proper functioning of the application. Ensure that you have the correct database credentials and the appropriate URL for the image stitching server before attempting to run the project.

## How to Configure
- build.gradle
  - Database configuration:
  ```
  dependencies {
      implementation 'org.mariadb.jdbc:mariadb-java-client' // Replace with your_database_driver if necessary
  }
  ```
- application.properties
  - Database configuration:
  ```
  spring.datasource.driver-class-name=your_database_driver_class_name
  spring.datasource.url=jdbc:your_database_type://your_database_url:your_port/your_database_name
  spring.datasource.username=your_database_username
  spring.datasource.password=your_database_password
  ```
  - Image stitching server URL:
  ```
  convert.server.url=http://your-stitching-server-url:your-stitching-server-port
  ```
