package io.pivotal.workshop.messaging.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix="messaging")
public class MessagingProperties {

    private boolean enableJms = false;
    private boolean enableAmqp = false;
    private boolean enableIntegration = false;
    private boolean enableCloudStream = false;
}
