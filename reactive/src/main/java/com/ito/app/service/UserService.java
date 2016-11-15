package com.ito.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ito.app.dto.Customer;

@Service
public class UserService {
	
	public List<Customer> getCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer(1, "ito"));
		customers.add(new Customer(2, "marshall"));
		customers.add(new Customer(3, "teguh"));
		
		return customers;
	}
}
