package io.pivotal.workshop.messaging.configuration;

import io.pivotal.workshop.messaging.aop.ItemEventMonitor;
import io.pivotal.workshop.messaging.listener.ItemEventListener;
import io.pivotal.workshop.messaging.producer.ItemProducer;
import io.pivotal.workshop.messaging.repository.InMemoryItemRepository;
import io.pivotal.workshop.messaging.repository.ItemRepository;
import io.pivotal.workshop.messaging.service.ItemService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Bean
    public ItemService simpleService(ItemRepository inMemoryItemRepository){
        return new ItemService(inMemoryItemRepository);
    }

    @Bean
    public ItemRepository inMemoryItemRepository(){
        return new InMemoryItemRepository();
    }

    @Bean
    public ItemEventMonitor monitor(ApplicationEventPublisher publisher){
        return new ItemEventMonitor(publisher);
    }

    @Bean
    public ItemEventListener listener(ItemProducer producer){
        return new ItemEventListener(producer);
    }
}
