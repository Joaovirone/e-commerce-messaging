package com.ecommerce.system.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.ecommerce.system.dto.PedidoRequestDto;
import com.ecommerce.system.dto.PedidoResponseDto;
import com.ecommerce.system.model.Pedido;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class PedidoMapper {
    
    private final ModelMapper modelMapper;


    public Pedido toEntity(PedidoRequestDto pedidoRequestDto){
        return modelMapper.map(pedidoRequestDto, Pedido.class);
    }

    public PedidoResponseDto toDto(Pedido pedido){
            return new PedidoResponseDto(
                    pedido.getId(),
                    pedido.getProdutoId(),
                    pedido.getQuantidade(),
                    pedido.getStatus()
            );
        }
}
