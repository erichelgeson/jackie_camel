package com.example.cameltesting;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpointsAndSkip;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@CamelSpringBootTest
@SpringBootApplication
@MockEndpointsAndSkip("cmis:foo.com")
class CameltestingApplicationTests {

	@Autowired
	private ProducerTemplate producerTemplate;
	@EndpointInject("mock:direct:start")
	MockEndpoint template;
	@EndpointInject("mock:cmis:foo.com")
	MockEndpoint cmisEndpoint;

	@Test public void testReceive() throws Exception {
		cmisEndpoint.expectedMessageCount(1);
		producerTemplate.sendBody("direct:start", "Hello World");
		cmisEndpoint.assertIsSatisfied();
	}
}
