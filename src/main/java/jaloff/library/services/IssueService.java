package jaloff.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jaloff.library.entities.Book;
import jaloff.library.entities.Issue;
import jaloff.library.entities.User;
import jaloff.library.exceptions.BookAlreadyIssuedException;
import jaloff.library.exceptions.MaximumIssuesReachedException;
import jaloff.library.repositories.IssueRepository;
import jaloff.library.utils.DateUtils;

@Service
public class IssueService {
	
	public static final int MAX_ISSUES = 1;

	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	public List<Issue> getAll() {
		return issueRepository.findAll();
	}
	
	public void issueBook(Long userId, Long bookId) {
		Book book = bookService.get(bookId);
		User user = userService.get(userId);
		
		if(user.getIssues().size() >= MAX_ISSUES) {
			throw new MaximumIssuesReachedException(MAX_ISSUES);
		}
		
		if(book.getStatus() == Book.Status.BORROWED) {
			throw new BookAlreadyIssuedException(bookId);
		}
		
		book.setStatus(Book.Status.BORROWED);
		Issue issue = Issue.builder()
				.book(book)
				.user(user)
				.issueDate(DateUtils.now())
				.build();
		
		issueRepository.save(issue);
	}
	
	public void delete(Issue issue) {
		issueRepository.delete(issue);
	}
	
}
