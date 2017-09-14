package jaloff.library.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jaloff.library.dto.requests.ChangePasswordRequest;
import jaloff.library.entities.Role;
import jaloff.library.entities.User;
import jaloff.library.exceptions.ReadOnlyPropertyChangedException;
import jaloff.library.exceptions.UserNotFoundException;
import jaloff.library.exceptions.UserWithEmailExistException;
import jaloff.library.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordGenerator passwordGenerator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User get(long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	// TODO send email
	public User create(User user) {
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new UserWithEmailExistException(user.getEmail());
		}
		
		if(user.getId() != null) {
			user.setId(null);
		}
		
		String password = passwordGenerator.generate(); 
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(Role.ROLE_USER.toString());
		
		return userRepository.save(user);
	}

	public void delete(long id) {
		if(userRepository.exists(id)) {
			userRepository.delete(id);
		} else {
			throw new UserNotFoundException(id);
		}
	}

	public User update(User updatedUser) {
		User user = this.get(updatedUser.getId());
		
		if(updatedUser.getRole() == null) {
			updatedUser.setRole(user.getRole());
		} else if(updatedUser.getRole().compareTo(user.getRole()) != 0) {
			throw new ReadOnlyPropertyChangedException("role", user.getRole(), updatedUser.getRole());
		}
		
		user.setFirstName(updatedUser.getFirstName());
		user.setLastName(updatedUser.getLastName());
		user.setEmail(updatedUser.getEmail());
		
		
		return userRepository.save(user);
	}
	
	public void changePassword(ChangePasswordRequest request) {
		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(principal.getName()).get();
		if(passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
			userRepository.save(user);
		} else {
			throw new BadCredentialsException("Wrong password");
		}
	}
}
