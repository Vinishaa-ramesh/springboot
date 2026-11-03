package com.bank.customer.service;

import com.bank.customer.data.respositories.CustomerRepository;
import com.bank.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Customer ID not found"));
    }

    @Override
    public Customer updateCustomer(Customer customer, Long customerId) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        return customerRepository.findById(customerId)
                                .map(existing -> {
                                    Optional.ofNullable(customer.getFirstName()).ifPresent(existing::setFirstName);
                                    Optional.ofNullable(customer.getLastName()).ifPresent(existing::setLastName);
                                    Optional.ofNullable(customer.getEmail()).ifPresent(existing::setEmail);
                                    Optional.ofNullable(customer.getPhone()).ifPresent(existing::setPhone);
                                    return customerRepository.save(existing);
                                })
                                .orElseThrow(() -> new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Customer ID not found"));
    }

    @Override
    public boolean deleteCustomer(Long customerId) {
        Customer savedCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Customer ID not found"));
        customerRepository.delete(savedCustomer);
        return true;
    }
}
