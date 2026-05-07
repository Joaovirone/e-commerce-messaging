package com.ecommerce.system.dto;

import com.ecommerce.system.model.enums.StatusPedido;

public record PedidoResponseDto(
    Long id, 
    Long produtoId, 
    Integer quantidade, 
    StatusPedido status
) {}
