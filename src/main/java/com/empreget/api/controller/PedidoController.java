package com.empreget.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.empreget.api.dto.EnderecoResponse;
import com.empreget.api.dto.PedidoModel;
import com.empreget.api.dto.ServicoModel;
import com.empreget.api.mapper.PedidoAssembler;
import com.empreget.domain.model.Pedido;
import com.empreget.domain.repository.PedidoRepositoy;
import com.empreget.domain.service.SolicitacaoPedidoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	private PedidoRepositoy pedidoRepository;
	private SolicitacaoPedidoService solicitacaoPedidoService;
	private PedidoAssembler pedidoAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel solicitar(@Valid @RequestBody Pedido pedido) {
		return pedidoAssembler.toModel(solicitacaoPedidoService.solicitar(pedido));
				
	}
	
	@GetMapping
	public List<PedidoModel> listar(){
		return pedidoAssembler.toCollectionModel(pedidoRepository.findAll());
//		List<Pedido> pedidos = pedidoRepository.findAll();
//		List<PedidoModel> pedidoModel = pedidoAssembler.toCollectionModel(pedidos);
//		
//		return ResponseEntity.ok(pedidoModel);
	}
	
	
	@GetMapping("/{pedidoId}")
	public ResponseEntity<PedidoModel> buscar(@PathVariable Long pedidoId) {
		return pedidoRepository.findById(pedidoId)
				.map(pedido -> {
				PedidoModel pedidoModel = pedidoAssembler.toModel(pedido);
	
				pedidoModel.setLocalDoServico(new EnderecoResponse());
				pedidoModel.getLocalDoServico().setLogradouro(pedido.getCliente().getEndereco().getLogradouro());
				pedidoModel.getLocalDoServico().setNumero(pedido.getCliente().getEndereco().getNumero());
				pedidoModel.getLocalDoServico().setComplemento(pedido.getCliente().getEndereco().getComplemento());
				pedidoModel.getLocalDoServico().setBairro(pedido.getCliente().getEndereco().getBairro());
				pedidoModel.getLocalDoServico().setCidade(pedido.getCliente().getEndereco().getCidade());
				pedidoModel.getLocalDoServico().setEstado(pedido.getCliente().getEndereco().getEstado());
				pedidoModel.getLocalDoServico().setPais(pedido.getCliente().getEndereco().getPais());
	
				pedidoModel.setServico(new ServicoModel());
				pedidoModel.getServico().setDescricao(pedido.getPrestador().getServico().getDescricao());
				pedidoModel.getServico().setValor(pedido.getPrestador().getServico().getValor());
	
				return ResponseEntity.ok(pedidoModel);
			}).orElse(ResponseEntity.notFound().build());
	}
}
