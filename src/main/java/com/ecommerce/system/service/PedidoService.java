package com.ecommerce.system.service;

import org.springframework.stereotype.Service;

import com.ecommerce.system.dto.PedidoRequestDto;
import com.ecommerce.system.dto.PedidoResponseDto;
import com.ecommerce.system.dto.mapper.PedidoMapper;
import com.ecommerce.system.messaging.producer.PedidoProducer;
import com.ecommerce.system.model.Pedido;
import com.ecommerce.system.model.enums.StatusPedido;
import com.ecommerce.system.repository.PedidoRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class PedidoService {
    
    private final PedidoRepository pedidoRepository;

    private final PedidoProducer pedidoProducer;

    private final PedidoMapper pedidoMapper;


    public PedidoResponseDto criarPedido (PedidoRequestDto request){

        Pedido pedido = pedidoMapper.toEntity(request);

        pedido.setStatus(StatusPedido.PENDENTE);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        pedidoProducer.enviarPedidoCriado(pedidoSalvo);

        return pedidoMapper.toDto(pedidoSalvo);

    }
}
