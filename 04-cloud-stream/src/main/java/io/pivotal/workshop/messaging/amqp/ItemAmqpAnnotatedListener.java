package io.pivotal.workshop.messaging.amqp;

import io.pivotal.workshop.messaging.domain.Item;
import io.pivotal.workshop.messaging.producer.ItemProducer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class ItemAmqpAnnotatedListener {

    @RabbitListener(queues= ItemAmqpProducer.QUEUE)
    public void processItem(Item item){
        System.out.println(String.format("AMQP Annotated Processing: %s", item));
    }
}
