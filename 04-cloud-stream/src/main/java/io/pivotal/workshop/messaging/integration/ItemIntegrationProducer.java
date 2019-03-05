package io.pivotal.workshop.messaging.integration;

import io.pivotal.workshop.messaging.domain.Item;
import io.pivotal.workshop.messaging.producer.ItemProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

public class ItemIntegrationProducer implements ItemProducer{

    private MessageChannel producer;
    public static final String QUEUE = "ITEM";

    public ItemIntegrationProducer(MessageChannel producer) {
        this.producer = producer;
    }

    @Override
    public void sendItem(Item item) {
        this.producer.send(MessageBuilder.withPayload(item).build());
    }
}
