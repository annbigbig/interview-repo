package com.kashu.demo.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public ResponseEntity<Void> createTweet(@RequestBody Tweet tweet, UriComponentsBuilder ucBuilder, @AuthenticationPrincipal UserDetails logonUser) {
		User currentUser = userService.findByUsername(logonUser.getUsername());
		  System.out.println("message " + tweet.getMessage());
		  System.out.println("username = " + logonUser.getUsername());		
		  System.out.println("user.id = " + currentUser.getId());
   tweet.setUser_id(currentUser.getId());
		tweetService.create(tweet);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/tweet/{id}").buildAndExpand(tweet.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	//-------------------Delete a Tweet--------------------------------------------------------
	@RequestMapping(value = "/tweet/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Tweet> deleteTweet(@PathVariable("id") long id, Principal principal){		
		Tweet tweet = tweetService.findOne(id);
		if(tweet == null){
			System.out.println("tweet with id " + id + " not found");
			return new ResponseEntity<Tweet>(HttpStatus.NOT_FOUND);
		}
		User currentUser = userService.findByUsername(principal.getName());
		if(currentUser.getId().longValue()!=tweet.getUser_id()){
			System.out.println("the user '" + currentUser.getUsername() + "' with id " + currentUser.getId() + " not the owner of tweet with id " + tweet.getId());
			return new ResponseEntity<Tweet>(HttpStatus.UNAUTHORIZED);
		}
		tweetService.delete(tweet);
		return new ResponseEntity<Tweet>(HttpStatus.NO_CONTENT);
	}

	//------------------- Update a Tweet --------------------------------------------------------
	@RequestMapping(value = "/tweet/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Tweet> updateTweet(@PathVariable("id") long id, @RequestBody Tweet tweet, @AuthenticationPrincipal UserDetails logonUser){
		Tweet tweetInDb = tweetService.findOne(id);
		if(tweetInDb == null){
			System.out.println("tweet with id " + id + " not found");
			return new ResponseEntity<Tweet>(HttpStatus.NOT_FOUND);
		}
		User currentUser = userService.findByUsername(logonUser.getUsername());
		if(currentUser.getId().longValue()!=tweetInDb.getUser_id().longValue()){
			System.out.println("the user '" + currentUser.getUsername() + "' with id " + currentUser.getId() + " not the owner of tweet with id " + tweetInDb.getId());
			return new ResponseEntity<Tweet>(HttpStatus.UNAUTHORIZED);
		}
		tweetInDb.setMessage(tweet.getMessage());
		tweetService.update(tweetInDb);
		return new ResponseEntity<Tweet>(tweetInDb,HttpStatus.OK);
	}

	//-------------------Retrieve a Tweet--------------------------------------------------------
		@RequestMapping(value = "/tweet/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Tweet> getTweet(@PathVariable("id") long id, Principal principal) {
			System.out.println("Fetching Tweet with id " + id);
			Tweet tweet = tweetService.findOne(id);
			User currentUser = userService.findByUsername(principal.getName());
			if (tweet == null) {
				System.out.println("Tweet with id " + id + " not found");
				return new ResponseEntity<Tweet>(HttpStatus.NOT_FOUND);
			}
			if(tweet.getUser_id().longValue()!=currentUser.getId().longValue()){
				System.out.println("you are not the owner of tweet id : " + tweet.getId());
				return new ResponseEntity<Tweet>(HttpStatus.FORBIDDEN);
			}
			
			return new ResponseEntity<Tweet>(tweet, HttpStatus.OK);
		}
	
		//-------------------Retrieve all of the Tweets owned by someone--------------------------------------------------------
		@RequestMapping(value = "/tweet/all/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Set<Tweet>> getTweets() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			System.out.println("username = " + username);
			User currentUser = userService.findByUsername(username);
			Set<Tweet> tweets = currentUser.getTweets();
			if(currentUser!=null){
				if(tweets.size()>0){
				  return new ResponseEntity<Set<Tweet>>(tweets, HttpStatus.OK);
				}
			}
			
			System.out.println("No any tweet was found");
			return new ResponseEntity<Set<Tweet>>(HttpStatus.NOT_FOUND);
		}
		
}
