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
class KafkaProducerTestingApplicationTests {

	private final Logger log = LoggerFactory.getLogger(KafkaProducerTestingApplicationTests.class);
	
	@Autowired
	private KafkaMessagePublisherService kafkaMessagePublisherService;
	
	/*
	 * @Autowired public
	 * KafkaProducerTestingApplicationTests(KafkaMessagePublisherService
	 * kafkaMessagePublisherService) { super(); this.kafkaMessagePublisherService =
	 * kafkaMessagePublisherService; }
	 */
	/*
	 * @Container static PostgreSQLContainer<?> postgres = new
	 * PostgreSQLContainer<>(DockerImageName.parse("postgres:15.1"));
	 * 
	 * 
	 * @Container static KafkaContainer kafka = new
	 * KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	 * 
	 * @DynamicPropertySource public static void
	 * initProperties(DynamicPropertyRegistry registry) { //kafka properties
	 * registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
	 * 
	 * //postgresql properties registry.add("spring.datasource.url",
	 * postgres::getJdbcUrl); registry.add("spring.datasource.username",
	 * postgres::getUsername); registry.add("spring.datasource.password",
	 * postgres::getPassword); }
	 */
	


	@Test
	public void testSendEventsToTopic() {
		Integer savedCustomerId = kafkaMessagePublisherService.sendEventsToTopic(new CustomerDto( "Rahul", "rahul@gmail.com", "76578456"));

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
