package com.rahul.kafka.producer.service;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.rahul.kafka.producer.dto.CustomerDto;
import com.rahul.kafka.producer.model.Customer;
import com.rahul.kafka.repo.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class KafkaMessagePublisherService implements IKafkaMessagePublisher{

	private final Logger log = LoggerFactory.getLogger(KafkaMessagePublisherService.class);
	
	@Autowired
	private KafkaTemplate<String, Object> template;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	
	public Integer sendEventsToTopic(CustomerDto customerDto) {
		//System.out.println("Messge sent :  "+ customer.toString());
		
		Customer customerData = null;
		
		try {
		CompletableFuture<SendResult<String,Object>> future = template.send("customer" ,customerDto.toString()); //topic name , message
		
		//future.get(); //It will block till the response come
		
		future.whenComplete((result,ex)->{
            if (ex == null) {
                log.info("Event sent =[" + customerDto.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.info("Unable to send message=[" +
                		customerDto.toString() + "] due to : " + ex.getMessage());
            }
        });
		
		//persisting event data to db
		 customerData = mapper.map(customerDto, Customer.class);
		Customer customerSaveData = customerRepository.save(customerData);
		log.info("Data Persist into DB" + customerSaveData);
		//return customerData.getId();
		
	}catch(Exception ex) {
		ex.getMessage();
	}
		return customerData.getId();
		
  }
	
	
	
	/*
	 * Send message to particular partition
	 */
	
	public void sendEventsToTopicInparticularPartition(CustomerDto customer) {
System.out.println("Messge "+ customer.toString());
		
		try {
		CompletableFuture<SendResult<String,Object>> future = template.send("customer-partition" ,customer.toString()); //topic name,partition,null, , message
		
		//future.get(); //It will block till the response come
		
		future.whenComplete((result,ex)->{
            if (ex == null) {
            	log.info("Sent message=[" + customer.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
            	log.info("Unable to send message=[" +
                		customer.toString() + "] due to : " + ex.getMessage());
            }
        });
	}catch(Exception ex) {
		ex.getMessage();
	}
		
  }



	@Override
	public CustomerDto fetchCustomerData(Integer custId) {
	 Customer customer = customerRepository.findById(custId).get();
	 CustomerDto customerDto = mapper.map(customer, CustomerDto.class);
		return customerDto;
	}
}
