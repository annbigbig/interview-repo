package com.kashu.demo.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

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
import com.kashu.demo.domain.Tweet;
import com.kashu.demo.domain.User;
import com.kashu.demo.domain.Role;

@RestController
public class RestUserController {
	@Autowired
	IUserService userService;
	
	//-------------------Create a User--------------------------------------------------------
	
		@RequestMapping(value = "/user/", method = RequestMethod.POST)
		public ResponseEntity<Void> createUser(@RequestBody User user, 	UriComponentsBuilder ucBuilder) {
			System.out.println("Creating User " + user.getUsername());
			Set<Role> roles = new HashSet<Role>();
			Role role = new Role();
			role.setRole("ROLE_USER");
			roles.add(role);
			user.setRoles(roles);
			user.setEnabled(true);
			role.setUser(user);
			
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
			User userInDb = userService.findOne(id);
			if (userInDb==null) {
				System.out.println("User with id " + id + " not found");
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			userInDb.setPassword(user.getPassword());
			userService.update(userInDb);
			return new ResponseEntity<User>(userInDb, HttpStatus.OK);
		}
		
		//-------------------Delete a User--------------------------------------------------------
		@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<User> deleteUser(@PathVariable("id") long id){		
			User user = userService.findOne(id);
			if(user == null){
				System.out.println("tweet with id " + id + " not found");
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}
			userService.delete(user);
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		
}
