package jaloff.library.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jaloff.library.dto.IssueBookRequestDto;
import jaloff.library.services.IssueService;

@RestController
@RequestMapping("/issues")
public class IssueController {

	@Autowired
	private IssueService issueService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public void issueBook(@RequestBody IssueBookRequestDto dto) {
		issueService.issueBook(dto.getUserId(), dto.getBookId());
	}
	
}
