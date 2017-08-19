package jaloff.library.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jaloff.library.entities.Book;
import jaloff.library.entities.Issue;
import jaloff.library.entities.Return;
import jaloff.library.exceptions.BookNotIssuedException;
import jaloff.library.repositories.ReturnRepository;
import jaloff.library.utils.DateUtils;

@Service
public class ReturnService {

	@Autowired
	private ReturnRepository returnRepository;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private IssueService issueService;
	
	public List<Return> getAll() {
		return returnRepository.findAll();
	}
	
	@Transactional
	public Return returnBook(Long bookId) {
		Book book = bookService.get(bookId);
		Issue issue = book.getIssue();
		if(issue == null) {
			throw new BookNotIssuedException(bookId);
		}

		Return _return = Return.builder()
				.book(book)
				.user(issue.getUser())
				.issueDate(issue.getIssueDate())
				.returnDate(DateUtils.now())
				.build();
		
		issueService.delete(issue);
		book.setStatus(Book.Status.AVAIABLE);
		
		return returnRepository.save(_return);
	}
}
