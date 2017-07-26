package jaloff.library.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jaloff.library.entities.Book;
import jaloff.library.repositories.BooksRepository;

@RestController
@RequestMapping("/books")
public class BooksControler {

	@Autowired
	private BooksRepository booksRepo;
	
	@GetMapping
	public Collection<Book> books(){
		return booksRepo.findAll();
	}
}
