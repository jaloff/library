package jaloff.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jaloff.library.dto.requests.CreateBookRequest;
import jaloff.library.entities.Book;
import jaloff.library.exceptions.BookNotFoundException;
import jaloff.library.exceptions.ReadOnlyPropertyChangedException;
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
	
	public Book create(CreateBookRequest dto) {
		Book book = Book.builder()
				.title(dto.getTitle())
				.status(Book.Status.AVAIABLE)
				.build();
		
		return bookRepository.save(book);
	}
	
	public void delete(long id) {
		if(bookRepository.exists(id)) {
			bookRepository.delete(id);
		} else {
			throw new BookNotFoundException(id);
		}
	}
	
	public Book update(Book updatedBook) {
		Book book = this.get(updatedBook.getId());
		
		if(updatedBook.getStatus() == null) {
			updatedBook.setStatus(book.getStatus());
		}else if(updatedBook.getStatus() != book.getStatus()) {
			throw new ReadOnlyPropertyChangedException("status", book.getStatus().toString(), updatedBook.getStatus().toString());
		}
		
		return bookRepository.save(updatedBook);
	}
}
