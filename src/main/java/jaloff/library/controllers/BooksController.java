package jaloff.library.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		return findBook(id);
	}
	
	@DeleteMapping("/{id}")
	public void deleteBookById(@PathVariable long id) {
		findBook(id);
		booksRepo.delete(id);
	}
	
	@PostMapping
	public Book createBook(@RequestBody Book book) {
		return booksRepo.save(book);
	}
	
	@PutMapping
	public Book updateBook(@RequestBody Book book) {
		findBook(book.getId());
		return booksRepo.save(book);
	}
	
	public Book findBook(long id) {
		return booksRepo.findById(id)
				.orElseThrow(() -> new BookNotFoundException(id));
	}
}
