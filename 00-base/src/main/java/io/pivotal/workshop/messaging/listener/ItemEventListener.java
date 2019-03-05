package io.pivotal.workshop.messaging.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class ItemEventListener implements ApplicationListener<ItemEvent> {

    Logger log = LoggerFactory.getLogger(ItemEventListener.class);

    @Override
    public void onApplicationEvent(ItemEvent itemEvent) {
        log.info(String.format("Action: %s",itemEvent.getAction().toString()));
        log.info(String.format(">>>> Getting the Item: %s",itemEvent.getItem()));
    }
}
