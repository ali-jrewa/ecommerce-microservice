package com.allosh.ecommerce.service;

import com.allosh.ecommerce.customer.Customer;
import com.allosh.ecommerce.customer.CustomerRequest;
import com.allosh.ecommerce.customer.CustomerResponse;
import com.allosh.ecommerce.exception.CustomerNotFoundException;
import com.allosh.ecommerce.mapper.CustomerMapper;
import com.allosh.ecommerce.repository.CustomerRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    public String createCustomer(CustomerRequest request){
        var customer = customerRepository.save(customerMapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request){
        var customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                String.format("Cannot update customer:: no customer found with the provided ID::%s" , request.id())
        ));
        mergeCustomer(customer , request);
        customerRepository.save(customer);
    }
    public List<CustomerResponse> findAllCustomer(){
        return customerRepository.findAll().stream().map(
                customerMapper::fromCustomer
        ).collect(Collectors.toList());
    }
    public Boolean existsById(String customerId){
        return customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId){
        return customerRepository.findById(customerId).
                map(customerMapper::fromCustomer).
                orElseThrow(() -> new CustomerNotFoundException(
                String.format("Cannot update customer:: no customer found with the provided ID::%s" , customerId)
        ));
    }
    public void deleteById(String customerId){
        customerRepository.deleteById(customerId);
    }

    private void mergeCustomer(Customer customer , CustomerRequest request){
        if(StringUtils.isNotBlank(request.firstname())){
            customer.setFirstname(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname())){
            customer.setLastname(request.lastname());
        }
        if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if(request.address() != null){
            customer.setAddress(request.address());
        }
    }
}
