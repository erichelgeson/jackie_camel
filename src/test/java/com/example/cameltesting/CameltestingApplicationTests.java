package com.example.cameltesting;

import org.apache.camel.*;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpointsAndSkip;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@CamelSpringBootTest
@SpringBootApplication
@MockEndpointsAndSkip("cmis:.*|log:.*")
@UseAdviceWith
class CameltestingApplicationTests {

	@Autowired CamelContext camelContext;
	@Autowired ProducerTemplate producerTemplate;

	@EndpointInject("mock:cmis:foo.com")
	MockEndpoint cmisEndpoint;

	@EndpointInject("mock:log:foo")
	MockEndpoint logMock;

	@Test
	public void testReceive() throws Exception {
		// Given: replace cmis:start with a direct: for testing
		AdviceWith.adviceWith(camelContext, "cmisRouteId", routeBuilder -> {
			routeBuilder.replaceFromWith("direct:cmis:start");
		});
		camelContext.start();
		// Expect: The CMIS endpoint to get 1 message
		cmisEndpoint.expectedMessageCount(1);
		cmisEndpoint.whenAnyExchangeReceived(new MockCMISResponseProcessor());

		// And: the process to set the fileId Header
		logMock.expectedHeaderReceived("fileId", "123456");
		// When we send the message
		producerTemplate.sendBody("direct:cmis:start", "Hello World");
		// Everything is satisfied.
		cmisEndpoint.assertIsSatisfied();
		logMock.assertIsSatisfied();
	}
}

/**
 * Mocks the fields that would be set by the real cmis endpoint
 */
class MockCMISResponseProcessor implements Processor {
	@Override
	public void process(Exchange exchange) {
		exchange.getIn().setBody("cmis document body here");
		exchange.getIn().setHeader("fileId", "123456");
	}
}
