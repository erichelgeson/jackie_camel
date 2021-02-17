package com.example.cameltesting;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .bean(MyProcessor.class)
                .to("mock:direct:end");
    }
}

