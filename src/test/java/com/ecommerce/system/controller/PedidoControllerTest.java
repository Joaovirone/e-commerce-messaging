package com.ecommerce.system.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.system.dto.PedidoRequestDto;
import com.ecommerce.system.dto.PedidoResponseDto;
import com.ecommerce.system.model.enums.StatusPedido;
import com.ecommerce.system.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Transforma objetos Java em JSON e vice-versa

    @MockBean
    private PedidoService pedidoService; // O Spring injeta esse Mock no nosso Controller

    @Test
    void deveReceberRequisicaoECriarPedidoRetornandoStatus201() throws Exception {
        // Arrange
        PedidoRequestDto requestDto = new PedidoRequestDto(1L, 2);
        PedidoResponseDto responseDto = new PedidoResponseDto(100L, 1L, 2, StatusPedido.PENDENTE);

        // Ensinando o Mock
        when(pedidoService.criarPedido(any(PedidoRequestDto.class))).thenReturn(responseDto);

        // Act & Assert (O MockMvc faz a chamada e já valida a resposta na mesma instrução)
        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))) // Converte o DTO pra JSON
                .andExpect(status().isCreated()) // Esperamos um HTTP 201 (Created)
                .andExpect(jsonPath("$.id").value(100L)) // Valida o corpo do JSON de resposta
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }
}