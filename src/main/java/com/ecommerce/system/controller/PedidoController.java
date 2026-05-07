package com.ecommerce.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.system.dto.PedidoRequestDto;
import com.ecommerce.system.dto.PedidoResponseDto;
import com.ecommerce.system.service.PedidoService;

import lombok.Data;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/pedidos")
@Data
public class PedidoController {
    
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDto> criarPedido(@RequestBody PedidoRequestDto request) {
        
        
        PedidoResponseDto response = pedidoService.criarPedido(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

        
    }
    

}
