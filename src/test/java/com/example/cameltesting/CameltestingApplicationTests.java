package com.example.cameltesting;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@CamelSpringBootTest
@SpringBootApplication
@MockEndpoints("direct:end")
class CamelRestingApplicationTests {
	@Autowired
	private ProducerTemplate template;

	@EndpointInject("mock:direct:end")
	private MockEndpoint mock;

	@Test
	public void testReceive() throws Exception {
		template.sendBody("direct:start", "Hello");
		mock.expectedBodiesReceived("HELLO");
		mock.assertIsSatisfied();
	}
}
