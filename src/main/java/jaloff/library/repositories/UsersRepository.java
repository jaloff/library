package jaloff.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jaloff.library.entities.User;

public interface UsersRepository extends JpaRepository<User, Long> {

}
