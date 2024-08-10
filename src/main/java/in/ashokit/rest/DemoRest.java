package in.ashokit.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoRest {

	@GetMapping("/admin")
	public String admin() {
		return "<h3> admin page </h3>";
	}
	
	@GetMapping("/user")
	public String user() {
		return "<h3> User page </h3>";
	}
	
	@GetMapping("/")
	public String welcome() {
		return "<h3>Welcome to Our Spring Security </h3>";
	}
}
