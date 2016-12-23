package gov.diski.ResourceServer.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
	

	@RequestMapping("/index")
	public String base(Principal user) {
		System.out.println("geldi");
		return "index";
	}


}
