package com.rahul.kafka;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.rahul.kafka.producer.dto.Customer;
import com.rahul.kafka.producer.service.KafkaMessagePublisherService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class KafkaProducerTestingApplicationTests {
	
	@Autowired
	private KafkaMessagePublisherService kafkaMessagePublisherService;
	

	
	
	
	
	/*
	 * public KafkaProducerTestingApplicationTests(KafkaMessagePublisherService
	 * kafkaMessagePublisherService) { this.kafkaMessagePublisherService =
	 * kafkaMessagePublisherService; }
	 */

	/*
	 * @Container static KafkaContainer kafka = new
	 * KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	 * 
	 * @DynamicPropertySource public static void
	 * initKafkaProperties(DynamicPropertyRegistry registry) {
	 * registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers); }
	 */
	
//	@Container
//	static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
//	.withNetwork(null)
//	.
//	.withExposedPorts(9091)
//	.withExtraHost("rahul","192.168.40.131");
//	
	
	
	static KafkaContainer kafkaContainer;

    static {
        // Initialize the Kafka container
        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
            .withNetwork(null) // Ensure no network conflicts
            .withExposedPorts(9092); // Expose Kafka's port

        // Override the Kafka bootstrap server with the remote host and mapped port
        kafkaContainer.setPortBindings(List.of("192.168.40.131:9092")); // Set the remote port

        // Start the Kafka container
        kafkaContainer.start();
    }
	
	
	/*
	 * // Initialize the Kafka container kafkaContainer = new
	 * KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
	 * .withNetwork(null) // Ensure no network conflicts .withExposedPorts(9092); //
	 * Expose Kafka's port
	 * 
	 * // Override the Kafka bootstrap server with the remote host and mapped port
	 * kafkaContainer.setPortBindings(List.of("YOUR_REMOTE_PORT:9092")); // Set the
	 * remote port
	 * 
	 * // Start the Kafka container kafkaContainer.start();
	 */
	
	
	@DynamicPropertySource
	public static void initKafkaProperties(DynamicPropertyRegistry registry) {
		//registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
		//registry.add("spring.kafka.", null);
		
	    registry.add("spring.kafka.bootstrap-servers", 
	    		() -> kafkaContainer.getHost() + ":" + kafkaContainer.getMappedPort(9092));
		
	}
	
	
	
	/*
	 * @Container static GenericContainer<?> redis = new
	 * GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine"))
	 * .withExposedPorts(6379);
	 * 
	 * @DynamicPropertySource static void overrideProperties(DynamicPropertyRegistry
	 * registry) { registry.add("spring.redis.host", () -> "localhost");
	 * registry.add("spring.redis.port", () -> redis.getMappedPort(6379)); }
	 */
	
	
	
	///-------------
	
	@Test
	public void testSendEventsToTopic() {
		kafkaMessagePublisherService.sendEventsToTopic(new Customer(250,"test_user","test@gmail.com","76578456"));
	
		Awaitility
		.await()
		.pollInterval(Duration.ofSeconds(3))
		.atMost(10,TimeUnit.SECONDS)
		.untilAsserted(() -> {
			//if any assert statement
		});
	}
	
	
	
	

	
	

}
