package jaloff.library.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jaloff.library.entities.User;
import jaloff.library.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<User> findUsers() {
		return userService.getAll();
	}
	
	@GetMapping("/{id}")
	public User findUserById(@PathVariable long id) {
		return userService.get(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public User registerUser(@RequestBody User user, BindingResult bindingResult) {
//		if(bindingResult.hasErrors()) {
//			ResponseEntity<User> response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//			return response;
//		}
//		
		
		return userService.create(user);
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable long id) {
		userService.delete(id);
	}
	
	@PutMapping
	public void updateUser(@RequestBody User user) {
		userService.update(user);
	}
	
}