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
	public void issueBook(IssueBookRequestDto dto) {
		Book book = bookService.get(dto.getBookId());
		
		if(book.getStatus() == Book.Status.BORROWED) {
			throw new BookAlreadyIssuedException(dto.getBookId());
		}
		
		book.setStatus(Book.Status.BORROWED);
		Issue issue = new Issue();
		issue.setUserId(dto.getUserId());
		issue.setBook(book);
		issue.setIssueDate(now());
		issueRepository.save(issue);
	}
	
	private Date now() {
		return Date.valueOf(LocalDate.now());
	}
}
