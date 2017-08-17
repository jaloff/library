package jaloff.library.services;

import java.sql.Date;
import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jaloff.library.dto.IssueBookRequestDto;
import jaloff.library.entities.Book;
import jaloff.library.entities.Issue;
import jaloff.library.exceptions.BookAlreadyIssuedException;
import jaloff.library.exceptions.IssueNotFoundException;
import jaloff.library.repositories.IssueRepository;

@Service
public class IssueService {

	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private BookService bookService;
	
	@Transactional
	public void issueBook(Long userId, Long bookId) {
		Book book = bookService.get(bookId);
		
		if(book.getStatus() == Book.Status.BORROWED) {
			throw new BookAlreadyIssuedException(bookId);
		}
		
		book.setStatus(Book.Status.BORROWED);
		Issue issue = new Issue();
		issue.setUserId(userId);
		issue.setBook(book);
		issue.setIssueDate(now());
		issueRepository.save(issue);
	}
	
	private Date now() {
		return Date.valueOf(LocalDate.now());
	}
}
