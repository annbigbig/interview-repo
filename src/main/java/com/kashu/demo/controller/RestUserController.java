package com.kashu.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

			if (userService.isExisted(user)) {
				System.out.println("A User with name " + user.getUsername() + " already exist");
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}

			userService.create(user);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}

}
