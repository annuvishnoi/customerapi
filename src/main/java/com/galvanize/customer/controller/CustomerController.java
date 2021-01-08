package com.galvanize.customer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.galvanize.customer.model.Customer;

@RestController
public class CustomerController {

	private List<Customer> customers = new ArrayList<>();
	
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		
		return getCustomerList();
		
	}
	private List<Customer> getCustomerList(){
		
		customers = new ArrayList<>();
		
        Customer customer1 = new Customer("Salvator","Di'Mario","510-555-7863","45 Carver Ave, Midland, TX 70134");
        customer1.setId("41acbb7a-ebc8-40b7-8281-70635e3466b8");

        Customer customer2 = new Customer("Qin","Zhang","510-555-2367","1 Main Street, Topeka, KS 37891");
        customer2.setId("b8a504e8-7cbd-4a54-9a24-dc1832558162");

        Customer customer3 = new Customer("Hanaan","Altalib","204-555-9753","1826 Truth Place, New York, NY, 20127");
        customer3.setId("a454f817-3534-43c4-9ceb-f285330693aa");

        Customer customer4 = new Customer("Isabella","Baumfree","309-555-1892","1826 Truth Place, New York, NY 20127");
        customer4.setId("812e2132-cffa-4bb6-8d17-b13c16b2c9b3");

        
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);

        return customers;
    }
	@GetMapping("/customer/{id}")
	public Customer getCustomer(@PathVariable(name = "id") String id) {
	    getCustomerList();
	    Optional<Customer> customerOptional = customers.stream().filter(customer -> customer.getId().equals(id)).findAny();
	    Customer customer = null;
	    if(customerOptional.isPresent()){
	        customer = customerOptional.get();
	    }
	    return customer;
	}
	
	@PostMapping("/customer")
	public ResponseEntity<Customer> getCustomer(@RequestBody Customer customer) {
		customer.setId("1");
		List<Customer> customers = getCustomerList();
	    customers.add(customer);
	    return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
	}
}
