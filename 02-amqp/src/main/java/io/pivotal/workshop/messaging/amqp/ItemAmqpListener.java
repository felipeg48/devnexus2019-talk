package io.pivotal.workshop.messaging.amqp;

import io.pivotal.workshop.messaging.domain.Item;

public class ItemAmqpListener {

    public void processItem(Item item){
        System.out.println(String.format("AMQP Processing: %s", item));
    }
}
