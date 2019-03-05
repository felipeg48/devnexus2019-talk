package io.pivotal.workshop.messaging.amqp;

import io.pivotal.workshop.messaging.domain.Item;
import io.pivotal.workshop.messaging.producer.ItemProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class ItemAmqpProducer implements ItemProducer {

    public static final String QUEUE = "ITEM";
    private RabbitTemplate template;

    public ItemAmqpProducer(RabbitTemplate template) {
        this.template = template;
    }

    @Override
    public void sendItem(Item item) {
        this.template.convertAndSend(QUEUE,item);
    }
}
