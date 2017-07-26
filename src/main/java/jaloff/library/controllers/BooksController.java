package jaloff.library.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jaloff.library.entities.Book;
import jaloff.library.exceptions.BookNotFoundException;
import jaloff.library.repositories.BooksRepository;

@RestController
@RequestMapping("/books")
public class BooksController {

	@Autowired
	private BooksRepository booksRepo;
	
	@GetMapping
	public Collection<Book> findBooks(){
		return booksRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Book findBookById(@PathVariable long id) {
		return booksRepo.findById(id)
				.orElseThrow(() -> new BookNotFoundException(id));
	}
}