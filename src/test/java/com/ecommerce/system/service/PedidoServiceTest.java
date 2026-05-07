package com.ecommerce.system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.system.dto.PedidoRequestDto;
import com.ecommerce.system.dto.PedidoResponseDto;
import com.ecommerce.system.dto.mapper.PedidoMapper;
import com.ecommerce.system.messaging.producer.PedidoProducer;
import com.ecommerce.system.model.Pedido;
import com.ecommerce.system.model.enums.StatusPedido;
import com.ecommerce.system.repository.PedidoRepository;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoProducer pedidoProducer;

    @Mock
    private PedidoMapper pedidoMapper; // 1. O novo Mock

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void deveCriarPedidoComStatusPendenteEEnviarMensagem() {
        // Arrange (Preparação)
        PedidoRequestDto request = new PedidoRequestDto(1L, 2);
        
        Pedido pedidoEntidade = new Pedido();
        pedidoEntidade.setProdutoId(1L);
        pedidoEntidade.setQuantidade(2);

        Pedido pedidoSalvoMock = new Pedido();
        pedidoSalvoMock.setId(100L);
        pedidoSalvoMock.setProdutoId(1L);
        pedidoSalvoMock.setQuantidade(2);
        pedidoSalvoMock.setStatus(StatusPedido.PENDENTE);

        PedidoResponseDto responseDtoMock = new PedidoResponseDto(100L, 1L, 2, StatusPedido.PENDENTE);

        // 2. Ensinando os Mocks a responderem
        when(pedidoMapper.toEntity(any(PedidoRequestDto.class))).thenReturn(pedidoEntidade);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoSalvoMock);
        when(pedidoMapper.toDto(any(Pedido.class))).thenReturn(responseDtoMock);

        // Act (Ação)
        PedidoResponseDto response = pedidoService.criarPedido(request);

        // Assert (Verificações)
        assertNotNull(response);
        assertEquals(100L, response.id());
        assertEquals(StatusPedido.PENDENTE, response.status());

        verify(pedidoRepository, times(1)).save(pedidoEntidade);
        verify(pedidoProducer, times(1)).enviarPedidoCriado(pedidoSalvoMock);
    }
}