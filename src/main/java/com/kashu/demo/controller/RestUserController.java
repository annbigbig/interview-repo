package com.kashu.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.kashu.demo.service.IUserService;
import com.kashu.demo.service.impl.UserService;
import com.kashu.demo.domain.User;

@RestController
public class RestUserController {
	@Autowired
	IUserService userService;
	
	//-------------------Create a User--------------------------------------------------------
	
		@RequestMapping(value = "/user/", method = RequestMethod.POST)
		public ResponseEntity<Void> createUser(@RequestBody User user, 	UriComponentsBuilder ucBuilder) {
			System.out.println("Creating User " + user.getUsername());

			/*
			if (userService.isExisted(user.getId())) {
				System.out.println("A User with id " + user.getId() + " called '" + user.getUsername() + "' already exist");
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			*/

			userService.create(user);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}

		//-------------------Retrieve Single User--------------------------------------------------------
		
		@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<User> getUser(@PathVariable("id") long id) {
			System.out.println("Fetching User with id " + id);
			User user = userService.findOne(id);
			if (user == null) {
				System.out.println("User with id " + id + " not found");
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		
		//------------------- Update a User (all you can do is changing password ) --------------------------------------------------------
		
		@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
		public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
			System.out.println("Updating User " + id);
			
			User currentUser = userService.findOne(id);
			
			if (currentUser==null) {
				System.out.println("User with id " + id + " not found");
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			currentUser.setPassword(user.getPassword());
			userService.update(currentUser);
			return new ResponseEntity<User>(currentUser, HttpStatus.OK);
		}
		
}
