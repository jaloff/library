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
import jaloff.library.services.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping
	public Collection<Book> findBooks(){
		return bookService.getAll();
	}
	
	@GetMapping("/{id}")
	public Book findBookById(@PathVariable long id) {
		return bookService.get(id);
	}
	
	@DeleteMapping("/{id}")
	public void deleteBookById(@PathVariable long id) {
		bookService.delete(id);
	}
	
	@PostMapping
	public Book createBook(@RequestBody Book book) {
		return bookService.create(book);
	}
	
	@PutMapping
	public void updateBook(@RequestBody Book book) {
		bookService.update(book);
	}
}
