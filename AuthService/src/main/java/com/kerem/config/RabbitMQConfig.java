package com.kerem.config;

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
    Queue getMailQueue() {
        return new Queue("getMail.Queue");
    }

    @Bean
    Queue updateStatusQueue() {
        return new Queue("updateStatus.Queue");
    }
    @Bean
    Queue createProfileQueue() {
        return new Queue("createProfile.Queue");
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("exchange.direct");
    }

    @Bean
    Binding sendEmailBinding(Queue getMailQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(getMailQueue)
                .to(exchange)
                .with("activationCode.Route");
    }

    @Bean
    Binding updateStatusBinding(Queue updateStatusQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(updateStatusQueue)
                .to(exchange)
                .with("updateStatus.Route");
    }
    @Bean
    Binding createProfileBinding(Queue createProfileQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(createProfileQueue)
                .to(exchange)
                .with("createProfile.Route");
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
