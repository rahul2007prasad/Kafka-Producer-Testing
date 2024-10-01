package com.rahul.kafka.producer.service;

import com.rahul.kafka.producer.dto.CustomerDto;

public interface IKafkaMessagePublisher {

	public CustomerDto fetchCustomerData(Integer custId);
}
