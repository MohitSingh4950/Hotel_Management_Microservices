package com.lcwd.rating.services;

import java.util.List;

import com.lcwd.rating.entities.Rating;

public interface RatingService {

	
	//create
	Rating create(Rating rating); 
	//get All
	List<Rating> getRatings();
	
	//get all by user id
	List<Rating> getRatingsByUserId(String userId);
	
	//get all by hotel
	List<Rating> getRatingsByHotelId(String hotelId);
}
