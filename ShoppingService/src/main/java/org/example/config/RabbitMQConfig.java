package org.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    Queue getCartQueue() {
        return new Queue("getCart.Queue");
    }
    @Bean
    Queue getBalanceQueue() {
        return new Queue("getBalance.Queue");
    }
    @Bean
    Queue deleteCartQueue() {
        return new Queue("deleteCart.Queue");
    }
    @Bean
    Queue updateBalanceQueue() {
        return new Queue("updateBalance.Queue");
    }

    @Bean
    Queue getAddressQueue() {
        return new Queue("getAddress.Queue");
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("exchange.direct");
    }

    @Bean
    Binding deleteCartBinding(Queue deleteCartQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(deleteCartQueue)
                .to(exchange)
                .with("deleteCart.Route");
    }

    @Bean
    Binding getCartBinding(Queue getCartQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(getCartQueue)
                .to(exchange)
                .with("getCart.Route");
    }
    @Bean
    Binding getBalanceBinding(Queue getBalanceQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(getBalanceQueue)
                .to(exchange)
                .with("getBalance.Route");
    }
    @Bean
    Binding updateBalanceBinding(Queue updateBalanceQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(updateBalanceQueue)
                .to(exchange)
                .with("updateBalance.Route");
    }

    @Bean
    Binding getAddressBinding(Queue getAddressQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(getAddressQueue)
                .to(exchange)
                .with("getAddress.Route");
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
