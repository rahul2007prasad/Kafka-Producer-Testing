package com.rahul.kafka.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.kafka.producer.dto.CustomerDto;
import com.rahul.kafka.producer.service.KafkaMessagePublisherService;

@RestController
@RequestMapping("/producer")
public class PoducerEventController {

	@Autowired
	private KafkaMessagePublisherService messagePublisherService;
	
	/*
	 * @GetMapping("/publish/{message}") public ResponseEntity
	 * publishMessage(@PathVariable String message){
	 * 
	 * try { for(int i = 0 ; i < 1000 ; i++) {
	 * messagePublisherService.sendMessageToTopic(message);} return new
	 * ResponseEntity<>("Message Published", HttpStatus.OK); }catch (Exception e) {
	 * return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
	 * 
	 * } }
	 */
	
	
	@PostMapping("/publish")
	public ResponseEntity<String> sendEvents(@RequestBody CustomerDto customer) {
		messagePublisherService.sendEventsToTopic(customer);
		return new ResponseEntity<>("Message Published", HttpStatus.OK);
	}
	
	
	
	//publish to particular partition
	@PostMapping("/publish/partition")
	public ResponseEntity<String> publishMessageToParticularPartition(@RequestBody CustomerDto customer){
		
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
