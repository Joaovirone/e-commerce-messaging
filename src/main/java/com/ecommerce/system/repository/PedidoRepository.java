package com.ecommerce.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.system.model.Pedido;

public interface PedidoRepository extends  JpaRepository<Pedido, Long> {
    
}
