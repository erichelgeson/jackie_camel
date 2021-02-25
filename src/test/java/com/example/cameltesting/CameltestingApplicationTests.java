package com.example.cameltesting;

import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.ExcludingPackageScanClassResolver;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.junit.jupiter.SpringExtension.getApplicationContext;

@CamelSpringBootTest
@SpringBootApplication
class CameltestingApplicationTests {

	@EndpointInject("mock:result")
	private MockEndpoint resultEndpoint;

	@EndpointInject("mock:cmis:foo")
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
		verify(myProcessor, times(1)).process(any(Exchange.class)); // Mock is called

		resultEndpoint.assertIsSatisfied();
		jdbcEndpoint.assertIsSatisfied();
	}

}
