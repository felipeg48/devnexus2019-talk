package io.pivotal.workshop.messaging.service;

import io.pivotal.workshop.messaging.domain.Item;
import io.pivotal.workshop.messaging.repository.ItemRepository;

public class ItemService {

    final private ItemRepository repo;
    public ItemService(ItemRepository repo){
        this.repo = repo;
    }

    public Item addItem(Item item){
        return this.repo.save(item);
    }

    public Iterable<Item> getAllItems(){
        return this.repo.findAll();
    }

    public Item findItem(String id){
        return this.repo.findById(id);
    }
}
