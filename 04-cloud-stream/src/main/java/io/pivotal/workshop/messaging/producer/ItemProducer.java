package io.pivotal.workshop.messaging.producer;

import io.pivotal.workshop.messaging.domain.Item;

public interface ItemProducer {

    public void sendItem(Item item);
}
