package io.pivotal.workshop.messaging.jms;

import io.pivotal.workshop.messaging.domain.Item;
import io.pivotal.workshop.messaging.producer.ItemProducer;
import org.springframework.jms.core.JmsTemplate;

public class ItemJmsProducer implements ItemProducer{

    public static final String QUEUE = "ITEM";
    private JmsTemplate template;
    public ItemJmsProducer(JmsTemplate template) {
        this.template = template;
    }

    @Override
    public void sendItem(Item item) {
        this.template.convertAndSend(QUEUE,item);
    }
}
