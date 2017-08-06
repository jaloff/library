package jaloff.library.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jaloff.library.entities.User;
import jaloff.library.exceptions.UserNotFoundException;
import jaloff.library.exceptions.UserWithEmailExistException;
import jaloff.library.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordGenerator passwordGenerator;

	public User get(long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	// TODO Encode password and send email
	public User create(User user) {
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new UserWithEmailExistException(user.getEmail());
		}
		
		if(user.getId() != null) {
			user.setId(null);
		}
		
		user.setPassword(passwordGenerator.generate());
		
		return userRepository.save(user);
	}

	public void delete(long id) {
		if(userRepository.exists(id)) {
			userRepository.delete(id);
		}
		throw new UserNotFoundException(id);
	}

	public void update(User user) {
		if(userRepository.exists(user.getId())) {
			userRepository.save(user);
		}
		throw new UserNotFoundException(user.getId());
	}
	
	// TODO Encode password
	public void changePassword(String passwordOld, String passwordNew) {
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(principal.getName()).get();
		if(user.getPassword().compareTo(passwordOld) == 0) {
			user.setPassword(passwordNew);
			userRepository.save(user);
		}
		// TODO Throw exception
	}
}
