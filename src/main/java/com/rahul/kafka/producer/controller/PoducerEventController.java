package com.rahul.kafka.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.kafka.producer.dto.Customer;
import com.rahul.kafka.producer.service.KafkaMessagePublisherService;

@RestController
@RequestMapping("/producer")
public class PoducerEventController {

	@Autowired
	private KafkaMessagePublisherService messagePublisherService;
	
	@GetMapping("/publish/{message}")
	public ResponseEntity publishMessage(@PathVariable String message){
		
		try {
			for(int i = 0 ; i < 1000 ; i++) {
			messagePublisherService.sendMessageToTopic(message);}
			return new ResponseEntity<>("Message Published", HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	
	@PostMapping("/publish")
	public ResponseEntity sendEvents(@RequestBody Customer customer) {
		messagePublisherService.sendEventsToTopic(customer);
		return new ResponseEntity<>("Message Published", HttpStatus.OK);
	}
	
	@GetMapping("/welcome")
	public ResponseEntity welcome() {
		System.out.println("Hi controller call");
		return new ResponseEntity<>("welcome" , HttpStatus.OK);
	}
	
	//publish to particular partition
	@PostMapping("/publish/partition")
	public ResponseEntity publishMessageToParticularPartition(@RequestBody Customer customer){
		
		try {
			for(int i = 0 ; i < 100 ; i++) {
				messagePublisherService.sendEventsToTopicInparticularPartition(customer);
				//return new ResponseEntity<>("Message Published", HttpStatus.OK);
			}
			return new ResponseEntity<>("Message Published", HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
}
