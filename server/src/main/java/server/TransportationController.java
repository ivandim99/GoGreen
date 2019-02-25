package server.group12;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

/* TransportationController - Use Spring Boot controller
 * to handle /transport requests
 */
@RestController
public class TransportationController {
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(method = RequestMethod.GET, value = "/transport")
	public Transportation transportation(@RequestParam(value = "vehicle",
			   										   defaultValue = "Unknown")
		   								 String vehicleType) {
		return new Transportation(counter.incrementAndGet(), vehicleType);
	}
}