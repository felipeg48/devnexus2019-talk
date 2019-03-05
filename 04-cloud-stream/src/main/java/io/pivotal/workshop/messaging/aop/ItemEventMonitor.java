package io.pivotal.workshop.messaging.aop;


import io.pivotal.workshop.messaging.domain.Item;
import io.pivotal.workshop.messaging.listener.ItemEvent;
import io.pivotal.workshop.messaging.listener.ItemEventAction;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationEventPublisher;

import java.util.regex.Pattern;

@Aspect
public class ItemEventMonitor {

    private ApplicationEventPublisher publisher;
    public ItemEventMonitor(ApplicationEventPublisher publisher){
        this.publisher = publisher;
    }

    @AfterReturning(pointcut = "execution(* io.pivotal.workshop..*Service.*(io.pivotal.workshop.messaging.domain.Item))",
    returning = "item")
    public void itemMonitor(JoinPoint jp, Item item){
        String name = jp.getSignature().getName();
        ItemEventAction action = ItemEventAction.UNKNOWN;

        if(name.contains("add"))
            action = ItemEventAction.SAVE;

        this.publisher.publishEvent(new ItemEvent(this,item, action));
    }
}
