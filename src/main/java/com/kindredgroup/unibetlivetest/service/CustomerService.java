package com.kindredgroup.unibetlivetest.service;

import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.repository.CustomerRepository;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Resource
    private CustomerRepository customerRepository;

    public Customer findCustomerByPseudo(String pseudo) {
        return customerRepository.getCustomerByPseudo(pseudo).orElseThrow(() -> new CustomException(format("customer %s not found", pseudo), ExceptionType.CUSTOMER_NOT_FOUND));
    }
    
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
