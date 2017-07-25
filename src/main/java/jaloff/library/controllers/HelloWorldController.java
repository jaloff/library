package jaloff.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HelloWorldController {
	
	@GetMapping
	@ResponseBody
	public String helloWorld() {
		return "Hello World!";
	}
}
