# Java Spring Client-Server Web-Application
Client-server web app testing web sites from server in parallel, saving monitoring data to database and posting response status to browser.

# Getting Started
How the system works:
  - Server:
      For building the server-side of the project I use Java Spring Framework. Basically the system uses REST API to communicate with the client. 
      1) With POST method server gets the JSON object, transform it to POJO, perfrom the pingUrl() method to get the response code and saves it to Database.
      2) Next we use @Scheduled annotation to update the status of the url in database every 5 sec.
      3) The last is GET method which sends the list of POJOs with id, url and response code.
   
   - Client 
      Front-end part of the program is one html page with input value where we put the desired url and the result table with response status. Program uses JavaScript for sending POST and GET requests, getting the json and transform it to the array of objects, update the result table etc.

# Prerequisites
You need to install Maven build tool as it can manage build of the project.
Download it here https://maven.apache.org/download.cgi

# Installation and Running
Run it from terminal:

  To run the application download zip of the project, unzip it and perform the following command on terminal:

  $ cd urlServiceCheck
  $ mvn clean spring-boot:run

Run it from IDE:
  
  Create new project from existed sources in Intelij IDea, choose maven Build tool and follow the instructions. Then just run UrlServiceCheckApplication.java file

Run the war file:

  To run the war file, move to the urlServiceCheck/target folder and run the following command:
  $ java -jar urlServiceCheck-0.0.1-SNAPSHOT.war


# Tests
The system can be tested by running:

$ mvn test

in the project folder or by running UrlServiceCheckApplicationTests.java file. The tests are used to check the GET and POST methods to the endpoint /urls.

# Built With
Operation system: Linux;
IDE: Intelij IDea Ultimate;
Web-server: Tomcat;
Build tool: Maven;
The web frameworw: Java Spring;
Database: H2;





 
