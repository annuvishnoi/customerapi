package com.galvanize.customer.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.customer.model.Customer;


@WebMvcTest
public class CustomerControllerTest {

	ObjectMapper mapper;
    ArrayList<Customer> customerList;
    

    String customersJsonPath = "src/test/java/data/customers.json"; // 4 customers
    String customerJsonPath = "src/test/java/data/existingCustomer.json"; // 1 customer
    String newCustomerJsonPath = "src/test/java/data/newCustomer.json"; // 1 customer
    
	@Autowired
	private MockMvc mockMVC;
	
	@BeforeEach
    void setUp() throws IOException {
        initializeCustomersData();
    }
	
	// TEST UTILITIES ----------------------------------------------------

    private void initializeCustomersData() throws IOException {
        mapper = new ObjectMapper();
        File customersFile = new File(customersJsonPath);
        customerList = mapper.readValue(customersFile, new TypeReference<ArrayList<Customer>>() {});
    }
    private String getCustomerJsonString() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File customerFile = new File(customerJsonPath);
        Customer customer = mapper.readValue(customerFile, Customer.class);
        return mapper.writeValueAsString(customer);
    }
    private String createCustomerJsonString() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File customerFile = new File(newCustomerJsonPath);
        Customer customer = mapper.readValue(customerFile, Customer.class);
        return mapper.writeValueAsString(customer);
    }
    
	@Test
	void getCustomers() throws Exception {
		String expectedCustomerJson = mapper.writeValueAsString(customerList);
		
		mockMVC.perform (
				MockMvcRequestBuilders.get("/customers"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().json(expectedCustomerJson));
	}
	@Test
	void getCustomer() throws Exception {
		String customerId = "b8a504e8-7cbd-4a54-9a24-dc1832558162";
        String expectedCustomerJson = getCustomerJsonString();
        
	   mockMVC.perform (
	         MockMvcRequestBuilders.get("/customer/{id}", customerId))
	         .andExpect(status().isOk())
	         .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	         .andExpect(MockMvcResultMatchers.content().json(expectedCustomerJson));
	}
	
	@Test
	void addCustomer() throws Exception {
		String newCustomerJson = createCustomerJsonString();
		 
	   
	   mockMVC.perform( MockMvcRequestBuilders
			      .post("/customer")
			      .content(newCustomerJson)
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isCreated())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}
}
