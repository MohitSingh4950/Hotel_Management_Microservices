package com.lcwd.user.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.entities.User.UserBuilder;
import com.lcwd.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
//	int retryCount =1;
	@Autowired
	private UserService userService;
	//create
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	//single user get
	@GetMapping("/{userId}")
//	@CircuitBreaker(name="ratingHotelBreaker", fallbackMethod="ratingHotelFallback")
//	@Retry(name="ratingHotelBreaker",fallbackMethod="ratingHotelFallback")
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		logger.info("Get Single User Handler: Usercontroller");
//		logger.info("Retry count: {}", retryCount);
//		retryCount++;
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	//creating fall back method for circuits breaker
	
	public ResponseEntity<User>  ratingHotelFallback(String userId, Exception ex){
		logger.info("Fallback is executed because service is down :", ex.getMessage());
		ex.printStackTrace();
		User user = User.builder().email("dummy@gmail.com").name("Dummy")
				.about("This user is created dummy because some service is down")
				.userId("141234")
				.build();
		return new ResponseEntity<>(user,  HttpStatus.BAD_REQUEST);
	}
	
	//all user get
	@GetMapping("displayAllUsers")
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}
	
}
