package com.rahul.kafka;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = AppTests.TestcontainersInitializer.class)
public class AppTests {

   
    @Container
    static MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:latest");
    
    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

   
    static class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext ctx) {
            TestPropertyValues.of(
            		//configuration for kafka
                    "spring.kafka.bootstrap-servers=" + kafkaContainer.getBootstrapServers(),
                    
                    //configuration properties for dtabase
                    "spring.datasource.driver-class-name=" + mariaDBContainer.getDriverClassName() ,
                    "spring.datasource.url=" + mariaDBContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mariaDBContainer.getUsername(),
                    "spring.datasource.password=" + mariaDBContainer.getPassword()
                    
            ).applyTo(ctx.getEnvironment());
        }
        
        static {
        	mariaDBContainer.start();
        	kafkaContainer.start(); 
        	}
        
		/*
		 * static { Startables.deepStart(mariaDBContainer, kafka).join(); }
		 */
    }
}
