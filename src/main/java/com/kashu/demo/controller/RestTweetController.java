package com.kashu.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.kashu.demo.domain.Tweet;
import com.kashu.demo.domain.User;
import com.kashu.demo.service.ITweetService;

@RestController
public class RestTweetController {
	@Autowired
	ITweetService tweetService;
	
	//-------------------Create a Tweet--------------------------------------------------------
	@RequestMapping(value = "/tweet/add/", method = RequestMethod.POST)
	public ResponseEntity<Void> createTweet(@RequestBody Tweet tweet, 	UriComponentsBuilder ucBuilder) {
		System.out.println("Creating Tweet " + tweet.getMessage());

		/*
		 * you must check the man owned the credential and the man created this tweet were the same guy.
		   (not implemetent yet because i have not work with the basic authentication part)
		*/
		
		/*
		   you can not update an existing tweet article
		 */
		if (tweetService.isExisted(tweet.getId())) {
			System.out.println("A Tweet with id " + tweet.getId() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		tweetService.create(tweet);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/tweet/{id}").buildAndExpand(tweet.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	//-------------------Retrieve a Tweet--------------------------------------------------------
	
		@RequestMapping(value = "/tweet/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Tweet> getTweet(@PathVariable("id") long id) {
			System.out.println("Fetching Tweet with id " + id);
			Tweet tweet = tweetService.findOne(id);
			if (tweet == null) {
				System.out.println("Tweet with id " + id + " not found");
				return new ResponseEntity<Tweet>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Tweet>(tweet, HttpStatus.OK);
		}
	
		//-------------------Retrieve all of the Tweets owned by someone--------------------------------------------------------
		
		

}
