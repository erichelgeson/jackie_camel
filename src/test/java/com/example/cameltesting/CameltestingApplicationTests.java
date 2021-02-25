package com.example.cameltesting;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.apache.camel.test.spring.junit5.MockEndpointsAndSkip;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;

@CamelSpringBootTest
@SpringBootApplication
@MockEndpoints
class CameltestingApplicationTests {

	@EndpointInject("mock:result")
	private MockEndpoint resultEndpoint;

	@EndpointInject("cmis:foo") // I expect this to be mocked
	private MockEndpoint jdbcEndpoint;

	@Produce("direct:start")
	protected ProducerTemplate template;

	@MockBean
	MyProcessor myProcessor;

	@Test
	public void testReceive() throws Exception {
		resultEndpoint.expectedBodiesReceived("Hello World"); // Processor is mocked so not upper case
		jdbcEndpoint.expectedMessageCount(1);

		template.sendBody("Hello World");
//		verify(myProcessor, times(1)).process(any(Exchange.class)); // Mock is called

		resultEndpoint.assertIsSatisfied();
		jdbcEndpoint.assertIsSatisfied();
	}

}
