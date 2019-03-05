package io.pivotal.workshop.messaging;

import io.pivotal.workshop.messaging.configuration.MessagingProperties;
import io.pivotal.workshop.messaging.domain.Item;
import io.pivotal.workshop.messaging.service.ItemService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessagingDemoApplicationTests {


    @Autowired
    ItemService service;

    @Autowired
    MessagingProperties props;

    @Rule
    public OutputCapture capture = new OutputCapture();


    @Test
	public void applicationEvents() throws InterruptedException {
        assertNotNull(service);
        Item item = service.addItem(new Item(null,"One","The only one", new Date(),100.00));
        assertNotNull(item);

        Item foundItem = service.findItem(item.getId());
        assertNotNull(foundItem);

        assertEquals(1,((Collection<?>)service.getAllItems()).size());
        assertThat(capture.toString(), containsString(foundItem.toString()));

        Thread.sleep(2000);

        if(props.isEnableJms())
            assertThat(capture.toString(),containsString("JMS Processing: Item"));

        if(props.isEnableAmqp())
            assertThat(capture.toString(),containsString("AMQP"));
	}

}
