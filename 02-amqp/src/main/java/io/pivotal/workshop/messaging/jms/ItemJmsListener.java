package io.pivotal.workshop.messaging.jms;

import io.pivotal.workshop.messaging.domain.Item;

public class ItemJmsListener {

    public void processItem(Item item){
        System.out.println(String.format("JMS Processing: %s", item));
    }
}
