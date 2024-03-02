# Hardware Store web application
This project is a web application for managing a hardware store and purchased items. 

## Technologies used:
### Frontend
React.js, Material UI, Vite (PWA Workbox), IndexedDB, Local cache

### Backend
Spring Boot, Gradle, Redis, MySql, JPA, Criteria Query API

## Setup and Installation:

### Redis configuration
To install Redis on Windows Subsystem for Linux (WSL), follow these steps:
1. Update Your System: `sudo apt update`
2. Install Redis: `sudo apt install redis-server`
3. Start Redis: `sudo service redis-server start`

### Backend Configuration:
1. Clone the backend repository and navigate to the project directory.
2. Configure the database connection in the application.properties file.
3. Build the project using Gradle: `./gradlew build`.
4. Run the backend server: `./gradlew bootRun`.


### Frontend Configuration:
1. Clone the frontend repository and navigate to the project directory.
2. Install dependencies: `npm install`.
3. Start the development server: `npm start`.
   
