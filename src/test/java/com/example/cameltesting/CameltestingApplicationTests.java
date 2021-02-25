package com.example.cameltesting;

import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.ExcludingPackageScanClassResolver;
import org.apache.camel.test.spring.junit5.CamelSpringTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.junit.jupiter.SpringExtension.getApplicationContext;

class CameltestingApplicationTests extends CamelSpringTestSupport {

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
		Exchange ex = createExchangeWithBody("Hello World");
		resultEndpoint.expectedBodiesReceived("HELLO WORLD");
		jdbcEndpoint.expectedMessageCount(1);

		template.send(ex);

//		verify(myProcessor, times(1)).process(any(Exchange.class));

		resultEndpoint.assertIsSatisfied();
		jdbcEndpoint.assertIsSatisfied();
	}

	@Override
	protected RoutesBuilder[] createRouteBuilders() throws Exception {
		// This is wrong as it's newing up MyRoute so not using the DI one from spring
		return new RoutesBuilder[] {
				new MyRoute()
		};
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		GenericApplicationContext app =  new GenericApplicationContext();
		return app;
	}

}
