package io.pivotal.workshop.messaging.configuration;


import io.pivotal.workshop.messaging.jms.ItemJmsListener;
import io.pivotal.workshop.messaging.jms.ItemJmsProducer;
import io.pivotal.workshop.messaging.producer.ItemProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;


@ConditionalOnProperty(prefix="messaging",name = "enable-jms", havingValue = "true")
@Configuration
public class JmsConfig {


    @Bean
    public DefaultMessageListenerContainer container(ConnectionFactory connectionFactory, MessageConverter jacksonJmsMessageConverter){
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName(ItemJmsProducer.QUEUE);

        MessageListenerAdapter adapter = new MessageListenerAdapter(new ItemJmsListener());
        adapter.setDefaultListenerMethod("processItem");
        adapter.setMessageConverter(jacksonJmsMessageConverter);

        container.setMessageListener(adapter);
        return container;
    }


    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_class_");
        return converter;
    }


    @Bean
    public ItemProducer producer(JmsTemplate jmsTemplatee){
        return new ItemJmsProducer(jmsTemplatee);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter jacksonJmsMessageConverter){
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(jacksonJmsMessageConverter);
        return template;
    }
}
