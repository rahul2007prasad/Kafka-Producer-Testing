package com.rahul.kafka.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rahul.kafka.producer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
