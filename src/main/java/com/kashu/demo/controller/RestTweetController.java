package com.kashu.demo.controller;

import java.util.Set;

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
import com.kashu.demo.service.IUserService;


@RestController
public class RestTweetController {
	@Autowired
	ITweetService tweetService;
	
	@Autowired
	IUserService userService;
	
	//-------------------Create a Tweet--------------------------------------------------------
	@RequestMapping(value = "/tweet/", method = RequestMethod.POST)
	public ResponseEntity<Void> createTweet(@RequestBody Tweet tweet, 	UriComponentsBuilder ucBuilder) {
		System.out.println("Creating Tweet " + tweet.getMessage());

		/*
		 * you must check the man owned the credential and the man created this tweet were the same guy.
		   (not implemetent yet because i have not work with the basic authentication part)
		*/
		
		/*
		   you can not update an existing tweet article
		 */
		Long id = tweet.getId();
		if (id!=null && tweetService.isExisted(id)) {
			System.out.println("A Tweet with id " + id + " already exist");
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
		@RequestMapping(value = "/tweets_owned_by/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Set<Tweet>> getTweets(@PathVariable("userId") long userId) {
			System.out.println("Fetching Tweets owned by user with id " + userId);
			User user = userService.findOne(userId);
			Set<Tweet> tweets;
			if (user != null) {
				tweets = user.getTweets();
				if(tweets != null){
					return new ResponseEntity<Set<Tweet>>(tweets, HttpStatus.OK);
				}
			}
			
			System.out.println("Not any tweet was found");
			return new ResponseEntity<Set<Tweet>>(HttpStatus.NOT_FOUND);
		}
		
}
