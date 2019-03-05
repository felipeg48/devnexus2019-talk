package io.pivotal.workshop.messaging;

import io.pivotal.workshop.messaging.domain.Item;
import io.pivotal.workshop.messaging.service.ItemService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

@SpringBootApplication
public class MessagingDemoApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(MessagingDemoApplication.class, args);

        System.in.read();
		context.close();
	}


    @Bean
    CommandLineRunner process(ItemService service){
        return args -> {
            Item item = service.addItem(new Item(null,"One","The only one", new Date(),100.00));
        };
    }
}
