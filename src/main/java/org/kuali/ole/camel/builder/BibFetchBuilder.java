package org.kuali.ole.camel.builder;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.kuali.ole.camel.processor.BibProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sheiks on 01/11/16.
 */
@Component
public class BibFetchBuilder {
    Logger logger = LoggerFactory.getLogger(BibFetchBuilder.class);

    @Autowired
    public BibFetchBuilder(CamelContext camelContext) {

        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    /*from("oleactivemq:queue:bibQ?asyncConsumer=true&concurrentConsumers=100")
                            .bean(BibProcessor.class,"processBib");*/
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
