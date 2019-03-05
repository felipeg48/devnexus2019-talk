package io.pivotal.workshop.messaging.repository;

import io.pivotal.workshop.messaging.domain.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryItemRepository implements ItemRepository {

    private List<Item> items = new ArrayList<>();

    @Override
    public Item save(Item item) {
        assert item != null;
        item.setId(UUID.randomUUID().toString());
        this.items.add(item);
        return item;
    }

    @Override
    public Iterable<Item> findAll() {
        return this.items;
    }

    @Override
    public Item findById(String id) {
        return items.stream().filter( item -> { return item.getId().equals(id); }).findFirst().orElse(null);
    }
}
