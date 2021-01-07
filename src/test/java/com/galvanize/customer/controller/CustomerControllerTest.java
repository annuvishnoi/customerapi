package com.galvanize.customer.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
    
	@Test
	void getCustomers() throws Exception {
		String expectedCustomerJson = mapper.writeValueAsString(customerList);
		
		mockMVC.perform (
				MockMvcRequestBuilders.get("/customers"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedCustomerJson));
	}
	
}
