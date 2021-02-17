package com.example.cameltesting;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyProcessorTest {

    Exchange ex;

    @Test
    void testSomething() throws Exception {
        Processor p = new MyProcessor();
        ex = newExchange("Hello");
        p.process(ex);
        String result = ex.getMessage().getBody(String.class);
        assertEquals(result, "HELLO");
    }

    Exchange newExchange(String body) {
        CamelContext ctx = new DefaultCamelContext();
        Exchange ex = new DefaultExchange(ctx);
        ex.getIn().setBody(body);
        return ex;
    }
}