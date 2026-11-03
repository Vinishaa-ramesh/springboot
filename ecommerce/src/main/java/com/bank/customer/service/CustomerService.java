package com.bank.customer.service;

import com.bank.customer.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long customerId);
    Customer updateCustomer(Customer customer, Long customerId);
    boolean deleteCustomer(Long customerId);
}