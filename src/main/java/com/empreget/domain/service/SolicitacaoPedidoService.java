package com.empreget.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.empreget.domain.exception.NegocioException;
import com.empreget.domain.model.Cliente;
import com.empreget.domain.model.Pedido;
import com.empreget.domain.model.Prestador;
import com.empreget.domain.model.StatusPedido;
import com.empreget.domain.repository.ClienteRepository;
import com.empreget.domain.repository.PedidoRepositoy;
import com.empreget.domain.repository.PrestadorRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SolicitacaoPedidoService {

	private PedidoRepositoy pedidoRepositoy;
	private ClienteRepository clienteRepository;
	private PrestadorRepository prestadorRepository;
	
	@Transactional
	public Pedido solicitar (Pedido pedido) {
		Cliente cliente = clienteRepository.findById(pedido.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		Prestador prestador = prestadorRepository.findById(pedido.getPrestador().getId())
				.orElseThrow(() -> new NegocioException("Prestador não encontrado"));
		
		
		pedido.setCliente(cliente);
		pedido.setPrestador(prestador);
		pedido.setStatus(StatusPedido.PENDENTE);
		pedido.setDataDoPedido(OffsetDateTime.now());
		
		return pedidoRepositoy.save(pedido);
	}
}
