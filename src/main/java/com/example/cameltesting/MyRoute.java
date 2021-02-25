package com.example.cameltesting;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {

// Testing constructor DI
//    final MyProcessor myProcessor;
//
//    public MyRoute(MyProcessor myProcessor) {
//        this.myProcessor = myProcessor;
//    }

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .bean(MyProcessor.class)
                .to("mock:cmis:foo")
                .to("mock:result");
    }
}

