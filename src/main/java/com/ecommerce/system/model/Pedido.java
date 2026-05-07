package com.ecommerce.system.model;

import com.ecommerce.system.model.enums.StatusPedido;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name="pedidos")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long produtoId;

    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;
}
