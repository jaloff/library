package jaloff.library.repositories;

import java.util.Optional;

import jaloff.library.entities.User;

public interface UserRepository extends GenericRepository<User> {
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
}
