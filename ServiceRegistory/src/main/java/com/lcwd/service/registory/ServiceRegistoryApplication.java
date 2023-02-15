package com.lcwd.service.registory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistoryApplication.class, args);
	}

}

/*
with the help of load balancing all the services are registered
how the service need to made
first request user with userid(suppose a request is generated)
with userid get all the info
with that which hotel got the ratings can be
fetch from hotel service then send response 

communicate the microservices
*/