package com.rahul.kafka.producer.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.rahul.kafka.producer.dto.Customer;

@Service
public class KafkaMessagePublisherService {

	@Autowired
	private KafkaTemplate<String, Object> template;
	
	public void sendMessageToTopic(String message) {
		System.out.println("Messge "+ message);
		CompletableFuture<SendResult<String,Object>> future = template.send("rahul-topic-4" ,message); //topic name , message
		
		//future.get(); //It will block till the response come
		
		future.whenComplete((result,ex)->{
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
	}
	
	
	public void sendEventsToTopic(Customer customer) {
		//System.out.println("Messge sent :  "+ customer.toString());
		
		try {
		CompletableFuture<SendResult<String,Object>> future = template.send("customer" ,customer.toString()); //topic name , message
		
		//future.get(); //It will block till the response come
		
		future.whenComplete((result,ex)->{
            if (ex == null) {
                System.out.println("Sent message=[" + customer.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                		customer.toString() + "] due to : " + ex.getMessage());
            }
        });
	}catch(Exception ex) {
		ex.getMessage();
	}
		
  }
	
	
	
	/*
	 * Send message to particular partition
	 */
	
	public void sendEventsToTopicInparticularPartition(Customer customer) {
System.out.println("Messge "+ customer.toString());
		
		try {
		CompletableFuture<SendResult<String,Object>> future = template.send("customer-partition" ,customer.toString()); //topic name,partition,null, , message
		
		//future.get(); //It will block till the response come
		
		future.whenComplete((result,ex)->{
            if (ex == null) {
                System.out.println("Sent message=[" + customer.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                		customer.toString() + "] due to : " + ex.getMessage());
            }
        });
	}catch(Exception ex) {
		ex.getMessage();
	}
		
  }
}
