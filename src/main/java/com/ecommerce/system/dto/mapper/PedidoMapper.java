package com.ecommerce.system.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.ecommerce.system.dto.PedidoRequestDto;
import com.ecommerce.system.dto.PedidoResponseDto;
import com.ecommerce.system.model.Pedido;


@Component
public class PedidoMapper {
    
    private final ModelMapper modelMapper;

    public PedidoMapper (ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public Pedido toEntity(PedidoRequestDto pedidoRequestDto){
        return modelMapper.map(pedidoRequestDto, Pedido.class);
    }

    public PedidoResponseDto toDto(Pedido pedido){
        return modelMapper.map(pedido, PedidoResponseDto.class);
    }
}
