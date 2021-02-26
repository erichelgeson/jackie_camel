package com.example.cameltesting;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("cmis:start").id("cmisRouteId")
                .to("cmis:foo.com")
                .to("log:foo?showAll=true");
    }
}

