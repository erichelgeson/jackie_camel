package com.example.cameltesting;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MyProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String payload = exchange.getIn().getBody(String.class);
        // do something with the payload and/or exchange here
        exchange.getIn().setBody(payload.toUpperCase(Locale.ROOT));
    }
}
