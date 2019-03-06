package io.pivotal.workshop.messaging.configuration;

import io.pivotal.workshop.messaging.domain.Item;
import io.pivotal.workshop.messaging.integration.ItemIntegrationProducer;
import io.pivotal.workshop.messaging.jms.ItemJmsProducer;
import io.pivotal.workshop.messaging.producer.ItemProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

@ConditionalOnProperty(prefix="messaging",name = "enable-cloud-stream", havingValue = "true")
@Configuration
public class CloudStreamConfig {

    @Bean
    public ItemProducer producer(JmsTemplate jmsTemplatee){
        return new ItemJmsProducer(jmsTemplatee);
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_class_");
        return converter;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter jacksonJmsMessageConverter){
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(jacksonJmsMessageConverter);
        return template;
    }

    @EnableBinding(Source.class)
    public static class JmsSource {

        @Bean
        public IntegrationFlow jmsListenerFlow(JmsTemplate jmsTemplate){
            return IntegrationFlows.from(Jms.inboundAdapter(jmsTemplate).destination("ITEM"),
                    c -> c.poller(Pollers.fixedRate(100)))
                    .channel(Source.OUTPUT)
                    .get();
        }

    }

    @EnableBinding(Sink.class)
    public static class LogSink {

        @StreamListener(Sink.INPUT)
        public void log(Item item){
            System.out.println(String.format("CLOUD STREAM: " + item));
        }
    }
}

