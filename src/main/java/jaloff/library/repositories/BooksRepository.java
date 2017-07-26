package jaloff.library.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jaloff.library.entities.Book;

public interface BooksRepository extends JpaRepository<Book, Long> {
	Optional<Book> findById(long id);
}
