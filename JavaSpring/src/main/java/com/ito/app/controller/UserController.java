package com.ito.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.kevinsawicki.http.HttpRequest;
import com.ito.app.model.Customer;
import com.ito.app.service.UserService;

@RestController
@RequestMapping("/customer")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/all")
	public @ResponseBody List<Customer> getAll() {
		HttpRequest request = HttpRequest.get("http://google.com", true, 'q', "baseball gloves", "size", 100);
		System.out.println(request.toString());
		
		return userService.getCustomers();
	}
}
