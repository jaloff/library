package jaloff.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jaloff.library.entities.Book;
import jaloff.library.exceptions.BookNotFoundException;
import jaloff.library.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public Book get(long id) {
		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
	}
	
	public List<Book> getAll() {
		return bookRepository.findAll();
	}
	
	public Book create(Book book) {
		if(book.getId() != null) {
			book.setId(null);
		}
		
		return bookRepository.save(book);
	}
	
	public void delete(long id) {
		if(bookRepository.exists(id)) {
			bookRepository.delete(id);
		}
		throw new BookNotFoundException(id);
	}
	
	public void update(Book book) {
		if(bookRepository.exists(book.getId())) {
			bookRepository.delete(book);
		}
		throw new BookNotFoundException(book.getId());
	}
}
