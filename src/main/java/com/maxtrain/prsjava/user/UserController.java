package com.maxtrain.prsjava.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/users")


public class UserController {
	
		@Autowired
		private UserRepository userRepo;
		
		//Login Method
		@GetMapping("{username}/{password}")
		public ResponseEntity<User> login(@PathVariable String username, @PathVariable String password) {
			Optional<User> user = userRepo.findByUsernameAndPassword(username, password);
				if(user.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}	
						return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		}
		// Get all method
		@GetMapping
		public ResponseEntity<Iterable<User>> getUsers() {
			Iterable<User> users = userRepo.findAll();
						return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
		}
		// by id
		@GetMapping("{id}")
		public ResponseEntity<User> getUser(@PathVariable int id) {
			Optional<User> user = userRepo.findById(id);
			if(user.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
						return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		}
		// PostMethod
		@PostMapping
		public ResponseEntity<User> postUser(@RequestBody User user) {
				User newUser = userRepo.save(user);
						return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
		}
		// PutMethod
		@SuppressWarnings("rawtypes")
		@PutMapping("{id}")
		public ResponseEntity putUser(@PathVariable int id, @RequestBody User user) {
				if(user == null || user.getId() == 0) {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				var active = userRepo.findById(user.getId());
				if(active.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
						
				}
				userRepo.save(user);
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		// DeleteMethod
		@SuppressWarnings("rawtypes")
		@DeleteMapping("{id}")
		public ResponseEntity deleteUser(@PathVariable int id) {
			var active = userRepo.findById(id);
			if(active.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
			userRepo.delete(active.get());
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
        }
}