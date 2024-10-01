package com.rahul.kafka;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rahul.kafka.producer.dto.CustomerDto;
import com.rahul.kafka.producer.service.KafkaMessagePublisherService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableTestcontainers
class KafkaProducerTestingApplicationTests2 {

	private final Logger log = LoggerFactory.getLogger(KafkaProducerTestingApplicationTests2.class);
	
	@Autowired
	private KafkaMessagePublisherService kafkaMessagePublisherService;
	
	


	@Test
	public void testSendEventsToTopic() {
		Integer savedCustomerId = kafkaMessagePublisherService.sendEventsToTopic(new CustomerDto( "test_user", "test@gmail.com", "76578456"));

		Awaitility.
		await().
		pollInterval(Duration.ofSeconds(3))
		.atMost(10, TimeUnit.SECONDS)
		.untilAsserted(() -> { // if any assert statment
				
			CustomerDto fetchedCustomerData = kafkaMessagePublisherService.fetchCustomerData(savedCustomerId);
			log.info("Fetched data from DB "+fetchedCustomerData.toString());
			
																																							// statement
		});
	}

}
