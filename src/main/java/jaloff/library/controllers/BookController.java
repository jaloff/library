package jaloff.library.controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jaloff.library.dto.requests.CreateBookRequest;
import jaloff.library.entities.Book;
import jaloff.library.entities.Return;
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
	public Book createBook(@RequestBody @Valid CreateBookRequest dto) {
		return bookService.create(dto);
	}
	
	@PutMapping("/{id}")
	public Book updateBook(@PathVariable long id, @RequestBody @Valid Book book) {
		book.setId(id);
		return bookService.update(book);
	}
	
	@GetMapping("/{id}/returns")
	public List<Return> findBookReturns(@PathVariable long id) {
		return bookService.get(id).getReturns();
	}
}
