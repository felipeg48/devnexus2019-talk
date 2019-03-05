package io.pivotal.workshop.messaging.listener;

import io.pivotal.workshop.messaging.domain.Item;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class ItemEvent extends ApplicationEvent {

    @Getter
    private Item item;

    @Getter
    private ItemEventAction action;

    public ItemEvent(Object source, Item item, ItemEventAction action) {
        super(source);
        this.item = item;
        this.action = action;
    }


}
