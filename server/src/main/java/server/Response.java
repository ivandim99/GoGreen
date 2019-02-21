package server.group12;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Response {

	@RequestMapping
	public @ResponseBody String response() {
		return "Hello ";
	}

}
