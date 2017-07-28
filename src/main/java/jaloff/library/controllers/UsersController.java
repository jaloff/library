package jaloff.library.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import jaloff.library.exceptions.UserNotFoundException;
import jaloff.library.exceptions.UserWithEmailExistException;
import jaloff.library.repositories.UsersRepository;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersRepository usersRepo;
	
	@GetMapping
	public List<User> findUsers() {
		return usersRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public User findUserById(@PathVariable long id) {
		return findUser(id); 
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public User registerUser(@RequestBody User user) {
		if(usersRepo.existsByEmail(user.getEmail())) {
			throw new UserWithEmailExistException(user.getEmail());
		}
		return usersRepo.save(user);
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable long id) {
		usersRepo.delete(findUser(id));
	}
	
	@PutMapping
	public void updateUser(@RequestBody User user) {
		User userToUpdate = findUser(user.getId());
		userToUpdate.setFirstName(user.getFirstName());
		userToUpdate.setLastName(user.getLastName());
		userToUpdate.setEmail(user.getEmail());
		usersRepo.save(userToUpdate);
	}
	
	private User findUser(long id) {
		return usersRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}
}