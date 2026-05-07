package com.ecommerce.system.messaging.producer;

import com.ecommerce.system.model.Pedido;

public interface PedidoProducer {
    
    void enviarPedidoCriado (Pedido pedido);
}
