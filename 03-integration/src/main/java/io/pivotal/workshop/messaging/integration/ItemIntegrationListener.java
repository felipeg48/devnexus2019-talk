package io.pivotal.workshop.messaging.integration;

import io.pivotal.workshop.messaging.domain.Item;

public class ItemIntegrationListener {

    public void process(Item item){
        System.out.println(String.format("Integration Processing: %s", item));
    }
}
