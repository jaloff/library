package jaloff.library.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jaloff.library.entities.User;

public interface UsersRepository extends JpaRepository<User, Long> {
	Optional<User> findById(long id);
	boolean existsByEmail(String email);
}
