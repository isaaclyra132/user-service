package br.tre.userservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.queue}")
    private String queue;
//    public static final String FILA_NEW_USER_REGISTERED = "NEW_USER_REGISTERED";

    @Bean
    public Queue newUserQueue() {
        return new Queue(queue, true);
    }
}
