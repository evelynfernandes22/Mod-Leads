package com.empreget.api.dto;

import java.time.OffsetDateTime;

import com.empreget.domain.model.StatusPedido;
import com.empreget.domain.model.TipoDiaria;

import lombok.Data;

@Data
public class PedidoModel {
	
	private Long id;
	private String nomeCliente;
	private EnderecoResponse LocalDoServico;
	private String nomePrestador;
	private TipoDiaria tipoDeDiaria;
	private StatusPedido status;
	private ServicoModel servico;
	private OffsetDateTime dataDoPedido;
	private OffsetDateTime dataDaFinalizacao;
	

}
