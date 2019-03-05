package io.pivotal.workshop.messaging.repository;

import io.pivotal.workshop.messaging.domain.Item;

public interface ItemRepository {

    public Item save(Item item);
    public Iterable<Item> findAll();
    public Item findById(String id);

}
