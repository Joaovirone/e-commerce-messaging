package com.ecommerce.system.messaging.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.ecommerce.system.config.RabbitMQConfig;
import com.ecommerce.system.model.Pedido;

@Component
public class PedidoProducerImpl implements PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void enviarPedidoCriado(Pedido pedido) {
        // Aqui a mágica acontece: enviamos o objeto Pedido para a Exchange
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.PEDIDO_EXCHANGE, 
            RabbitMQConfig.PEDIDO_CRIADO_ROUTING_KEY, 
            pedido
        );
        
        System.out.println("Mensagem enviada para o RabbitMQ: Pedido ID " + pedido.getId());
    }
}