package jaloff.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import jaloff.library.entities.Book;

public interface BooksRepository extends JpaRepository<Book, Long> {

}
