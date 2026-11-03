package com.bank.customer.controller;

import com.bank.customer.model.Customer;
import com.bank.customer.service.CustomerService;
import com.bank.customer.service.CustomerServiceImplementation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    CustomerService customerService;

    CustomerController(CustomerServiceImplementation customerServiceImplementation) {
        this.customerService = customerServiceImplementation;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.createCustomer(customer));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers());
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Long customerId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerById(customerId));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer,
                                                   @PathVariable("id") Long customerId){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(customerService.updateCustomer(customer, customerId));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long customerId){
        try {
            customerService.deleteCustomer(customerId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Customer with ID "+customerId+ " deleted!");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}