package jaloff.library.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jaloff.library.entities.User;
import jaloff.library.repositories.UsersRepository;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersRepository userRepo;
	
	@GetMapping
	public List<User> findUsers() {
		return userRepo.findAll();
	}
}