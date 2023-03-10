package com.lcwd.user.service.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.external.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService  hotelService;
	
	@Override
	public User saveUser(User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		//implement rating service call : using rest template
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		// get user from database with the help of user repository
		User user =	userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id not found on server" +userId));
		
		//fetch ratings of the above user from rating service
		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/"+user.getUserId(),Rating[].class);
		logger.info("List of ratings {}",ratingsOfUser);
		List<Rating> listOfRatings = Arrays.stream(ratingsOfUser).toList();
		List<Rating> ratingList = listOfRatings.stream().map(rating ->{
			//api call to hotel service to get the hotel
			//set the hotel to rating
			//return the rating
//			ResponseEntity<Hotel> hotelList = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
//			Hotel hotel = hotelList.getBody();
//			logger.info("STATUS : {} ",hotelList.getStatusCode());
			rating.setHotels(hotel);
			return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingList);
		return user;
	}

	@Override
	public void deleteUser(String userId) {
		User user = getUser(userId);
		 userRepository.delete(user);
	}

	@Override
	public User updateUser(String userId) {
		User user = getUser(userId);
		return userRepository.saveAndFlush(user);
	}

}
