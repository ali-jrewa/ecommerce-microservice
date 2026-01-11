package com.allosh.ecommerce.repository;

import com.allosh.ecommerce.customer.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer , String> {
}
