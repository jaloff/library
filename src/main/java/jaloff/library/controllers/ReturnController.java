package jaloff.library.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jaloff.library.dto.requests.ReturnBookRequest;
import jaloff.library.entities.Return;
import jaloff.library.services.ReturnService;

@RestController
@RequestMapping("/returns")
public class ReturnController {
	
	@Autowired
	private ReturnService returnService;
	
	@GetMapping
	public List<Return> findAll() {
		return returnService.getAll();
	}
	
	@PostMapping
	public Return returnBook(@RequestBody ReturnBookRequest dto) {
		return returnService.returnBook(dto.getBookId());
	}
}
