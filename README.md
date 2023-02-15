# Hotel_Management_Microservices

This is a microservice project
These are 3 microservices, 
USER_SERVICE ==> RATING_SERVICE ==>HOTEL_SERVICE

All these 3 microservices are interacting with one API_GATEWAY microservice.
ALL these 3 microservices are having 3 DB connectivity, 
USER_SERVICE is using MySQL database
RATING_SERVICE is using PostgreSQL
HOTEL_SERVICE is using MongoDB
Feign Client is used for configuring the host & port number with the service names directly.

one CONFIG_SERVICE microservice is created to store the configuration in one location.
Jmeter is being used to test the set of loads in this microservices.
Okta security is used to secure this application. We can use JWT authentication also for the security.

Third party softwares used
1)Apache Jmeter for Testing
2)Okta for Security(Oauth2)

Install these above 2 softwares for testing this application.
 
