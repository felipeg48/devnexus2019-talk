package io.pivotal.workshop.messaging.domain;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    String id = null;
    String caption = "";
    String description = "";
    Date created;
    Double price;
}
