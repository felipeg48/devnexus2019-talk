package io.pivotal.workshop.messaging.configuration;

import io.pivotal.workshop.messaging.amqp.ItemAmqpAnnotatedListener;
import io.pivotal.workshop.messaging.amqp.ItemAmqpProducer;
import io.pivotal.workshop.messaging.producer.ItemProducer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix="messaging",name = "enable-amqp", havingValue = "true")
@Configuration
public class AmqpConfig {

    @Bean
    public ItemProducer itemProducer(RabbitTemplate template){
        return new ItemAmqpProducer(template);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public Queue queue(){
        return new Queue(ItemAmqpProducer.QUEUE,true,false,true);
    }

    /*
    @Bean
    public SimpleMessageListenerContainer listenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(ItemAmqpProducer.QUEUE);

        MessageListenerAdapter adapter = new MessageListenerAdapter(new ItemAmqpListener());
        adapter.setDefaultListenerMethod("processItem");
        adapter.setMessageConverter(new Jackson2JsonMessageConverter());
        container.setMessageListener(adapter);
        return container;
    }
    */

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    public ItemAmqpAnnotatedListener itemAmqpAnnotatedListener(){
        return new ItemAmqpAnnotatedListener();
    }
}
