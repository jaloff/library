package jaloff.library.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jaloff.library.dto.requests.IssueBookRequest;
import jaloff.library.entities.Issue;
import jaloff.library.services.IssueService;

@RestController
@RequestMapping("/issues")
public class IssueController {

	@Autowired
	private IssueService issueService;
	
	@GetMapping
	public List<Issue> findIssues() {
		return issueService.getAll();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void issueBook(@RequestBody IssueBookRequest dto) {
		issueService.issueBook(dto.getUserId(), dto.getBookId());
	}
	
}
