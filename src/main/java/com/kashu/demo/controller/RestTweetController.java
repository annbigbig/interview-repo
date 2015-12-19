package com.kashu.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Void> createUser(@RequestBody Tweet tweet, 	UriComponentsBuilder ucBuilder) {
		System.out.println("Creating Tweet " + tweet.getMessage());

		/*
		 * you must check the man owned the credential and the man created this tweet were the same guy.
		if (userService.isExisted(user)) {
			System.out.println("A User with name " + user.getUsername() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		*/

		tweetService.create(tweet);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/tweet/{id}").buildAndExpand(tweet.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

}
