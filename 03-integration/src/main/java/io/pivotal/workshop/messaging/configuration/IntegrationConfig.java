package io.pivotal.workshop.messaging.configuration;

import io.pivotal.workshop.messaging.amqp.ItemAmqpListener;
import io.pivotal.workshop.messaging.integration.ItemIntegrationListener;
import io.pivotal.workshop.messaging.integration.ItemIntegrationProducer;
import io.pivotal.workshop.messaging.jms.ItemJmsProducer;
import io.pivotal.workshop.messaging.producer.ItemProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.MessageChannel;

import javax.jms.ConnectionFactory;

@ConditionalOnProperty(prefix="messaging",name = "enable-integration", havingValue = "true")
@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel input(){
        return MessageChannels.direct().get();
    }

    @Bean
    public IntegrationFlow jmsSenderFlow(JmsTemplate jmsTemplate){
        return IntegrationFlows.from(MessageChannels.direct("input"))
                .handle(Jms.outboundAdapter(jmsTemplate).destination(ItemIntegrationProducer.QUEUE))
                .get();
    }

    @Bean
    public IntegrationFlow jmsListenerFlow(JmsTemplate jmsTemplate){
        return IntegrationFlows.from(Jms.inboundAdapter(jmsTemplate).destination(ItemIntegrationProducer.QUEUE),
                    c -> c.poller(Pollers.fixedRate(100)))
                .handle("itemIntegrationListener","process")
                .get();
    }

    @Bean
    public ItemIntegrationListener itemIntegrationListener(){
        return new ItemIntegrationListener();
    }

    @Bean
    public ItemProducer producer(MessageChannel input){
        return new ItemIntegrationProducer(input);
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

}
