package com.ecommerce.system.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String PEDIDO_EXCHANGE = "pedido.v1.events";

    public static final String PEDIDO_CRIADO_QUEUE = "pedido.v1.pedido-criado.estoque-baixa";

    public static final String PEDIDO_CRIADO_ROUTING_KEY = "pedido.criado";

    @Bean
    public Queue queue() {
        return new Queue(PEDIDO_CRIADO_QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(PEDIDO_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(PEDIDO_CRIADO_ROUTING_KEY);
    }

    // Essencial para o Spring enviar o objeto Pedido como JSON para a fila
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
}
